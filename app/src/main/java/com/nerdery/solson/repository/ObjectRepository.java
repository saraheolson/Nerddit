package com.nerdery.solson.repository;

import java.sql.SQLException;

/**
 * Base class for a repository, which holds a common sql exception method.
 *
 * @author solson
 */
public class ObjectRepository {

    public void onSqlException(SQLException e) {
        throw new RuntimeException(e); //Not Better...
    }
}
