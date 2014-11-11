package com.nerdery.solson.dependencyinjection.modules;

import com.nerdery.solson.Constants;
import com.nerdery.solson.api.HotTopicsEndpoint;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Module for Retrofit API endpoints.
 * @author solson
 */
@Module(complete = false, library = true)
public class EndpointModule {

    @Provides
    @Singleton
    HotTopicsEndpoint provideHotTopicsEndpoint() {
        RestAdapter ra = new RestAdapter.Builder().setEndpoint(Constants.ENDPOINT_URL).build();
        return ra.create(HotTopicsEndpoint.class);
    }
}
