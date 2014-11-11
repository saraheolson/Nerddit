package com.nerdery.solson.activity;

import com.nerdery.solson.NerdditApplication;
import com.nerdery.solson.dependencyinjection.modules.ActivityModule;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import dagger.ObjectGraph;

/**
 * @author Andrew
 */

public abstract class BaseActivity extends FragmentActivity {

    private ObjectGraph mActivityGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inject objects into the object graph at the activity level, this is for
        // objects that need values that aren't available until the activity is created.
        NerdditApplication application = NerdditApplication.getInstance();
        mActivityGraph = application
                .getApplicationGraph()
                .plus(
                        getModules().toArray()
                );

        mActivityGraph.inject(this);
    }

    @Override
    protected void onDestroy() {
        // Eagerly clear the reference to the activity graph to allow
        // it to be garbage collected as soon as possible.
        mActivityGraph = null;
        super.onDestroy();
    }

    protected List<Object> getModules() {
        return Arrays.<Object>asList(
                new ActivityModule(this)
        );
    }


    /**
     * Inject the supplied {@code object} using the activity-specific graph.
     */
    public void inject(Object object) {
        mActivityGraph.inject(object);
    }
}
