package com.nerdery.solson.view;

import com.nerdery.solson.R;
import com.nerdery.solson.model.RedditListing;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by solson on 11/11/14.
 */
public class LinksViewHolder implements ViewHolder {

    @InjectView(R.id.list_item_link_title)
    TextView mTitleView;

    @InjectView(R.id.list_item_link_score)
    TextView mScoreView;

    @InjectView(R.id.list_item_link_posted_info)
    TextView mPostedView;

    @InjectView(R.id.list_item_link_comments)
    TextView mCommentsView;

    @InjectView(R.id.list_item_link_image)
    ImageView mImageView;

    private View parentView;
    private List<RedditListing> mRedditListings;

    public List<RedditListing> getListings() {
        return mRedditListings;
    }

    public void setTopics(List<RedditListing> listings) {
        this.mRedditListings = listings;
    }

    @Override
    public void bindView(View parentView) {
        ButterKnife.inject(this, parentView);
        this.parentView = parentView;
    }

    public TextView getTitleView() {
        return mTitleView;
    }

    public void setTitleText(String title) {
        this.mTitleView.setText(title);
    }

    public TextView getScoreView() {
        return mScoreView;
    }

    public void setScoreText(String score) {
        this.mScoreView.setText(score);
    }

    public TextView getPostedView() {
        return mPostedView;
    }

    public void setPostedText(String posted) {
        this.mPostedView.setText(posted);
    }

    public TextView getCommentsView() {
        return mCommentsView;
    }

    public void setCommentsText(String comments) {
        this.mCommentsView.setText(comments);
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public void setImage(Drawable image) {
        this.mImageView.setImageDrawable(image);
    }
}
