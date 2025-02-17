package com.cand.data_base;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.persistence.Column;
import javax.persistence.Id;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Repository {
    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(CV.JDBC_URL); // 数据库的URL
        config.setUsername(CV.USER);
        config.setPassword(CV.PASSWORD);

        config.setConnectionTimeout(5 * 1000); // 等待连接超时时间设为 5 秒
        config.setMaximumPoolSize(50); // 最大连接池大小，建议从 50 开始，根据负载调整
        config.setMinimumIdle(10); // 最小空闲连接数，确保低负载时也有可用连接
        config.setIdleTimeout(5 * 60 * 1000); // 空闲连接超时时间，避免无用连接占用资源
        config.setMaxLifetime(60 * 60 * 1000); // 连接最大存活时间，避免长期连接导致问题
        dataSource = new HikariDataSource(config);
    }

    public static Connection connect() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    private static final Map<String, Boolean> tableExistsMap = new HashMap<>();

    private static void validateTableName(String tableName) {
        if (!tableName.matches("[a-zA-Z0-9_]+")) {
            throw new IllegalArgumentException("Invalid table name: " + tableName);
        }
    }

    public static boolean exists(String tableName) throws SQLException {
        validateTableName(tableName);
        boolean exists = tableExistsMap.getOrDefault(tableName, false);
        if (exists) {
            return true;
        }
        String query = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tableName.toUpperCase());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    exists = resultSet.getInt(1) > 0;
                }
            }
        }
        tableExistsMap.put(tableName, exists);
        return exists;
    }

    // 使用线程安全的 ConcurrentHashMap
    private static final Map<Class<?>, List<Field>> fieldCache = new ConcurrentHashMap<>();

    private static List<Field> getCachedFields(Class<?> clazz) {
        // 使用 computeIfAbsent 缓存字段列表
        return fieldCache.computeIfAbsent(clazz, cls ->
                Arrays.stream(cls.getDeclaredFields())
                        .peek(field -> field.setAccessible(true)) // 设置字段可访问
                        .collect(Collectors.toList()) // Java 8 收集为 List
        );
    }


    // 插入单个实体
    public <T> int insertEntity(T entity, String tableName) throws SQLException {
        Class<?> clazz = entity.getClass();
        List<Object> values = new ArrayList<>();
        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();
        boolean isFirstField = true;

        // 遍历所有字段
        for (Field field : getCachedFields(clazz)) {
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
        // System.out.println("执行的sql:" + sql);
        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {
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
        List<Field> fields = getCachedFields(clazz);
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
        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {
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
            throw new SQLException("发生了异常: " + tableName, e);
        }
    }


    // 查询所有实体
    // 查询所有实体，支持返回最近的 size 条数据
    public <T> List<T> findAllEntities(Class<T> clazz, String tableName, int size) throws SQLException {
        // 添加 LIMIT 子句来限制返回的记录数
        String sql = "SELECT * FROM " + tableName + " ORDER BY timestamp DESC LIMIT ?";
        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {
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

    public int getTableRowCount(String tableName) {
        // 使用 COUNT(*) 获取表的总行数
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // 获取 COUNT(*) 的结果
                } else {
                    return 0;
                }
            }
        } catch (Exception e) {
            return 0;
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

        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {
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

    public <T> T getNewestInsertEntity(Class<T> clazz, String tableName) throws SQLException {
        String sql = "SELECT * FROM " + tableName + " ORDER BY timestamp DESC LIMIT 1";
        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return mapResultSetToEntity(rs, clazz);
            }
            return null; // 表为空时
        } catch (SQLException e) {
            throw new SQLException("Error fetching last entity: " + sql, e);
        }
    }


    private <T> T mapResultSetToEntity(ResultSet rs, Class<T> clazz) throws SQLException {
        try {
            T entity = clazz.getDeclaredConstructor().newInstance(); // 实例化实体类
            for (Field field : getCachedFields(clazz)) {
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
                    } else if (columnValue != null) {
                        field.set(entity, columnValue); // 普通字段赋值
                    }
                }
            }
            return entity;
        } catch (Exception e) {
            throw new SQLException("Error mapping result set to entity: " + clazz.getName(), e);
        }
    }

    /**
     * 获取数据库中所有表名
     */
    public List<String> getAllTableNames(String tableNamePrefix) throws SQLException {
        // 用于存储表名的列表
        List<String> tableNames = new ArrayList<>();
        String catalog = null; // 当前数据库目录（可以为 null）
        String schemaPattern = null; // 当前数据库模式（可以为 null 或者具体模式名称）
        String tableNamePattern = null; // 表名模式（可以为 null 或者具体表名模式）
        String[] types = {"TABLE"}; // 只获取表类型的对象

        try (Connection connection = connect()) {
            DatabaseMetaData metaData = connection.getMetaData();

            try (ResultSet rs = metaData.getTables(catalog, schemaPattern, tableNamePattern, types)) {
                while (rs.next()) {
                    String tableName = rs.getString("TABLE_NAME");
                    if(tableNamePrefix != null && tableName.startsWith(tableNamePrefix)){
                        tableNames.add(tableName);
                    }else if(tableNamePrefix == null){
                        tableNames.add(tableName);
                    }
                }
            }
        } catch (Exception e) {
            throw new SQLException("Error retrieving table names.", e);
        }
        return tableNames;
    }


    // 更新实体
    public <T> int updateEntity(T entity) throws SQLException {
        Class<?> clazz = entity.getClass();
        String tableName = clazz.getSimpleName().toLowerCase();
        List<Object> values = new ArrayList<>();
        StringBuilder setClause = new StringBuilder();
        String idColumn = null;
        Object idValue = null;

        for (Field field : getCachedFields(clazz)) {
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
        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {
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

        for (Field field : getCachedFields(clazz)) {
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
        try (Connection connection = connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, idValue);
            return ps.executeUpdate();
        }
    }
}
