package com.cand.data_server;

import javax.persistence.Column;
import javax.persistence.Id;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public class GenericRepository {
    private final Connection connection;

    public GenericRepository(Connection connection) {
        this.connection = connection;
    }

    // 插入单个实体
    public <T> int insertEntity(T entity, String tableName) throws SQLException {
        Class<?> clazz = entity.getClass();
        List<Object> values = new ArrayList<>();
        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();

        for (Field field : clazz.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                field.setAccessible(true);
                try {
                    columns.append(column.name()).append(", ");
                    placeholders.append("?, ");
                    values.add(field.get(entity));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        // 去掉最后的 ", "
        columns.setLength(columns.length() - 2);
        placeholders.setLength(placeholders.length() - 2);

        String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + placeholders + ")";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < values.size(); i++) {
                ps.setObject(i + 1, values.get(i));
            }
            return ps.executeUpdate();
        }
    }

    // 插入多个实体
    public <T> int[] insertEntities(List<T> entities) throws SQLException {
        if (entities.isEmpty()) return new int[0];

        T firstEntity = entities.get(0);
        Class<?> clazz = firstEntity.getClass();
        String tableName = clazz.getSimpleName().toLowerCase(); // 使用类名作为表名
        List<Object[]> batchArgs = new ArrayList<>();
        List<String> columns = new ArrayList<>();
        StringBuilder placeholders = new StringBuilder();

        // 获取字段名
        for (Field field : clazz.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                columns.add(column.name());
                placeholders.append("?, ");
            }
        }

        // 去掉最后的 ", "
        placeholders.setLength(placeholders.length() - 2);

        String sql = "INSERT INTO " + tableName + " (" + String.join(", ", columns) + ") VALUES (" + placeholders + ")";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (T entity : entities) {
                List<Object> values = new ArrayList<>();
                for (Field field : clazz.getDeclaredFields()) {
                    Column column = field.getAnnotation(Column.class);
                    if (column != null) {
                        field.setAccessible(true);
                        try {
                            values.add(field.get(entity));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                for (int i = 0; i < values.size(); i++) {
                    ps.setObject(i + 1, values.get(i));
                }
                ps.addBatch();
            }
            return ps.executeBatch();
        }
    }

    // 根据ID查询实体
    public <T> Optional<T> findEntityById(Class<T> clazz, Object id) throws SQLException {
        String tableName = clazz.getSimpleName().toLowerCase();
        String idColumn = null;

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                idColumn = field.getAnnotation(Column.class).name();
                break;
            }
        }

        if (idColumn == null) {
            throw new IllegalArgumentException("Entity does not have an @Id annotated field.");
        }

        String sql = "SELECT * FROM " + tableName + " WHERE " + idColumn + " = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToEntity(rs, clazz));
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    // 查询所有实体
    public <T> List<T> findAllEntities(Class<T> clazz) throws SQLException {
        String tableName = clazz.getSimpleName().toLowerCase();
        String sql = "SELECT * FROM " + tableName;
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<T> entities = new ArrayList<>();
            while (rs.next()) {
                entities.add(mapResultSetToEntity(rs, clazz));
            }
            return entities;
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

    // 将 ResultSet 映射为实体对象
    private <T> T mapResultSetToEntity(ResultSet rs, Class<T> clazz) throws SQLException {
        try {
            T entity = clazz.getDeclaredConstructor().newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    field.setAccessible(true);
                    field.set(entity, rs.getObject(column.name()));
                }
            }
            return entity;
        } catch (Exception e) {
            throw new SQLException("Error mapping ResultSet to entity", e);
        }
    }
}
