package com.cand.data_base;

import javax.persistence.Column;
import javax.persistence.Id;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class Repository {
    private final Connection connection;

    public Repository(Connection connection) {
        this.connection = connection;
    }

    // 插入单个实体
    public <T> int insertEntity(T entity, String tableName) throws SQLException {
        Class<?> clazz = entity.getClass();
        List<Object> values = new ArrayList<>();
        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();
        boolean isFirstField = true;

        // 遍历所有字段
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                try {
                    // 忽略主键字段 (如CandleEntity的timestamp)
                    if (field.getAnnotation(Id.class) == null) {
                        if (!isFirstField) {
                            columns.append(", ");
                            placeholders.append(", ");
                        }
                        columns.append(column.name());
                        placeholders.append("?");

                        // 获取字段值并处理 LocalDateTime
                        Object fieldValue = field.get(entity);

                        // 特殊处理 LocalDateTime 类型字段
                        if (fieldValue instanceof LocalDateTime) {
                            fieldValue = Timestamp.valueOf((LocalDateTime) fieldValue);
                        }

                        values.add(fieldValue);
                        isFirstField = false;
                    }
                } catch (IllegalAccessException e) {
                    // 更好的错误处理
                    throw new SQLException("Error accessing field: " + field.getName(), e);
                }
            }
        }

        // 构建SQL语句
        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";
        System.out.println("执行的sql:" + sql);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // 设置参数
            for (int i = 0; i < values.size(); i++) {
                ps.setObject(i + 1, values.get(i));
            }
            return ps.executeUpdate();
        } catch (Exception e) {
            // 捕获SQL异常，抛出给调用者
            e.printStackTrace();
        }
        return -1;
    }

    public <T> int batchInsertEntities(List<T> entities, String tableName) throws Exception {
        if (entities == null || entities.isEmpty()) {
            return 0; // 如果实体列表为空，直接返回
        }

        Class<?> clazz = entities.get(0).getClass();
        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();
        boolean isFirstField = true;

        // 获取字段映射
        Field[] fields = clazz.getDeclaredFields();
        List<Field> entityFields = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);
            if (column != null && field.getAnnotation(Id.class) == null) {
                if (!isFirstField) {
                    columns.append(", ");
                    placeholders.append(", ");
                }
                columns.append(column.name());
                placeholders.append("?");
                entityFields.add(field);
                isFirstField = false;
            }
        }

        // 构建SQL模板
        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";
        System.out.println("执行的SQL模板: " + sql);

        // 执行批量插入
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int batchSize = 5000; // 批次大小，适当调整以平衡性能与内存消耗
            int totalInserted = 0;
            int batchCount = 0;

            for (T entity : entities) {
                // 设置参数
                int paramIndex = 1;
                for (Field field : entityFields) {
                    Object fieldValue = field.get(entity);
                    if (fieldValue instanceof LocalDateTime) {
                        fieldValue = Timestamp.valueOf((LocalDateTime) fieldValue);
                    }
                    ps.setObject(paramIndex++, fieldValue);
                }
                ps.addBatch();
                batchCount++;

                // 达到批次大小或是最后一批时，执行批量插入
                if (batchCount % batchSize == 0 || batchCount == entities.size()) {
                    int[] batchResult = ps.executeBatch();
                    totalInserted += Arrays.stream(batchResult).sum(); // 累加插入结果
                    ps.clearBatch(); // 清空批处理队列
                }
            }

            return totalInserted; // 返回总插入记录数
        } catch (Exception e) {
            throw e;
        }
    }


    // 查询所有实体
    // 查询所有实体，支持返回最近的 size 条数据
    public <T> List<T> findAllEntities(Class<T> clazz, String tableName, int size) throws SQLException {
        // 添加 LIMIT 子句来限制返回的记录数
        String sql = "SELECT * FROM " + tableName + " ORDER BY timestamp DESC LIMIT ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, size); // 设置 LIMIT 参数
            try (ResultSet rs = ps.executeQuery()) {
                List<T> entities = new ArrayList<>();
                while (rs.next()) {
                    entities.add(mapResultSetToEntity(rs, clazz));
                }
                return entities;
            }
        } catch (SQLException e) {
            throw new SQLException("Error executing query: " + sql, e);
        }
    }

    public <T> List<T> findDataInRange(Class<T> clazz, String tableName, int start, int end) throws SQLException {
        // 检查 start 和 end 是否合理
        if (start < 0 || end <= start) {
            throw new IllegalArgumentException("Invalid range: start must be >= 0 and end must be greater than start.");
        }

        // 计算 LIMIT 和 OFFSET 的值
        int size = end - start;  // 数据范围的大小，包含 start 但不包含 end
        String sql = "SELECT * FROM " + tableName + " LIMIT ? OFFSET ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, size);   // 设置 LIMIT 参数
            ps.setInt(2, start);  // 设置 OFFSET 参数

            try (ResultSet rs = ps.executeQuery()) {
                List<T> entities = new ArrayList<>();
                while (rs.next()) {
                    entities.add(mapResultSetToEntity(rs, clazz));
                }
                return entities;
            }
        } catch (SQLException e) {
            throw new SQLException("Error executing query: " + sql, e);
        }
    }



    private <T> T mapResultSetToEntity(ResultSet rs, Class<T> clazz) throws SQLException {
        try {
            T entity = clazz.getDeclaredConstructor().newInstance(); // 实例化实体类
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    String columnName = column.name(); // 数据库列名
                    Object columnValue = rs.getObject(columnName); // 从结果集中获取值

                    // 特殊处理 LocalDateTime 类型
                    if (field.getType().equals(LocalDateTime.class) && columnValue instanceof Timestamp) {
                        field.set(entity, ((Timestamp) columnValue).toLocalDateTime());
                    }
                    // 特殊处理 BigDecimal 类型
                    else if (field.getType().equals(BigDecimal.class) && columnValue instanceof BigDecimal) {
                        field.set(entity, ((BigDecimal) columnValue).stripTrailingZeros());
                    }

                    else if (columnValue != null) {
                        field.set(entity, columnValue); // 普通字段赋值
                    }
                }
            }
            return entity;
        } catch (Exception e) {
            throw new SQLException("Error mapping result set to entity: " + clazz.getName(), e);
        }
    }


    // 更新实体
    public <T> int updateEntity(T entity) throws SQLException {
        Class<?> clazz = entity.getClass();
        String tableName = clazz.getSimpleName().toLowerCase();
        List<Object> values = new ArrayList<>();
        StringBuilder setClause = new StringBuilder();
        String idColumn = null;
        Object idValue = null;

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    if (field.isAnnotationPresent(Id.class)) {
                        idColumn = column.name();
                        idValue = field.get(entity);
                    } else {
                        setClause.append(column.name()).append(" = ?, ");
                        values.add(field.get(entity));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        // 去掉最后的 ", "
        setClause.setLength(setClause.length() - 2);

        String sql = "UPDATE " + tableName + " SET " + setClause + " WHERE " + idColumn + " = ?";
        values.add(idValue);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < values.size(); i++) {
                ps.setObject(i + 1, values.get(i));
            }
            return ps.executeUpdate();
        }
    }

    // 删除实体
    public <T> int deleteEntity(T entity) throws SQLException {
        Class<?> clazz = entity.getClass();
        String tableName = clazz.getSimpleName().toLowerCase();
        String idColumn = null;
        Object idValue = null;

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                idColumn = field.getAnnotation(Column.class).name();
                field.setAccessible(true);
                try {
                    idValue = field.get(entity);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        if (idColumn == null) {
            throw new IllegalArgumentException("Entity does not have an @Id annotated field.");
        }

        String sql = "DELETE FROM " + tableName + " WHERE " + idColumn + " = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, idValue);
            return ps.executeUpdate();
        }
    }


}
