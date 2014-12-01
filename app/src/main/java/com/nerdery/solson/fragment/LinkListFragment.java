package com.nerdery.solson.fragment;

import com.nerdery.solson.R;
import com.nerdery.solson.activity.BaseActivity;
import com.nerdery.solson.activity.LinkDetailsController;
import com.nerdery.solson.adapter.RedditLinkAdapter;
import com.nerdery.solson.model.RedditLink;
import com.nerdery.solson.model.RedditListing;
import com.nerdery.solson.model.RedditObject;
import com.nerdery.solson.model.RedditResponse;
import com.nerdery.solson.repository.RedditLinkRepository;

import org.joda.time.DateTime;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Display the list of HOT links from the Reddit API.
 *
 * @author solson
 */
public class LinkListFragment extends BaseFragment
        implements Callback<RedditResponse<RedditListing>> {

    /**
     * The adapter for the link list.
     */
    @Inject
    RedditLinkAdapter mRedditLinkAdapter;

    /**
     * The link list view.
     */
    @InjectView(R.id.topic_list_list_view)
    ListView mTopicListView;

    /**
     * The fragment view.
     */
    private View mView;

    /**
     * The controller for updating detail information for a selected link.
     */
    private LinkDetailsController mLinkDetailsController;

    /**
     * Creates a new instance of the fragment.
     */
    public LinkListFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of the fragment.
     *
     * @return fragment
     */
    public static LinkListFragment newInstance() {
        return new LinkListFragment();
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

        // Add the Load More button to the list footer.
        Button loadMoreButton = new Button(getActivity());
        loadMoreButton.setText(R.string.button_load_more_title);
        loadMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //Retrieve the next set of links
                getNext();
            }
        });
        mTopicListView.addFooterView(loadMoreButton);

        // Retrieve the links data
        retrieveData();

        return mView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((BaseActivity) activity).inject(this);

        // Ensure the activity implements the LinkDetailsController
        try {
            mLinkDetailsController = (LinkDetailsController) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement LinkDetailsController.");
        }

    }

    /**
     * On selection of a link, display the details.
     *
     * @param position int index of the selected link.
     */
    @OnItemClick(R.id.topic_list_list_view)
    void onItemClick(int position) {

        // Call the controller to update the details
        RedditLink link = mRedditLinkAdapter.getItem(position);
        mLinkDetailsController.updateDetails(link);
    }

    /**
     * Retrieves the link data from the API or the cache.
     */
    public void retrieveData() {

        boolean retrievedFromApi = false;

        if (hasCacheExpired()) {

            // Cached data has expired. Make the API call.
            retrievedFromApi = retrieveRedditData();
        }

        if (!retrievedFromApi) {

            // Use cached data
            retrieveCachedData();
        }
    }

    /**
     * Retrieves data from the Reddit API.
     */
    public boolean retrieveRedditData() {

        if (isConnected()) {

            // Call the API endpoint to retrieve the hot links.
            mRedditEndpoint.getHot(this);

            // Show the loading dialog to indicate the user should wait.
            mProgressDialog.show();

            // API was called successfully
            return true;

        } else {

            // We couldn't connect, so use cached data.
            return false;
        }
    }

    /**
     * Retrieves cached data from the database.
     */
    public void retrieveCachedData() {

        // Retrieve all links from the database.
        List<RedditLink> links = mRedditLinkRepository.findAll();

        if ((links != null) && links.size() > 0) {

            Log.d(getClass().getName(), "Retrieved links from cache");

            // Set the links from the cache (db)
            mRedditLinkAdapter.setList(links);

            // Set the adapter
            mTopicListView.setAdapter(mRedditLinkAdapter);

        } else {

            //no data, retrieve from api
            if (!retrieveRedditData()) {

                // Couldn't connect, and no cached data, so show an error.
                showNoConnectionDialog();
            }
        }
    }

    /**
     * Refreshes the list of links by calling the API.
     */
    public void refreshLinks() {

        if (isConnected()) {

            // Retrieve data from the API.
            retrieveRedditData();

        } else {

            // No internet connection, show an error.
            showNoConnectionDialog();
        }
    }

    /**
     * Retrieves the next page of links from the API.
     */
    public void getNext() {

        if (isConnected()) {

            //Get the last link from the list.
            RedditLink link = mRedditLinkAdapter.getLastItem();

            // Call the API and pass in the ID of the last link.
            mRedditEndpoint.getNextHot(link.getName(), this);

            // Show the loading dialog.
            mProgressDialog.show();

        } else {

            // Cannot load more without an internet connection, so show an error dialog.
            showNoConnectionDialog();
        }
    }



    /**
     * Callback after a successful API call to load the received data.
     *
     * @param listing  The RedditListing object.
     * @param response The response.
     */
    @Override
    public void success(RedditResponse<RedditListing> listing, Response response) {
        //if (getActivity().isDestroyed()) return;
        mProgressDialog.dismiss();
        onListingReceived(listing);
    }

    /**
     * Callback after a failed API call.
     *
     * @param error The error.
     */
    @Override
    public void failure(RetrofitError error) {
        //if (getActivity().isDestroyed()) return;
        mProgressDialog.dismiss();
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.api_links_failure)
                .setCancelable(false)
                .setNeutralButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .show();
    }

    /**
     * Loads the listing data received from the API.
     *
     * @param listing The RedditListing object.
     */
    private void onListingReceived(RedditResponse<RedditListing> listing) {

        // Clear all data from the current list and from the db
        mRedditLinkAdapter.clear();
        mRedditLinkRepository.removeAll();

        // Loop through all RedditLink objects
        for (RedditObject redditObject : listing.getData().getChildren()) {

            // Get the link object
            RedditLink link = (RedditLink) redditObject;
            Log.d(getClass().getName(), "Retrieved link from API: Link Title: " + link.getTitle());

            // Set the downloaded date/time
            link.setDownloaded(new DateTime());

            // Add the link to the list
            mRedditLinkAdapter.addLink(link);

            // Save the link to the db
            mRedditLinkRepository.saveOrUpdate(link);
        }

        // Set the adapter
        mTopicListView.setAdapter(mRedditLinkAdapter);
    }
}
