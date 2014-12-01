package com.nerdery.solson.data;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.nerdery.solson.model.RedditComment;
import com.nerdery.solson.model.RedditLink;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Creates tables for the cached Reddit data objects.
 *
 * @author saraheolson
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "nerddit.db";

    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, RedditLink.class);
            TableUtils.createTable(connectionSource, RedditComment.class);
        } catch (SQLException se) {
            throw new RuntimeException(se);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion,
            int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, RedditLink.class, true);
            TableUtils.dropTable(connectionSource, RedditComment.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException se) {
            throw new RuntimeException(se);
        }
    }

    @Override
    public void close() {
        super.close();
    }
}
