package com.nerdery.solson.api;

import com.nerdery.solson.model.RedditListing;
import com.nerdery.solson.model.RedditResponse;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * @author solson
 */
public interface HotLinksEndpoint {

    @GET("/hot.json")
    public void getHot(
            Callback<RedditResponse<RedditListing>> callback);

    @GET("/hot.json")
    public RedditResponse<RedditListing> getHot();
}
