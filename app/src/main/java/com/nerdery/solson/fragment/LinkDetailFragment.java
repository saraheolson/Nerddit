package com.nerdery.solson.fragment;

import com.nerdery.solson.R;
import com.nerdery.solson.activity.BaseActivity;
import com.nerdery.solson.activity.LinkCommentsController;
import com.nerdery.solson.model.RedditLink;
import com.nerdery.solson.viewholder.LinksViewHolder;
import com.squareup.picasso.Picasso;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class LinkDetailFragment extends BaseFragment {

    /**
     * Key for the link argument.
     */
    public static final String ARG_REDDIT_LINK = "com.nerdery.solson.RedditLink";

    /**
     * Selected link.
     */
    private RedditLink mLink;

    /**
     * Fragment view.
     */
    private View mView;

    /**
     * Viewholder for the link details.
     */
    private LinksViewHolder mLinksViewHolder;

    /**
     * For navigating to comments view (on phones).
     */
    private LinkCommentsController mLinkCommentsController;

    private DateTimeFormatter mDateTimeFormatter;

    /**
     * Creates a new instance of this fragment using the provided link.
     *
     * @param link The RedditLink to display.
     * @return A new instance of fragment.
     */
    public static LinkDetailFragment newInstance(RedditLink link) {
        LinkDetailFragment fragment = new LinkDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_REDDIT_LINK, link);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creates a new instance of the fragment.
     */
    public LinkDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the link object from the bundle.
        if ((mLink == null) && (getArguments() != null)) {
            mLink = (RedditLink) getArguments().getSerializable(ARG_REDDIT_LINK);
        }

        // Create the date/time formatter.
        mDateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_link_detail, container, false);
        ButterKnife.inject(this, mView);

        // Create the view
        if (mLinksViewHolder == null) {
            mLinksViewHolder = new LinksViewHolder();
        }
        mLinksViewHolder.bindView(mView);

        // Get string resources
        String postedResources = getString(R.string.link_list_posted_info);
        String numComments = getString(R.string.link_list_num_comments);

        //Create a linked title and set the link & title text
        TextView titleView = mLinksViewHolder.getTitleView();
        titleView.setClickable(true);
        titleView.setMovementMethod(LinkMovementMethod.getInstance());
        mLinksViewHolder.setTitleText(Html.fromHtml("<a href=\"http://www.reddit.com" + mLink.getPermalink() + "\">" + mLink.getTitle() + "<\\a>"));
        //Linkify.addLinks(mLinksViewHolder.getTitleView(), Linkify.WEB_URLS);

        //Populate the other text views
        mLinksViewHolder.setCommentsText(String.format(numComments, mLink.getNumComments()));
        mLinksViewHolder.setPostedText(
                String.format(postedResources, mDateTimeFormatter.print(mLink.getCreatedUtc()),
                        mLink.getAuthor()));
        mLinksViewHolder.setScoreText(Integer.toString(mLink.getScore()));

        // If the URL is an image, display it. Otherwise display the URL.
        if ((mLink.getUrl() != null) && !mLink.getUrl().isEmpty()) {

            if (isUrlImage(mLink.getUrl())) {

                // Display the image
                Picasso.with(getActivity()).load(mLink.getUrl())
                        .into(mLinksViewHolder.getImageView());
            } else {

                // Not an image, so display the URL
                mLinksViewHolder.setUrlView(mLink.getUrl());
            }
        }

        return mView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((BaseActivity) activity).inject(this);

        // Make sure the activity implements LinkCommentsController
        try {
            mLinkCommentsController = (LinkCommentsController) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement LinkCommentsController.");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Save the link for future use
        savedInstanceState.putSerializable(ARG_REDDIT_LINK, mLink);
    }

    /**
     * Returns true if the specified URL is an image.
     *
     * NOTE: This isn't terribly robust, since case may not match or jpg may be jpeg, but since
     * these three options were specifically called out in the instructions, I'm considering
     * anything else out of scope.
     *
     * @param url The URL.
     * @return True if the URL is an image.
     */
    public boolean isUrlImage(String url) {

        // In a real app, I'd use constants and also ignore case.
        if ((url.endsWith(".jpg")) || (url.endsWith(".gif")) || (url.endsWith(".png"))) {
            return true;
        }
        return false;
    }

    /**
     * The onlick method for the View Comments button will call the controller to display the
     * comments for the specified link.
     */
    @Optional
    @OnClick(R.id.fragment_details_view_comments_button)
    void onClick() {
        mLinkCommentsController.updateComments(mLink);
    }

    /**
     * Sets the selected link for this fragment.
     *
     * @param link The RedditLink to display.
     */
    public void setLink(RedditLink link) {
        this.mLink = link;
    }
}
