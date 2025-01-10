package com.cand.data_server;

import static com.cand.data_server.H2TableGenerator.generateTable;
import static com.cand.data_server.H2TableGenerator.printTableStructure;

import java.sql.Connection;
import java.sql.SQLException;

public class RepositoryTest {
    public static String tableName = "candle_test";

    public static void main(String[] args) throws Exception {
        testCreateTable();
        testInsert();
    }

    public static void testCreateTable() throws Exception {
        // 示例：生成表
        generateTable(CV.JDBC_URL, CandleEntity.class, tableName);
        // 示例：打印表结构
        printTableStructure(CV.JDBC_URL, tableName);
    }

    public static void testInsert() throws SQLException {
        System.out.println("开始插入数据...");
        Connection connect = H2DatabaseUtils.connect();
        Repository repository = new Repository(connect);
        CandleEntity candleEntity = CandleEntity.createSimpleObj();
        repository.insertEntity(candleEntity, tableName);
        System.out.println("插入数据成功..." + candleEntity.timestamp);
    }

    public static void testFind(){

    }

    public static void testDelete(){

    }

    public static void testUpdate(){

    }
}
