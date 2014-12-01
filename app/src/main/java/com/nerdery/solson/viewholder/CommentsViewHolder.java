package com.nerdery.solson.viewholder;

import com.nerdery.solson.R;
import com.nerdery.solson.model.RedditComment;

import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Viewholder for comments list.
 *
 * @author solson
 */
public class CommentsViewHolder implements ViewHolder {

    /**
     * View for body text.
     */
    @InjectView(R.id.list_item_comment_body)
    TextView mBodyView;

    /**
     * Cell view.
     */
    private View parentView;

    /**
     * List of comments.
     */
    private List<RedditComment> mComments;

    /**
     * Returns the list of comments.
     *
     * @return List of RedditComment objects.
     */
    public List<RedditComment> getComments() {
        return mComments;
    }

    /**
     * Sets the list of comments.
     *
     * @param comments List of RedditComment objects.
     */
    public void setComments(List<RedditComment> comments) {
        this.mComments = comments;
    }

    /**
     * Injects the view into the parent.
     *
     * @param parentView Parent view.
     */
    @Override
    public void bindView(View parentView) {
        ButterKnife.inject(this, parentView);
        this.parentView = parentView;
    }

    /**
     * Returns the view for the comment body text.
     *
     * @return The TextView body view.
     */
    public TextView getBodyView() {
        return mBodyView;
    }

    /**
     * Sets the text for the comment body view.
     *
     * @param bodyText String body text.
     */
    public void setBodyText(String bodyText) {
        this.mBodyView.setText(bodyText);
    }
}
