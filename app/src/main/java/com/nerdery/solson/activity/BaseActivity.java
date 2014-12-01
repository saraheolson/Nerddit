package com.nerdery.solson.activity;

import com.nerdery.solson.NerdditApplication;
import com.nerdery.solson.dependencyinjection.modules.ActivityModule;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * @author andrew
 * @author solson
 */

public abstract class BaseActivity extends ActionBarActivity {

    private Activity mActivity;

    private ObjectGraph mActivityGraph;

    private boolean finishActivity;

    private boolean mIsTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Set up object graph before calling super.onCreate so we guarantee mObjectGraph is non-null before child fragments' onAttach is called
        injectSelf();

        super.onCreate(savedInstanceState);
    }

    private void injectSelf() {

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
        if (mActivityGraph != null) {
            mActivityGraph.inject(object);
        }
    }

    /**
     * Checks to see if user has a working internet connection.
     *
     * @return True if user is connected.
     */
    protected boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    protected void setFinishActivity(boolean finishActivity) {
        this.finishActivity = finishActivity;
    }

    /**
     * Returns true if the app is running on a tablet.
     *
     * @return True if on tablet.
     */
    public boolean isTablet() {
        return mIsTablet;
    }

    /**
     * Sets the tablet property.
     *
     * @param isTablet boolean
     */
    public void setTablet(boolean isTablet) {
        mIsTablet = isTablet;
    }
}
