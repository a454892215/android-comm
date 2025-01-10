package com.cand.data_server;

import java.sql.Connection;
import java.sql.SQLException;

public class RepositoryTest {

    public static void main(String[] args) throws SQLException {
        Connection connect = H2DatabaseUtils.connect();
        Repository repository = new Repository(connect);
        repository.insertEntity(new CandleEntity(), "candle_test");
    }

    public static void testInsert(){

    }

    public static void testFind(){

    }

    public static void testDelete(){

    }

    public static void testUpdate(){

    }
}
