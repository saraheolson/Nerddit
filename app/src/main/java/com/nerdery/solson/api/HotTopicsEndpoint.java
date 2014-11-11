package com.nerdery.solson.api;

import com.nerdery.solson.model.Topic;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by saraheolson on 11/10/14.
 */
public interface HotTopicsEndpoint {

    @GET("/r/hot.json")
    public void fetchHotTopics( Callback<TopicHolder> callback );
}
