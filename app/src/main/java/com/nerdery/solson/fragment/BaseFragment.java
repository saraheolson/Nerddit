package com.nerdery.solson.fragment;


import android.support.v4.app.Fragment;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * @author areitz
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Release the views injects by butterknife
        ButterKnife.reset(this);
    }
}
