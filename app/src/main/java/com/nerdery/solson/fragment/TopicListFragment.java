package com.nerdery.solson.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nerdery.solson.data.HotTopicsEndpoint;
import com.nerdery.solson.R;

import javax.inject.Inject;

/**
 * @author solson
 */
public class TopicListFragment extends ListFragment {

    @Inject
    HotTopicsEndpoint mHotTopicsEndpoint;

    public TopicListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.fragment_topic_list_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topic_list, container, false);
    }
}
