package com.nerdery.solson.repository;

import com.j256.ormlite.dao.Dao;
import com.nerdery.solson.model.RedditLink;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data repository for links.
 *
 * @author solson
 */
public class RedditLinkRepository extends ObjectRepository {

    private Dao<RedditLink, String> mLinkDao;

    public RedditLinkRepository(Dao<RedditLink, String> linkDao) {
        this.mLinkDao = linkDao;
    }

    public RedditLink find(String linkId) {
        RedditLink link = new RedditLink();
        try {
            link = mLinkDao.queryForId(linkId);
        } catch (SQLException se) {
            onSqlException(se);
        }
        return link;
    }

    public List<RedditLink> findAll() {
        List<RedditLink> links = new ArrayList<RedditLink>();
        try {
            links = mLinkDao.queryForAll();
        } catch (SQLException se) {
            onSqlException(se);
        }
        return links;
    }

    public RedditLink findMostRecentDownload() {
        RedditLink link = null;
        try {
//            link = mLinkDao.queryBuilder().orderBy("downloaded", true)
//                    .queryForFirst();
            link = mLinkDao.queryBuilder().queryForFirst();
        } catch (SQLException se) {
            onSqlException(se);
        }
        return link;
    }

    public void removeAll() {
        removeAll(findAll());
    }

    public void removeAll(List<RedditLink> linksToRemove) {
        try {
            mLinkDao.delete(linksToRemove);
        } catch (SQLException se) {
            onSqlException(se);
        }
    }

    public void saveOrUpdate(RedditLink link) {
        try {
            mLinkDao.createOrUpdate(link);
        } catch (SQLException se) {
            onSqlException(se);
        }
    }

    public void delete(RedditLink link) {
        try {
            mLinkDao.delete(link);
        } catch (SQLException se) {
            onSqlException(se);
        }
    }
}
