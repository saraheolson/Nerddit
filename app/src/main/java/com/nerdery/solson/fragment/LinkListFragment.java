package com.nerdery.solson.fragment;

import com.nerdery.solson.R;
import com.nerdery.solson.activity.BaseActivity;
import com.nerdery.solson.activity.MasterDetailController;
import com.nerdery.solson.adapter.RedditLinkAdapter;
import com.nerdery.solson.api.HotLinksEndpoint;
import com.nerdery.solson.api.reddit.model.RedditLink;
import com.nerdery.solson.api.reddit.model.RedditListing;
import com.nerdery.solson.api.reddit.model.RedditObject;
import com.nerdery.solson.api.reddit.model.RedditResponse;
import com.nerdery.solson.repository.RedditLinkRepository;

import org.joda.time.DateTime;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author solson
 */
public class LinkListFragment extends BaseFragment
        implements Callback<RedditResponse<RedditListing>> {

    private static final int DEFAULT_CACHE_LIMIT = 60;

    @Inject
    HotLinksEndpoint mHotLinksEndpoint;

    @Inject
    RedditLinkRepository mRedditLinkRepository;

    @Inject
    RedditLinkAdapter mRedditLinkAdapter;

    @Inject
    ProgressDialog mProgressDialog;

    @InjectView(R.id.topic_list_list_view)
    ListView mTopicListView;

    private View mView;
    private MasterDetailController mMasterDetailController;

    public LinkListFragment() {
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
        mView = inflater.inflate(R.layout.fragment_link_list, container, false);
        ButterKnife.inject(this, mView);

        retrieveData();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRedditLinkAdapter.notifyDataSetInvalidated();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((BaseActivity) activity).inject(this);
        try {
            mMasterDetailController = (MasterDetailController) activity;
        }  catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MasterDetailController.");
        }

    }

    @OnItemClick(R.id.topic_list_list_view)
    void onItemClick(int position) {

        RedditLink link = mRedditLinkAdapter.getItem(position);
        mMasterDetailController.updateDetails(link);
    }

    public void retrieveData() {

        if (isConnected()) {

            if (hasCacheExpired()) {
                retrieveRedditData();
            } else {
                retrieveCachedData();
            }
        } else {
            //showDialog();
            retrieveCachedData();
        }
    }

    /**
     * Retrieves data from the Reddit API.
     */
    public void retrieveRedditData() {
        //get the hot links data from Reddit
        mHotLinksEndpoint.getHot(this);
        mProgressDialog.show();
    }

    /**
     * Retrieves cached data from the database.
     */
    public void retrieveCachedData() {

        Log.d("TopicListFragment", "Retrieving cached data");
        List<RedditLink> links = mRedditLinkRepository.findAll();

        if ((links != null) && links.size() > 0) {
            //set the links from the cache (db)
            mRedditLinkAdapter.setList(links);
            mTopicListView.setAdapter(mRedditLinkAdapter);

        } else {
            //no data, retrieve from api
            retrieveRedditData();
        }
    }

    /**
     * Checks the database to determine if the data should be cached, or downloaded from the API.
     *
     */
    private boolean hasCacheExpired() {

        boolean refresh = true;

        RedditLink mostRecent = mRedditLinkRepository.findMostRecentDownload();

        if (mostRecent != null) {
            //if the new time is before the current time, refresh the data
            if (mostRecent.getCreatedUtc().isBefore(new DateTime())) {
                //refresh
                Log.d("TopicListFragment", "Refresh data");
            } else {
                //no refresh
                Log.d("TopicListFragment", "NO refreshing data");
                refresh = false;
            }
        }
        return refresh;
    }

    public static LinkListFragment newInstance() {
        return new LinkListFragment();
    }


    @Override
    public void success(RedditResponse<RedditListing> listing, Response response) {
        //if (getActivity().isDestroyed()) return;
        mProgressDialog.dismiss();
        onListingReceived(listing);
    }

    @Override
    public void failure(RetrofitError error) {
        //if (getActivity().isDestroyed()) return;
        mProgressDialog.dismiss();
        new AlertDialog.Builder(getActivity())
                .setMessage("Loading failed :(")
                .setCancelable(false)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .show();
    }

    private void onListingReceived(RedditResponse<RedditListing> listing) {
        mRedditLinkAdapter.clear();
        for (RedditObject redditObject : listing.getData().getChildren()) {
            RedditLink link = (RedditLink) redditObject;
            //link.setDownloaded(new DateTime());
            Log.d("TopicListFragment", "Retrieve from HTTP: Link Title: " + link.getTitle());
            mRedditLinkAdapter.addLink(link);
            mTopicListView.setAdapter(mRedditLinkAdapter);

            //save to db
            mRedditLinkRepository.saveOrUpdate(link);
        }
    }
}
