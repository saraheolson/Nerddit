package com.nerdery.solson.fragment;


import com.nerdery.solson.api.RedditEndpoint;
import com.nerdery.solson.fragment.dialog.NoConnectionDialogFragment;
import com.nerdery.solson.model.RedditLink;
import com.nerdery.solson.repository.RedditLinkRepository;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Base class for fragments.
 *
 * @author areitz
 * @author solson
 */
public abstract class BaseFragment extends Fragment {

    private static final int CACHE_EXPIRATION_SECONDS = 60;

    /**
     * The maximum number of seconds data should be cached.
     */
    private NoConnectionDialogFragment mNoConnectionDialog;

    private Activity mActivity;

    /**
     * Endpoint class for fragments to access the Reddit API.
     */
    @Inject
    RedditEndpoint mRedditEndpoint;

    /**
     * A Loading dialog for fragments to use while loading data.
     */
    @Inject
    ProgressDialog mProgressDialog;

    /**
     * The data repository for links.
     */
    @Inject
    RedditLinkRepository mRedditLinkRepository;

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Release the views injects by butterknife
        ButterKnife.reset(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    /**
     * Shows a dialog notifying the user that they lack an internet connection. The Dialog will be
     * created if it has not been shown before.
     */
    protected void showNoConnectionDialog() {
        if (mNoConnectionDialog == null) {
            mNoConnectionDialog = new NoConnectionDialogFragment();
        }
        if (!mNoConnectionDialog.isVisible()) {
            mNoConnectionDialog.show(getActivity().getSupportFragmentManager(), "no-connection");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mNoConnectionDialog != null) {
            mNoConnectionDialog.dismiss();
        }
    }

    /**
     * Checks to see if user has a working internet connection.
     *
     * @return True if user is connected.
     */
    protected boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    /**
     * Checks the database to determine if the data should be cached, or downloaded from the API.
     */
    public boolean hasCacheExpired() {

        boolean refresh = true;

        // Get the last link downloaded
        RedditLink mostRecent = mRedditLinkRepository.findMostRecentDownload();

        if (mostRecent != null) {

            /**
             * For some reason, the cached date/times were not working properly. The download
             * date/time is not being updated properly, and I'm not sure yet how to debug the
             * database on the device.
             * Workaround: if cached data exists, use it until the user hits the refresh button.
             */
            refresh = false;

//            // Calculate cache expiration time
//            DateTime cacheExpiration = mostRecent.getCreatedUtc()
//                    .plusSeconds(CACHE_EXPIRATION_SECONDS);
//
//            Log.d(getClass().getName(), "Cache expiration date: " + cacheExpiration
//              + " current date: " + new DateTime());
//
//            //if the new time is before the current time, refresh the data
//            if (cacheExpiration.isBefore(new DateTime())) {
//
//                // Data should be refreshed
//                Log.d(getClass().getName(), "Refresh existing data.");
//
//            } else {
//
//                // Data should not be refreshed
//                Log.d(getClass().getName(), "Cached data still valid. Do not refresh.");
//                refresh = false;
//            }
        }
        return refresh;
    }
}
