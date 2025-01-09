package com.cand.data_server;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class H2TableGenerator {




    /**
     * 根据实体类生成数据库表
     *
     * @param entityClass 实体类的Class对象
     * @throws Exception 如果出现反射或SQL异常
     */
    public static void generateTable(String JDBC_URL, Class<?> entityClass) throws Exception {
        // 检查是否有@Entity注解
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class " + entityClass.getName() + " is not an Entity.");
        }

        // 获取表名
        String tableName = entityClass.getSimpleName();
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = entityClass.getAnnotation(Table.class);
            tableName = tableAnnotation.name();
        }

        // 构建SQL创建表的语句
        StringBuilder createTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (");

        // 遍历所有字段
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            // 检查是否有@Column注解
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                String columnName = columnAnnotation.name().isEmpty() ? field.getName() : columnAnnotation.name();
                String columnType = getSQLType(field.getType());

                createTableSQL.append(columnName).append(" ").append(columnType);

                if (!columnAnnotation.nullable()) {
                    createTableSQL.append(" NOT NULL");
                }

                createTableSQL.append(", ");
            }
        }

        // 移除最后一个逗号并添加括号
        createTableSQL.setLength(createTableSQL.length() - 2);
        createTableSQL.append(");");

        // 执行SQL
        try (Connection connection = DriverManager.getConnection(JDBC_URL, CV.USER, CV.PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL.toString());
            System.out.println("Table " + tableName + " created successfully.");
        }
    }

    /**
     * 根据Java类型返回SQL类型
     *
     * @param type Java字段类型
     * @return 对应的SQL类型
     */
    private static String getSQLType(Class<?> type) {
        if (type == String.class) {
            return "VARCHAR(255)";
        } else if (type == int.class || type == Integer.class) {
            return "INT";
        } else if (type == long.class || type == Long.class) {
            return "BIGINT";
        } else if (type == double.class || type == Double.class) {
            return "DOUBLE";
        } else if (type == boolean.class || type == Boolean.class) {
            return "BOOLEAN";
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + type.getName());
        }
    }

    public static void main(String[] args) throws Exception {
        // 示例：生成表
        generateTable(CV.JDBC_URL, ExampleEntity.class);
    }
}

// 示例实体类
@Entity
@Table(name = "example_table")
class ExampleEntity {
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age")
    private int age;
}
