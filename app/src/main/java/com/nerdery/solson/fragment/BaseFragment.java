package com.nerdery.solson.fragment;


import com.nerdery.solson.api.RedditEndpoint;
import com.nerdery.solson.fragment.dialog.NoConnectionDialogFragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * @author areitz
 */
public abstract class BaseFragment extends Fragment {

    private NoConnectionDialogFragment mNoConnectionDialog;
    private Activity mActivity;

    @Inject
    RedditEndpoint mRedditEndpoint;

    @Inject
    ProgressDialog mProgressDialog;

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
    protected void showDialog() {
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
     * @return
     *          True if user is connected.
     */
    protected boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

}
