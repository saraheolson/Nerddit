package com.nerdery.solson.fragment;


import com.nerdery.solson.R;
import com.nerdery.solson.activity.BaseActivity;
import com.nerdery.solson.adapter.RedditCommentAdapter;
import com.nerdery.solson.model.RedditComment;
import com.nerdery.solson.model.RedditLink;
import com.nerdery.solson.model.RedditListing;
import com.nerdery.solson.model.RedditObject;
import com.nerdery.solson.model.RedditResponse;
import com.nerdery.solson.repository.RedditCommentRepository;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Displays a list of comments for a ReddtLink.
 *
 * @author solson
 */
public class CommentsFragment extends BaseFragment implements
        Callback<List<RedditResponse<RedditListing>>> {

    /**
     * Adapter for the comment list.
     */
    @Inject
    RedditCommentAdapter mCommentAdapter;

    /**
     * Data repository for the comments.
     */
    @Inject
    RedditCommentRepository mCommentRepository;

    /**
     * Comment list view.
     */
    @InjectView(R.id.comment_list_list_view)
    ListView mCommentListView;

    /**
     * Selected link.
     */
    private RedditLink mLink;

    /**
     * Fragment view
     */
    private View mView;

    /**
     * Creates a new instance of the fragment with a RedditLink object.
     *
     * @param link The RedditLink.
     * @return A new instance of the fragment.
     */
    public static CommentsFragment newInstance(RedditLink link) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putSerializable(LinkDetailFragment.ARG_REDDIT_LINK, link);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creates a new instance of the fragment.
     */
    public CommentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the link from the bundle.
        if ((mLink == null) && (getArguments() != null)) {
            mLink = (RedditLink) getArguments().getSerializable(LinkDetailFragment.ARG_REDDIT_LINK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_comments, container, false);
        ButterKnife.inject(this, mView);

        // Add the Load More button to the list footer
        Button loadMoreButton = new Button(getActivity());
        loadMoreButton.setText("Load More");
        loadMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                retrieveNext();
            }
        });
        mCommentListView.addFooterView(loadMoreButton);

        // If on tablet, add a header.
        if (((BaseActivity) getActivity()).isTablet()) {
            TextView headerView = new TextView(getActivity());
            headerView.setText("Comments");
            headerView.setTextSize(12f);
            mCommentListView.addHeaderView(headerView);
        }

        return mView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mLink != null) {
            // Retrieve the data from either the API or the cache (database).
            retrieveData();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Inject this fragment into the activity
        ((BaseActivity) activity).inject(this);
    }

    /**
     * Retrieves and loads comments data either from the API or from the cache.
     */
    private void retrieveData() {

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

    private boolean retrieveRedditData() {

        if (isConnected()) {

            Log.d(getClass().getName(), "Retrieved comments from the API.");

            // Make the API call
            mRedditEndpoint.getComments(mLink.getId(), this);

            // Display the progress dialog
            mProgressDialog.show();

            return true;
        }
        return false;
    }

    /**
     * Retrieves cached data from the repository.
     */
    private void retrieveCachedData() {

        List<RedditComment> comments = mCommentRepository.findAll( mLink.getName() );

        if ((comments != null) && comments.size() > 0) {

            Log.d(getClass().getName(), "Retrieved comments from cache");

            // Set the links from the cache (db)
            mCommentAdapter.setList(comments);

            // Set the adapter
            mCommentListView.setAdapter(mCommentAdapter);

        } else {

            //no data, retrieve from api
            retrieveRedditData();
        }
    }

    /**
     * Loads more comments.
     */
    public void retrieveNext() {
        /*
        This API call was so poorly documented and strange that I decided not to implement it.
        Joshua Beardsley OK'd my decision.

        //RedditComment comment = mCommentAdapter.getLastItem();
        //mRedditEndpoint.getNextComments(mLink.getId(), this);
        //mProgressDialog.show();
        */

        //showing a message to alert the user the functionality has not been implemented
        Toast.makeText(getActivity(),
                "Due to the absurdity of the Reddit API, this call was not implemented.",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * After a succesful API call, this method will load the comments data.
     *
     * @param commentResponse The API response object containing the data.
     * @param response        The response object.
     */
    @Override
    public void success(List<RedditResponse<RedditListing>> commentResponse,
            Response response) {
        mProgressDialog.dismiss();
        onCommentReceived(commentResponse);
    }

    /**
     * After a failed API call, this method will be called to display the error message.
     *
     * @param error The error object.
     */
    @Override
    public void failure(RetrofitError error) {

        // Remove the loading dialog
        mProgressDialog.dismiss();

        // Display an error
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.api_comments_failure)
                .setCancelable(false)
                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    /**
     * Loads the comment data received from the Reddit API.
     *
     * @param listings The comment data object.
     */
    private void onCommentReceived(List<RedditResponse<RedditListing>> listings) {

        // Remove any current data from the list of comments
        mCommentAdapter.clear();

        // Get the RedditLink object
        RedditObject linkObject = listings.get(0).getData().getChildren().get(0);
        RedditLink link = (RedditLink) linkObject;

        // Load the comments from the link
        addCommentsToAdapter(listings.get(1).getData().getChildren());

        // Set the adapter on the list view
        mCommentListView.setAdapter(mCommentAdapter);
    }

    /**
     * Parses the list of comments and adds each one to the list.
     *
     * @param children List of comment objects
     */
    private void addCommentsToAdapter(List<RedditObject> children) {
        for (RedditObject child : children) {
            if (child instanceof RedditComment) {
                RedditComment comment = (RedditComment) child;

                // Add comment to the list
                mCommentAdapter.addCommentToList(comment);

                // Save comment to the database
                mCommentRepository.saveOrUpdate(comment);
            }
        }
    }
}

