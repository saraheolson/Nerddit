package com.nerdery.solson.view;

import com.nerdery.solson.R;
import com.nerdery.solson.model.RedditComment;
import com.nerdery.solson.model.RedditListing;

import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * @author solson
 */
public class CommentsViewHolder implements ViewHolder {

    @InjectView(R.id.list_item_comment_body)
    TextView mBodyView;

    private View parentView;
    private List<RedditComment> mComments;

    public List<RedditComment> getComments() {
        return mComments;
    }

    public void setComments(List<RedditComment> comments) {
        this.mComments = comments;
    }

    @Override
    public void bindView(View parentView) {
        ButterKnife.inject(this, parentView);
        this.parentView = parentView;
    }

    public TextView getBodyView() {
        return mBodyView;
    }

    public void setBodyText(String bodyText) {
        this.mBodyView.setText(bodyText);
    }

}
