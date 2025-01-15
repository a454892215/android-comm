package com.cand.data_base;


import com.cand.util.LogUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 写一个 H2 Database 工具类， 具有的功能函数有
 * 0. 连接到数据库
 * 1. 判断指定名字的table是否存在
 */
public class H2DatabaseUtils {
    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(CV.JDBC_URL); // 数据库的URL
        config.setUsername(CV.USER);
        config.setPassword(CV.PASSWORD);

        config.setConnectionTimeout(5 * 1000); // 等待连接超时时间设为 5 秒
        config.setMaximumPoolSize(50); // 最大连接池大小，建议从 50 开始，根据负载调整
        config.setMinimumIdle(10); // 最小空闲连接数，确保低负载时也有可用连接
        config.setIdleTimeout(10 * 1000); // 空闲连接超时时间，避免无用连接占用资源
        config.setMaxLifetime(30 * 60 * 1000); // 连接最大存活时间，避免长期连接导致问题
        dataSource = new HikariDataSource(config);
    }

    public static Connection connect() throws SQLException {
        return dataSource.getConnection();
    }

    private static final Map<String, Boolean> tableExistsMap = new HashMap<>();

    public boolean exists(String tableName) throws SQLException {
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


    public void generateTable(Class<?> entityClass, String tableName) throws Exception {
        if (exists(tableName)) {
            LogUtil.d(tableName + "表，在数据库已经存在");
        } else {
            H2TableGenerator.generateTable(entityClass, tableName);
            H2TableGenerator.printTableStructure(tableName);
        }

    }

    public <T> int insertEntity(T entity, String tableName) throws Exception {
        Connection connect = connect();
        Repository repository = new Repository(connect);
        if (!exists(tableName)) {
            H2TableGenerator.generateTable(entity.getClass(), tableName);
        }
        return repository.insertEntity(entity, tableName);
    }

    public <T> T getLastEntity(String tableName, Class<T> clazz) throws Exception {
        Connection connect = connect();
        Repository repository = new Repository(connect);
        if (!exists(tableName)) {
            H2TableGenerator.generateTable(clazz, tableName);
        }
        return repository.getLastEntity(clazz, tableName);
    }

    public static void main(String[] args) throws Throwable {
        H2DatabaseUtils utils = new H2DatabaseUtils();
        utils.generateTable(CandleEntity.class, "test_table");
    }
}
