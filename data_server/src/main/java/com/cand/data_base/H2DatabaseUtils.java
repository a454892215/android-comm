package com.cand.data_base;


import com.cand.util.LogUtil;

/**
 * 写一个 H2 Database 工具类， 具有的功能函数有
 * 0. 连接到数据库
 * 1. 判断指定名字的table是否存在
 */
public class H2DatabaseUtils {

    public void generateTable(Class<?> entityClass, String tableName) throws Exception {
        if (Repository.exists(tableName)) {
            LogUtil.d(tableName + "表，在数据库已经存在");
        } else {
            H2TableGenerator.generateTable(entityClass, tableName);
            H2TableGenerator.printTableStructure(tableName);
        }

    }

    public <T> int insertEntity(T entity, String tableName) throws Exception {
        Repository repository = new Repository();
        if (!Repository.exists(tableName)) {
            H2TableGenerator.generateTable(entity.getClass(), tableName);
        }
        return repository.insertEntity(entity, tableName);
    }

    public <T> T getLastEntity(String tableName, Class<T> clazz) throws Exception {
        Repository repository = new Repository();
        if (!Repository.exists(tableName)) {
            H2TableGenerator.generateTable(clazz, tableName);
        }
        return repository.getNewestInsertEntity(clazz, tableName);
    }

    public static void main(String[] args) throws Throwable {
        H2DatabaseUtils utils = new H2DatabaseUtils();
        utils.generateTable(CandleEntity.class, "test_table");
    }
}
