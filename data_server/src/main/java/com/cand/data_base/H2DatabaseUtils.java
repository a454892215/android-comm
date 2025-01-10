package com.cand.data_base;

import com.util.LogUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

/**
 * 写一个 H2 Database 工具类， 具有的功能函数有
 * 0. 连接到数据库
 * 1. 判断指定名字的table是否存在
 */
public class H2DatabaseUtils {
    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(CV.JDBC_URL); // 修改为你的数据库路径
        config.setUsername("sa");
        config.setPassword("");
        config.setConnectionTimeout(3 * 1000); // 3秒
        config.setMaximumPoolSize(10); // 最大连接数
        dataSource = new HikariDataSource(config);
    }

    public static Connection connect() throws SQLException {
        return dataSource.getConnection();
    }

    public static boolean exists(String tableName) throws SQLException {
        String query = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tableName.toUpperCase());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }


    public static void generateTable(String JDBC_URL, Class<?> entityClass, String tableName) throws Exception {
        if(exists(tableName)){
         LogUtil.d(tableName + "表，在数据库已经存在");
        }else{
            H2TableGenerator.generateTable(JDBC_URL, entityClass, tableName);
            H2TableGenerator.printTableStructure(JDBC_URL, tableName);
        }

    }

    public static void main(String[] args) throws Throwable {
        generateTable(CV.JDBC_URL, CandleEntity.class, "test_table");
    }
}
