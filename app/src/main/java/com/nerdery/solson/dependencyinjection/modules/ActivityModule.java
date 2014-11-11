package com.nerdery.solson.dependencyinjection.modules;

/**
 * @author areitz
 */

import com.nerdery.solson.R;
import com.nerdery.solson.TopicsAdapter;
import com.nerdery.solson.activity.TopicListActivity;
import com.nerdery.solson.dependencyinjection.annotations.ForActivity;
import com.nerdery.solson.activity.BaseActivity;
import com.nerdery.solson.fragment.EmptyListFragment;
import com.nerdery.solson.fragment.TopicDetailFragment;
import com.nerdery.solson.fragment.TopicListFragment;

import android.app.ProgressDialog;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * This module represents objects which exist only for the scope of a single activity. We can safely
 * create singletons using the activity instance because ths entire object graph will only ever
 * exist inside of that activity.
 */
@Module(
        injects = {
                TopicListActivity.class,
                TopicDetailFragment.class,
                TopicListFragment.class,
                EmptyListFragment.class,
                TopicsAdapter.class,
        },
        includes = {
                EndpointModule.class,
                RepositoryModule.class,
        },
        addsTo = AndroidModule.class,
        library = true
)
public class ActivityModule {

    private final BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    /**
     * Allow the activity context to be injected but require that it be annotated with {@link
     * {package}.dependencyinjection.annotations.ForActivity @ForActivity} to explicitly differentiate it from
     * application context.
     */
    @Provides
    @Singleton
    @ForActivity
    Context provideActivityContext() {
        return activity;
    }

    @Provides
    @Singleton
    ProgressDialog provideProgressDialog(@ForActivity Context context) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage(context.getResources().getString(R.string.progress_dialog_message));
        return pd;
    }
}
