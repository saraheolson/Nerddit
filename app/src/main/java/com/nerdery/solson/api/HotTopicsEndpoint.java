package com.nerdery.solson.api;

import retrofit.http.GET;

/**
 * Created by saraheolson on 11/10/14.
 */
public interface HotTopicsEndpoint {

    @GET("/hot")
    public void fetchHotTopics();
}
