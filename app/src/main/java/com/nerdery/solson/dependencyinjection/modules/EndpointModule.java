package com.nerdery.solson.dependencyinjection.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.nerdery.solson.Constants;
import com.nerdery.solson.api.HotLinksEndpoint;
import com.nerdery.solson.api.reddit.DateTimeDeserializer;
import com.nerdery.solson.api.reddit.RedditObjectDeserializer;
import com.nerdery.solson.api.reddit.model.RedditObject;

import org.joda.time.DateTime;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Module for Retrofit API endpoints.
 * @author solson
 */
@Module(complete = false, library = true)
public class EndpointModule {

    @Provides
    @Singleton
    HotLinksEndpoint provideHotLinksEndpoint() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(RedditObject.class, new RedditObjectDeserializer())
                .registerTypeAdapter(DateTime.class, new DateTimeDeserializer())
                .create();
        return new RestAdapter.Builder()
                .setConverter(new GsonConverter(gson))
                .setEndpoint(Constants.ENDPOINT_URL).build().create(HotLinksEndpoint.class);
    }
}
