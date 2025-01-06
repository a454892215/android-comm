package com.cand.data_server;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.Collections;
import java.util.List;

/**
 以下代码使用chatgpt生成：
 写一个 H2 Database 工具类， 具有的功能函数有
 *    0. 连接到数据库
 *    1. 判断指定名字的table是否存在
 *    2. 创建指定名字的table
 *    3. 向指定table插入一条数据或者多条数据
 *    4. 从指定table，指定位置开始获取一条或者多条数据
 *    5. 从指定table指定位置删除一条或者多条数据
 */
public class H2DatabaseUtils {
    private static final String JDBC_URL = "jdbc:h2:./local_data/candle01"; // 修改为你的数据库路径


    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(JDBC_URL); // 修改为你的数据库路径
        config.setUsername("sa");
        config.setPassword("");
        config.setConnectionTimeout(3 * 1000); // 3秒
        config.setMaximumPoolSize(10); // 最大连接数
        dataSource = new HikariDataSource(config);
    }

    public static Connection connect() throws SQLException {
        return dataSource.getConnection();
    }

    public static boolean tableExists(String tableName) throws SQLException {
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

    /**
     * 创建指定名字的 table
     * @param tableName 表名
     * @param schema    表结构 (如 "id INT PRIMARY KEY, name VARCHAR(255)")
     * @throws SQLException SQL 异常
    CREATE TABLE tableName (
    open_price DECIMAL(30, 20),   -- 开盘价格，30位总精度，20位小数
    close_price DECIMAL(30, 20),  -- 关盘价格，30位总精度，20位小数
    high_price DECIMAL(30, 20),   -- 最高价格，30位总精度，20位小数
    low_price DECIMAL(30, 20),    -- 最低价格，30位总精度，20位小数
    volume DECIMAL(30, 10),       -- 成交量，30位总精度，10位小数（根据实际需求调整）
    timestamp TIMESTAMP           -- 时间戳
    );

     */
    public static void createTable(String tableName, String schema) throws SQLException {
        String createSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + schema + ")";
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.execute(createSQL);
        }
    }

    /**
     open DECIMAL(30, 20),   -- 开盘价格，30位总精度，20位小数
     close DECIMAL(30, 20),  -- 关盘价格，30位总精度，20位小数
     high DECIMAL(30, 20),   -- 最高价格，30位总精度，20位小数
     low DECIMAL(30, 20),    -- 最低价格，30位总精度，20位小数
     volume DECIMAL(30, 10),       -- 成交量，30位总精度，10位小数（根据实际需求调整）
     timestamp TIMESTAMP           -- 时间戳
     */
    public static void createCandleTableByName(String tableName){
        try {
            String schema = "open DECIMAL(30, 20), " +
                    "close DECIMAL(30, 20), " +
                    "high DECIMAL(30, 20), " +
                    "low DECIMAL(30, 20), " +
                    "volume DECIMAL(30, 10), " +
                    "timestamp TIMESTAMP";
            createTable(tableName, schema);
            System.out.println("创建表：" + tableName + "成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向指定 table 插入一条或多条数据
     *
     * @param tableName 表名
     * @param columns   列名 (如 "id, name")
     * @param values    值的集合 (如 "(1, 'Alice')", "(2, 'Bob')")
     * @throws SQLException SQL 异常
     */
    public static void insertData(String tableName, String columns, List<String> values) throws SQLException {
        String insertSQL = "INSERT INTO " + tableName + " (" + columns + ") VALUES ";
        insertSQL += String.join(", ", values);
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.execute(insertSQL);
        }
    }

    /**
     * 从指定 table 获取一条或多条数据
     *
     * @param tableName 表名
     * @param columns   要获取的列 (如 "id, name")
     * @param offset    起始位置
     * @param limit     获取的数量
     * @return 结果集
     * @throws SQLException SQL 异常
     */
    public static ResultSet fetchData(String tableName, String columns, int offset, int limit) throws SQLException {
        String query = "SELECT " + columns + " FROM " + tableName + " LIMIT ? OFFSET ?";
        Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, limit);
        statement.setInt(2, offset);
        return statement.executeQuery();
    }

    /**
     * 从指定 table 删除一条或多条数据
     *
     * @param tableName 表名
     * @param condition 删除条件 (如 "id = 1")
     * @throws SQLException SQL 异常
     */
    public static void deleteData(String tableName, String condition) throws SQLException {
        String deleteSQL = "DELETE FROM " + tableName + " WHERE " + condition;
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.execute(deleteSQL);
        }
    }

    /**
     * 测试工具类功能
     */
    public static void main(String[] args) {
        try {
            // 测试表名
            String tableName = "test_users";
            // 1. 创建表
            if (!tableExists(tableName)) {
                createCandleTableByName(tableName);
            }

            /*
             open DECIMAL(30, 20),   -- 开盘价格，30位总精度，20位小数
             close DECIMAL(30, 20),  -- 关盘价格，30位总精度，20位小数
             high DECIMAL(30, 20),   -- 最高价格，30位总精度，20位小数
             low DECIMAL(30, 20),    -- 最低价格，30位总精度，20位小数
             volume DECIMAL(30, 10),       -- 成交量，30位总精度，10位小数（根据实际需求调整）
             timestamp TIMESTAMP           -- 时间戳
             */

            // 2. 插入数据
            String columns = "open, close, high, low, volume, timestamp";

            // 构造符合 SQL 插入语法的值
            String timestamp = "'2024-12-16 12:00:00'"; // 格式化的时间戳，需加单引号
            String value = String.format("(1.22, 1.33, 1.88, 1.66, 888, %s)", timestamp);

            // 调用插入函数
            insertData(tableName, columns, Collections.singletonList(value));
            System.out.println("插入数据结束");

            // 3. 获取数据
            ResultSet resultSet = fetchData(tableName, "open, close, high, low, volume, timestamp", 0, 10);
            while (resultSet.next()) {
                System.out.println("获取数据； open: " + resultSet.getBigDecimal("open") + ", timestamp: " + resultSet.getTimestamp("timestamp"));
            }

            // 4. 删除数据
           // deleteData(tableName, "id = 1");
           // System.out.println("Data deleted.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
