package com.nerdery.solson.activity;

import com.nerdery.solson.R;
import com.nerdery.solson.fragment.LinkDetailFragment;
import com.nerdery.solson.model.RedditLink;

import android.content.Intent;
import android.os.Bundle;

public class LinkDetailActivity extends BaseActivity implements LinkCommentsController {

    /* The request code for the Comments activity request */
    public static final int COMMENT_REQUEST_CODE = 1;

    /* The detail fragment */
    private LinkDetailFragment detailFragment;

    /* The selected RedditLink object */
    private RedditLink mLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_detail);

        // Set the title
        super.setTitle(R.string.activity_details_title);

        // Pass the RedditLink object on to the fragment
        if ((savedInstanceState == null) && (mLink == null)) {
            mLink = (RedditLink) getIntent()
                    .getSerializableExtra(LinkDetailFragment.ARG_REDDIT_LINK);
            detailFragment = LinkDetailFragment.newInstance(mLink);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.link_detail_container, detailFragment).commit();
        }
    }

    /**
     * Navigates to the Comments activity.
     *
     * @param link The selected RedditLink object.
     */
    @Override
    public void updateComments(RedditLink link) {

        if (link != null) {

            // Start the comments activity.
            Intent commentsIntent = new Intent(this, CommentsActivity.class);
            commentsIntent.putExtra(LinkDetailFragment.ARG_REDDIT_LINK, link);
            startActivityForResult(commentsIntent, COMMENT_REQUEST_CODE);
        }
    }

    /**
     * Resets the selected RedditLink after navigating back from the Comments activity.
     *
     * @param requestCode The request code for the Comments request.
     * @param resultCode  The result code from Comments.
     * @param data        The RedditLink object.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // We only care about the Comments request
        if (requestCode == COMMENT_REQUEST_CODE) {

            // Get the selected RedditLink object
            mLink = (RedditLink) data.getSerializableExtra(LinkDetailFragment.ARG_REDDIT_LINK);
            if ((mLink != null) && (detailFragment != null)) {

                // Set the selected RedditLink on the fragment
                detailFragment.setLink(mLink);
            }
        }
    }
}
