package com.nerdery.solson.fragment;

import com.nerdery.solson.R;
import com.nerdery.solson.TopicsAdapter;
import com.nerdery.solson.activity.BaseActivity;
import com.nerdery.solson.api.HotTopicsEndpoint;
import com.nerdery.solson.api.TopicHolder;
import com.nerdery.solson.model.Topic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author solson
 */
public class TopicListFragment extends BaseFragment implements Callback<TopicHolder> {

    @Inject
    HotTopicsEndpoint mHotTopicsEndpoint;

    @Inject
    TopicsAdapter mTopicsAdapter;

    @Inject
    ProgressDialog mProgressDialog;

    @InjectView(R.id.topic_list_list_view)
    ListView mTopicListView;

    private View mView;

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
        mView = inflater.inflate(R.layout.fragment_topic_list, container, false);
        ButterKnife.inject(this, mView);

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTopicsAdapter.notifyDataSetInvalidated();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((BaseActivity) activity).inject(this);

        getTopics();
    }

    private void getTopics() {
//        if (isConnected()) {
            mHotTopicsEndpoint.fetchHotTopics(this);
            mProgressDialog.show();
//        } else {
//            showDialog();
//        }
    }

    public static TopicListFragment newInstance() {
        return new TopicListFragment();
    }

    @Override
    public void success(TopicHolder topicHolder, Response response) {
        mProgressDialog.dismiss();
        mTopicsAdapter.setList(topicHolder.getTopics());
        mTopicListView.setAdapter(mTopicsAdapter);
    }

    @Override
    public void failure(RetrofitError error) {
        error.printStackTrace();
        Log.d("TopicListFragment", "Retrofit error: " + error.toString());
        mProgressDialog.dismiss();
        showDialog();
    }
}
