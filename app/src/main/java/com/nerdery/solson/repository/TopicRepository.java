package com.nerdery.solson.repository;

import com.j256.ormlite.dao.Dao;
import com.nerdery.solson.model.Topic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author saraheolson
 */
public class TopicRepository extends ObjectRepository {

    private Dao<Topic, Long> topicDao;

    public Topic find(Long topicId) {
        Topic topic = new Topic();
        try {
            topic = topicDao.queryForId(topicId);
        } catch (SQLException se) {
            onSqlException(se);
        }
        return topic;
    }

    public List<Topic> findAll() {
        List<Topic> topics = new ArrayList<Topic>();
        try {
            topics = topicDao.queryForAll();
        } catch (SQLException se) {
            onSqlException(se);
        }
        return topics;
    }

    public void removeAll(List<Topic> topicsToRemove) {
        try {
            topicDao.delete(topicsToRemove);
        } catch (SQLException se) {
            onSqlException(se);
        }
    }

    public void saveOrUpdate(Topic topic) {
        try {
            topicDao.createOrUpdate(topic);
        } catch (SQLException se) {
            onSqlException(se);
        }
    }

    public void delete(Topic topic) {
        try {
            topicDao.delete(topic);
        } catch (SQLException se) {
            onSqlException(se);
        }
    }
}
