package com.cand.data_server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 写一个 H2 Database 工具类， 具有的功能函数有
   0. 连接到数据库
   1. 判断指定名字的table是否存在
   2. 创建指定名字的table
   3. 向指定table插入一条数据或者多条数据
   4. 从指定table，指定位置开始获取一条或者多条数据
   5. 从指定table指定位置删除一条或者多条数据
 */
public class H2DataBase {

    public void connect(){
        // JDBC URL（这里使用文件模式）
        String jdbcUrl = "jdbc:h2:./data/testdb";
        String username = "sa"; // 默认用户名
        String password = "";   // 默认无密码
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement()) {
            // 创建表
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY, name VARCHAR(100))";
            statement.execute(createTableSQL);
            // 插入数据
            String insertSQL = "INSERT INTO users (id, name) VALUES (1, 'Alice'), (2, 'Bob')";
            statement.execute(insertSQL);
            // 查询数据
            String querySQL = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(querySQL);

            // 遍历结果
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println("ID: " + id + ", Name: " + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTable(String tableName){

    }
}
