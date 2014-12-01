package com.nerdery.solson.model;

import com.google.gson.JsonElement;

import com.nerdery.solson.api.reddit.RedditType;

/**
 * To save time implenting the Reddit API (which has TERRIBLE documentation), I took this code from:
 * https://github.com/jacobtabak/droidcon/
 */
public class RedditObjectWrapper {

    RedditType kind;

    JsonElement data;

    public RedditType getKind() {
        return kind;
    }

    public JsonElement getData() {
        return data;
    }
}
