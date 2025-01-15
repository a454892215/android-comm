package com.cand.data_base;

import static com.cand.data_base.H2TableGenerator.generateTable;
import static com.cand.data_base.H2TableGenerator.printTableStructure;

import com.cand.util.LogUtil;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositoryTest {
    public static String tableName = "candle_test";


    @Test
    public void testCreateTable() throws Exception {
        // 示例：生成表
        generateTable(CandleEntity.class, tableName);
        // 示例：打印表结构
        printTableStructure(tableName);
    }

    @Test
    public void testInsert() throws Exception {
        System.out.println("开始插入数据...");
        Connection connect = H2DatabaseUtils.connect();
        Repository repository = new Repository(connect);
        repository.insertEntity(CandleEntity.createSimpleObj(), tableName);
        LogUtil.d(" 插入数据完毕  当前表的数据量是：" + repository.getTableRowCount(tableName));

    }

    @Test
    public void testInsertALotOfData() throws SQLException {
        long start = System.currentTimeMillis();
        System.out.println("开始插入500万条数据...");
        Connection connect = H2DatabaseUtils.connect();
        Repository repository = new Repository(connect);
        for (int i = 0; i < 5000 * 1000; i++) {
            CandleEntity candleEntity = CandleEntity.createSimpleObj();
            repository.insertEntity(candleEntity, tableName);
        }
        long cost = (System.currentTimeMillis() - start) / 1000;
        System.out.println("插入500万数据完毕...cost:" + cost + "秒" + " 当前表的数据量是：" + repository.getTableRowCount(tableName));

    }

    @Test
    public void testInsertALotOfData2() throws Exception {
        long start = System.currentTimeMillis();
        System.out.println("开始插入100万条数据...");
        Connection connect = H2DatabaseUtils.connect();
        Repository repository = new Repository(connect);
        List<CandleEntity> list = new ArrayList<>();
        for (int i = 0; i < 5000 * 1000; i++) {
            CandleEntity candleEntity = CandleEntity.createSimpleObj();
            list.add(candleEntity);
        }
        repository.batchInsertEntities(list, tableName);
        long cost = (System.currentTimeMillis() - start) / 1000;
        System.out.println("批量插入500万数据完毕...cost:" + cost + "秒" + " 当前表的数据量是：" + repository.getTableRowCount(tableName));

    }

    @Test
    public void testFind() throws SQLException {
        System.out.println("开始获取数据库数据...");
        Connection connect = H2DatabaseUtils.connect();
        Repository repository = new Repository(connect);
        List<CandleEntity> list = repository.findAllEntities(CandleEntity.class, tableName, 1000);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + "   " + list.get(i));
        }
        System.out.println("开始范围获取数据库数据...");
        List<CandleEntity> list2 = repository.findDataInRange(CandleEntity.class, tableName, 2, 4);
        for (int i = 0; i < list2.size(); i++) {
            System.out.println(i + "   " + list2.get(i));
        }

        System.out.println("获取数据库数据完毕...");
    }

    @Test
    public void testDelete() {

    }

    @Test
    public void testUpdate() {

    }
}
