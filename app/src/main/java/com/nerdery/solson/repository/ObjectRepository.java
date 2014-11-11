package com.nerdery.solson.repository;

import java.sql.SQLException;

/**
 * @author saraheolson
 */
public class ObjectRepository {
    public void onSqlException(SQLException e) {
        throw new RuntimeException(e); //Not Better...
    }
}
