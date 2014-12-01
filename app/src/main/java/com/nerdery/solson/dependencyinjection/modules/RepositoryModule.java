package com.nerdery.solson.dependencyinjection.modules;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.nerdery.solson.data.DatabaseHelper;
import com.nerdery.solson.dependencyinjection.annotations.ForApplication;
import com.nerdery.solson.model.RedditComment;
import com.nerdery.solson.model.RedditLink;
import com.nerdery.solson.repository.RedditCommentRepository;
import com.nerdery.solson.repository.RedditLinkRepository;

import android.content.Context;

import java.sql.SQLException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module for all Database/ORMLite/SQLite providers
 *
 * @author kwatson
 * @author solson
 */
@Module(complete = false, library = true)
public class RepositoryModule {

    @Provides
    @Singleton
    DatabaseHelper providesDatabaseHelper(@ForApplication Context context) {
        return new DatabaseHelper(context);
    }

    @Provides
    @Singleton
    RedditLinkRepository providesLinkRepository(DatabaseHelper databaseHelper) {
        ConnectionSource connectionSource = new AndroidConnectionSource(databaseHelper);
        try {
            Dao<RedditLink, String> linkDao = DaoManager.createDao(connectionSource, RedditLink.class);
            return new RedditLinkRepository(linkDao);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Provides
    @Singleton
    RedditCommentRepository providesCommentRepository(DatabaseHelper databaseHelper) {
        ConnectionSource connectionSource = new AndroidConnectionSource(databaseHelper);
        try {
            Dao<RedditComment, String> commentDao = DaoManager.createDao(connectionSource, RedditComment.class);
            return new RedditCommentRepository(commentDao);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}