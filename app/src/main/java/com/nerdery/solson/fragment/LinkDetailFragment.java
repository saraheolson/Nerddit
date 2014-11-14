package com.nerdery.solson.fragment;

import com.nerdery.solson.R;
import com.nerdery.solson.view.LinksViewHolder;
import com.nerdery.solson.model.RedditLink;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public class LinkDetailFragment extends BaseFragment {

    //required argument names
    public static final String ARG_REDDIT_LINK = "com.nerdery.solson.RedditLink";

    //link object
    private RedditLink mLink;
    private View mView;
    private LinksViewHolder mLinksViewHolder;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param link The RedditLink to display.
     * @return A new instance of fragment TopicDetailFragment.
     */
    public static LinkDetailFragment newInstance(RedditLink link) {
        LinkDetailFragment fragment = new LinkDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_REDDIT_LINK, link);
        fragment.setArguments(args);
        return fragment;
    }

    public LinkDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((mLink == null) && (getArguments() != null)) {
            mLink = (RedditLink) getArguments().getSerializable(ARG_REDDIT_LINK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_link_detail, container, false);
        ButterKnife.inject(this, mView);

        Log.d("TopicDetailFragment", "Link title: " + mLink.getTitle());
        if (mLinksViewHolder == null) {
            mLinksViewHolder = new LinksViewHolder();
        }
        mLinksViewHolder.bindView(mView);
        mLinksViewHolder.setTitleText(mLink.getTitle());

        //load the image or the URL link
        if ((mLink.getUrl() != null) && !mLink.getUrl().isEmpty()) {

            if (isUrlImage(mLink.getUrl())) {
                Picasso.with(getActivity()).load(mLink.getUrl())
                        .into(mLinksViewHolder.getImageView());
            } else {
                //TODO display the link?
                mLinksViewHolder.setUrlView(mLink.getUrl());
            }

        }

        mLinksViewHolder.setPostedText("Posted on " + mLink.getCreatedUtc() + " by: " + mLink.getAuthor());
        mLinksViewHolder.setScoreText("Score: "+mLink.getScore());
        mLinksViewHolder.setCommentsText("Number of comments: " + mLink.getNumComments());

        return mView;
    }

    public boolean isUrlImage(String url) {

        //This isn't terribly robust, since case may not match or jpg may be jpeg, but since
        //these three options were specifically called out in the instructions, I'm considering
        //anything else out of scope.
        if ((url.endsWith(".jpg")) || (url.endsWith(".gif")) || (url.endsWith(".png"))) {
            return true;
        }
        return false;
    }

//    public void viewLinkInBrowser() {
//        Uri uriUrl = Uri.parse(mLink.getUrl());
//        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
//        startActivity(launchBrowser);
//    }
}
