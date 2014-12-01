package com.nerdery.solson.viewholder;

import com.nerdery.solson.R;
import com.nerdery.solson.model.RedditListing;

import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Viewholder for links list.
 *
 * @author solson
 */
public class LinksViewHolder implements ViewHolder {

    /**
     * Link title view.
     */
    @InjectView(R.id.list_item_link_title)
    TextView mTitleView;

    /**
     * Link score view.
     */
    @InjectView(R.id.list_item_link_score)
    TextView mScoreView;

    /**
     * Link date/time and author view.
     */
    @InjectView(R.id.list_item_link_posted_info)
    TextView mPostedView;

    /**
     * Link number of comments view.
     */
    @InjectView(R.id.list_item_link_comments)
    TextView mCommentsView;

    /**
     * Link image view.
     */
    @InjectView(R.id.list_item_link_image)
    ImageView mImageView;

    /**
     * (Optional) Link URL view.
     */
    @Optional
    @InjectView(R.id.list_item_link_url)
    TextView mUrlView;

    /**
     * The cell view.
     */
    private View parentView;

    /**
     * The list of links.
     */
    private List<RedditListing> mRedditListings;

    /**
     * Binds the view to its parent.
     *
     * @param parentView The parent view.
     */
    @Override
    public void bindView(View parentView) {
        ButterKnife.inject(this, parentView);
        this.parentView = parentView;
    }

    public List<RedditListing> getListings() {
        return mRedditListings;
    }

    public void setLinks(List<RedditListing> listings) {
        this.mRedditListings = listings;
    }

    public TextView getTitleView() {
        return mTitleView;
    }

    public void setTitleText(Spanned title) {
        this.mTitleView.setText(title);
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

    public TextView getUrlView() {
        return mUrlView;
    }

    public void setUrlView(String url) {
        this.mUrlView.setText(url);
    }

}
