package com.nerdery.solson.fragment;


import com.nerdery.solson.R;
import com.nerdery.solson.adapter.RedditCommentAdapter;
import com.nerdery.solson.model.RedditComment;
import com.nerdery.solson.model.RedditListing;
import com.nerdery.solson.model.RedditObject;
import com.nerdery.solson.model.RedditResponse;
import com.nerdery.solson.repository.RedditCommentRepository;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CommentsFragment extends BaseFragment implements
        Callback<RedditResponse<RedditListing>> {

    public static final String ARG_REDDIT_LINK_ID = "com.nerdery.solson.RedditLinkID";

    @Inject
    RedditCommentAdapter mCommentAdapter;

    @Inject
    RedditCommentRepository mCommentRepository;

    @InjectView(R.id.comment_list_list_view)
    ListView mCommentListView;

    private String mLinkId;
    private View mView;

    /**
     *
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param linkId The ID of the RedditLink.
     * @return A new instance of fragment TopicDetailFragment.
     */
    public static CommentsFragment newInstance(String linkId) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_REDDIT_LINK_ID, linkId);
        fragment.setArguments(args);
        return fragment;
    }

    public CommentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((mLinkId == null) && (getArguments() != null)) {
            mLinkId = getArguments().getString(ARG_REDDIT_LINK_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_comments, container, false);
        ButterKnife.inject(this, mView);

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        Log.d("Comments", "LinkID: " + mLinkId);
        retrieveData();
    }

    /**
     * Retrieves data from the Reddit API.
     */
    public void retrieveData() {
        if (isConnected()) {

            if (mLinkId != null) {
                mRedditEndpoint.getComments(mLinkId, this);
                //mRedditEndpoint.getHot(this);
                mProgressDialog.show();
            }
        } else {
            retrieveCachedData();
        }
    }

    private void retrieveCachedData() {
        Log.d("CommentsFragment","Retrieve cached data");
    }

    @Override
    public void success(RedditResponse<RedditListing> commentResponse,
            Response response) {
        mProgressDialog.dismiss();
        onCommentReceived(commentResponse);
    }

    @Override
    public void failure(RetrofitError error) {
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

    private void onCommentReceived(RedditResponse<RedditListing> commentResponse) {
        mCommentAdapter.clear();
        for (RedditObject object : commentResponse.getData().getChildren()) {
            RedditComment comment = (RedditComment) object;
            Log.d("CommentFragment", "Retrieve from HTTP: Comment Body: " + comment.getBody());
            mCommentAdapter.addCommentToList(comment);
            mCommentListView.setAdapter(mCommentAdapter);

            //save to db
            mCommentRepository.saveOrUpdate(comment);
        }
    }

}
