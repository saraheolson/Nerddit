package com.nerdery.solson.model;

import java.util.List;

/**
 * To save time implenting the Reddit API (which has TERRIBLE documentation), I took this code from:
 * https://github.com/jacobtabak/droidcon/
 */
public class RedditListing extends RedditObject {

    String modhash;

    String after;

    String before;

    List<RedditObject> children;

    public String getModhash() {
        return modhash;
    }

    public String getAfter() {
        return after;
    }

    public String getBefore() {
        return before;
    }

    public List<RedditObject> getChildren() {
        return children;
    }
}
