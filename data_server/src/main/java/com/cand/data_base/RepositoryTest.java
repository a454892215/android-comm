package com.cand.data_base;

import static com.cand.data_base.H2TableGenerator.generateTable;
import static com.cand.data_base.H2TableGenerator.printTableStructure;

import com.cand.util.LogUtil;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositoryTest {
    public static String tableName = "candle_test";

    @Before
    public void testTableExistsAndCreate() throws Exception {
        boolean exists = Repository.exists(tableName);
        if (!exists) {
            // 示例：生成表
            generateTable(CandleEntity.class, tableName);
            // 示例：打印表结构
            printTableStructure(tableName);
        }
    }

    @Test
    public void testInsert() throws Exception {
        System.out.println("开始插入数据...");
        Repository repository = new Repository();
        repository.insertEntity(CandleEntity.createSimpleObj(), tableName);
        LogUtil.d("插入数据完毕  当前表的数据量是：" + repository.getTableRowCount(tableName));

    }

    @Test
    public void testInsertALotOfData() throws SQLException {
        long start = System.currentTimeMillis();
        System.out.println("开始插入100万条数据...");
        Repository repository = new Repository();
        for (int i = 0; i < 5000 * 1000; i++) {
            CandleEntity candleEntity = CandleEntity.createSimpleObj();
            repository.insertEntity(candleEntity, tableName);
        }
        long cost = (System.currentTimeMillis() - start) / 1000;
        System.out.println("插入100万数据完毕...cost:" + cost + "秒" + " 当前表的数据量是：" + repository.getTableRowCount(tableName));

    }

    @Test
    public void testInsertALotOfData2() throws Exception {
        long start = System.currentTimeMillis();
        Repository repository = new Repository();
        List<CandleEntity> list = new ArrayList<>();
        System.out.println("开始插入100万条数据...");
        for (int i = 0; i < 5000 * 1000; i++) {
            CandleEntity candleEntity = CandleEntity.createSimpleObj();
            list.add(candleEntity);
        }
        int ret = repository.batchInsertEntities(list, tableName);
        long cost = (System.currentTimeMillis() - start) / 1000;
        System.out.println("批量插入100万数据完毕...cost:" + cost + "秒" + " 当前表的数据量是：" + repository.getTableRowCount(tableName) + " ret:" + ret);

    }

    @Test
    public void testFind() throws SQLException {
        Repository repository = new Repository();
        int tableRowCount = repository.getTableRowCount(tableName);
        System.out.println("开始获取数据库数据...tableRowCount:" + tableRowCount);
        List<CandleEntity> list = repository.findAllEntities(CandleEntity.class, tableName, 1000);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + "   " + list.get(i));
        }
        System.out.println("开始范围获取数据库数据...2-4");
        List<CandleEntity> list2 = repository.findDataInRange(CandleEntity.class, tableName, 2, 4);
        for (int i = 0; i < list2.size(); i++) {
            System.out.println(i + "   " + list2.get(i));
        }
        System.out.println("获取数据库数据完毕...");
        CandleEntity lastEntity = repository.getNewestInsertEntity(CandleEntity.class, tableName);
        System.out.println("最新插入的数据是：" + lastEntity);
    }

    @Test
    public void testDelete() {

    }

    @Test
    public void testUpdate() {

    }
}
