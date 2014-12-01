package com.nerdery.solson.model;

import java.util.List;

/**
 * To save time implenting the Reddit API (which has TERRIBLE documentation), I took this code from:
 * https://github.com/jacobtabak/droidcon/
 */
public class RedditMore extends RedditObject {

    int count;

    String parent_id;

    String id;

    String name;

    List<String> children;

    public int getCount() {
        return count;
    }

    public String getParentId() {
        return parent_id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getChildren() {
        return children;
    }
}
