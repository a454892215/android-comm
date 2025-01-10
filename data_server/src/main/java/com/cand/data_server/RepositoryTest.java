package com.cand.data_server;

import java.sql.Connection;
import java.sql.SQLException;

public class RepositoryTest {

    public static void main(String[] args) throws SQLException {
        Connection connect = H2DatabaseUtils.connect();
        GenericRepository repository = new GenericRepository(connect);
        repository.insertEntity(new CandleEntity(), "candle_test");
    }
}
