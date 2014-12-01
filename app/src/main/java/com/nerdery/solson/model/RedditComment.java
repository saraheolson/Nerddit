package com.nerdery.solson.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * To save time implenting the Reddit API (which has TERRIBLE documentation), I took this code from:
 * https://github.com/jacobtabak/droidcon/
 */
public class RedditComment extends RedditSubmission {

    RedditObject replies;

    String subreddit_id;

    String parent_id;

    int controversiality;

    @DatabaseField
    String body;

    String body_html;

    @DatabaseField
    String link_id;

    @DatabaseField
    int depth;

    public RedditObject getReplies() {
        return replies;
    }

    public String getSubredditId() {
        return subreddit_id;
    }

    public String getParentId() {
        return parent_id;
    }

    public int getControversiality() {
        return controversiality;
    }

    public String getBody() {
        return body;
    }

    public String getBodyHtml() {
        return body_html;
    }

    public String getLinkId() {
        return link_id;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
