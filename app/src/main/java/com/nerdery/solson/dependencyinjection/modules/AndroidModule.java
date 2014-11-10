package com.nerdery.solson.dependencyinjection.modules;

import com.nerdery.solson.NerdditApplication;
import com.nerdery.solson.dependencyinjection.annotations.ForApplication;
import com.nerdery.solson.logging.LogCatLogger;
import com.nerdery.solson.logging.Logger;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A module for Android-specific dependencies which require a {@link android.content.Context} or
 * {@link android.app.Application} to create.  Also may be used for singleton objects, such as the
 * logger
 */
@Module(library = true)
public class AndroidModule {

    /**
     * SharedPreferences name
     */
    public static final String PREFERENCE_NAME = AndroidModule.class
            .getPackage()
            .getName() +
            "Preferences";

    private final NerdditApplication mApplication;

    public AndroidModule(NerdditApplication application) {
        mApplication = checkNotNull(application);
    }

    /**
     * Allow the application context to be injected but require that it be annotated with {@link
     * com.nerdery.solson.dependencyinjection.annotations.ForApplication @Annotation} to explicitly differentiate it from an activity context.
     */
    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return mApplication.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    Logger provideLoggingService() {
        return new LogCatLogger(mApplication);
    }
}
