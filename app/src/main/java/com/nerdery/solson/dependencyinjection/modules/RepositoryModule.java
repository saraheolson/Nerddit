package com.nerdery.solson.dependencyinjection.modules;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.nerdery.solson.data.DatabaseHelper;
import com.nerdery.solson.dependencyinjection.annotations.ForApplication;
import com.nerdery.solson.model.Topic;
import com.nerdery.solson.repository.TopicRepository;

import android.content.Context;

import java.sql.SQLException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module for all Database/ORMLite/SQLite providers
 *
 * @author Kenton Watson
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
    TopicRepository providesProductRepository(DatabaseHelper databaseHelper) {
        ConnectionSource connectionSource = new AndroidConnectionSource(databaseHelper);
        try {
            Dao<Topic, Long> topicDao = DaoManager.createDao(connectionSource, Topic.class);
            return new TopicRepository(topicDao);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}