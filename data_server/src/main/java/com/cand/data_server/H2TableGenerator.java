package com.cand.data_server;

import org.h2.util.StringUtils;

import javax.persistence.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;

public class H2TableGenerator {

    public static void generateTable(String JDBC_URL, Class<?> entityClass, String tableName) throws Exception {
        // 检查是否有 @Entity 注解
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class " + entityClass.getName() + " is not an Entity.");
        }

        // 获取表名
        if (StringUtils.isNullOrEmpty(tableName) && entityClass.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = entityClass.getAnnotation(Table.class);
            tableName = tableAnnotation.name();
        }

        // 构建 CREATE TABLE 语句
        StringBuilder createTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (");

        // 标记主键字段
        String primaryKey = null;

        // 遍历字段
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                String columnName = columnAnnotation.name().isEmpty() ? field.getName() : columnAnnotation.name();
                String columnType = getSQLType(field.getType(), columnAnnotation);

                createTableSQL.append(columnName).append(" ").append(columnType);

                if (!columnAnnotation.nullable()) {
                    createTableSQL.append(" NOT NULL");
                }

                if (field.isAnnotationPresent(Id.class)) {
                    primaryKey = columnName; // 记录主键字段名
                    // 如果主键的生成策略是 IDENTITY，添加自增关键字
                    GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
                    if (generatedValue != null && generatedValue.strategy() == GenerationType.IDENTITY) {
                        createTableSQL.append(" AUTO_INCREMENT");
                    }
                }

                createTableSQL.append(", ");
            }
        }

        // 移除最后一个逗号
        createTableSQL.setLength(createTableSQL.length() - 2);

        // 添加主键约束
        if (primaryKey != null) {
            createTableSQL.append(", PRIMARY KEY (").append(primaryKey).append(")");
        }

        createTableSQL.append(");");

        // 执行 SQL
        try (Connection connection = DriverManager.getConnection(JDBC_URL, CV.USER, CV.PASSWORD);
             Statement statement = connection.createStatement()) {
            System.out.println("执行的SQL语句是：" + createTableSQL);
            statement.execute(createTableSQL.toString());
            System.out.println("Table " + tableName + " created successfully.");
        }
    }

    private static String getSQLType(Class<?> type, Column columnAnnotation) {
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
        } else if (type == BigDecimal.class) {
            // 对 BigDecimal 类型处理 precision 和 scale
            int precision = columnAnnotation.precision();
            int scale = columnAnnotation.scale();
            if (precision > 0 && scale >= 0) {
                return String.format("DECIMAL(%d, %d)", precision, scale);
            } else {
                return "DECIMAL(38, 18)"; // 默认值 38代表总位数， 18代表小数位
            }
        } else if (type == LocalDateTime.class) {
            // 对 LocalDateTime 类型映射为 TIMESTAMP
            return "TIMESTAMP";
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + type.getName());
        }
    }

    public static void printTableStructure(String JDBC_URL, String tableName) throws Exception {
        String query = "SHOW COLUMNS FROM " + tableName; // H2 数据库使用这个语法
        try (Connection connection = DriverManager.getConnection(JDBC_URL, CV.USER, CV.PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("Structure of table: " + tableName);
            System.out.println("-----------------------------------");
            while (resultSet.next()) {
                String columnName = resultSet.getString("FIELD");  // 列名
                String columnType = resultSet.getString("TYPE");   // 列类型
                String isNullable = resultSet.getString("NULL");  // 是否允许 NULL
                String key = resultSet.getString("KEY");          // 主键或索引信息
                System.out.printf("Column: %-15s Type: %-15s Nullable: %-10s Key: %-10s\n",
                        columnName, columnType, isNullable, key);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String tableName = "candle_test";
        // 示例：生成表
        generateTable(CV.JDBC_URL, CandleEntity.class, tableName);
        // 示例：打印表结构
        printTableStructure(CV.JDBC_URL, tableName);
    }
}
