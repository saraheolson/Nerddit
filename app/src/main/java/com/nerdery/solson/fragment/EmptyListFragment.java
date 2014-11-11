package com.nerdery.solson.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nerdery.solson.R;

/**
 * @author saraheolson
 */
public class EmptyListFragment extends BaseFragment {

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_empty_list, container, false);
        return mView;
    }

    public static EmptyListFragment newInstance() { return new EmptyListFragment(); }
}
