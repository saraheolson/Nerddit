package com.nerdery.solson.model;

/**
 * To save time implenting the Reddit API (which has TERRIBLE documentation), I took this code from:
 * https://github.com/jacobtabak/droidcon/
 */
public class RedditResponse<T> {

    RedditResponse(T data) {
        this.data = data;
    }

    T data;

    public T getData() {
        return data;
    }
}
