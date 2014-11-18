package com.nerdery.solson.repository;

import com.j256.ormlite.dao.Dao;
import com.nerdery.solson.model.RedditComment;
import com.nerdery.solson.model.RedditLink;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author solson
 */
public class RedditCommentRepository extends ObjectRepository {

    private Dao<RedditComment, String> mCommentDao;

    public RedditCommentRepository(Dao<RedditComment, String> commentDao) {
        this.mCommentDao = commentDao;
    }

    public RedditComment find(String linkId) {
        RedditComment comment = new RedditComment();
        try {
            comment = mCommentDao.queryForId(linkId);
        } catch (SQLException se) {
            onSqlException(se);
        }
        return comment;
    }

    public List<RedditComment> findAll() {
        List<RedditComment> comments = new ArrayList<RedditComment>();
        try {
            comments = mCommentDao.queryForAll();
        } catch (SQLException se) {
            onSqlException(se);
        }
        return comments;
    }

    public void saveOrUpdate(RedditComment comment) {
        try {
            mCommentDao.createOrUpdate(comment);
        } catch (SQLException se) {
            onSqlException(se);
        }
    }
}
