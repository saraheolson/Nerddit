package com.nerdery.solson.fragment;

import com.nerdery.solson.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * This fragment is displayed in the details area on tablets before a link has been selected.
 *
 * @author solson
 */
public class EmptyDetailsFragment extends BaseFragment {

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_empty_list, container, false);
        return mView;
    }

    public static EmptyDetailsFragment newInstance() { return new EmptyDetailsFragment(); }
}