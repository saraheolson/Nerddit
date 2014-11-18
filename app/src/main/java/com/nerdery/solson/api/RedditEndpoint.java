package com.nerdery.solson.api;

import com.nerdery.solson.model.RedditListing;
import com.nerdery.solson.model.RedditResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * The Reddit endpoints for the API calls.
 *
 * @author solson
 */
public interface RedditEndpoint {

    /**
     * First call to the Hot list, gets the first page of items.
     *
     * @param callback The object receiving the callback response.
     */
    @GET("/hot.json")
    public void getHot(
            Callback<RedditResponse<RedditListing>> callback);

    /**
     * Subsequent calls to the hot list, returns the next page of items.
     *
     * @param fullname The name of the last item received (from previous page).
     * @param callback The object receiving the callback response.
     */
    @GET("/hot.json")
    public void getNextHot(@Query("after") String fullname,
            Callback<RedditResponse<RedditListing>> callback);

    /**
     * Returns the comments for the selected link.
     *
     * @param linkId The ID of the link for the comments.
     * @param callback The object receiving the callback response.
     */
    @GET("/comments/{id}.json")
    public void getComments(@Path("id") String linkId,
            Callback<RedditResponse<RedditListing>> callback);
}
