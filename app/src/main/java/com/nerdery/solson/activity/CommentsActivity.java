package com.nerdery.solson.activity;

import com.nerdery.solson.R;
import com.nerdery.solson.fragment.CommentsFragment;
import com.nerdery.solson.fragment.LinkDetailFragment;
import com.nerdery.solson.model.RedditLink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class CommentsActivity extends BaseActivity {

    private RedditLink mLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        // Set the title
        super.setTitle(R.string.activity_commments_title);

        // Pass the RedditLink ID on to the fragment
        if (savedInstanceState == null) {
            mLink = (RedditLink) getIntent().getSerializableExtra(
                    LinkDetailFragment.ARG_REDDIT_LINK);
            CommentsFragment commentsFragment = CommentsFragment.newInstance(mLink);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.link_comments_container, commentsFragment).commit();
        }
    }

    /**
     * Handles any calls to the action bar's menu items.
     *
     * @param item The item selected by the user.
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                // pass the selected RedditLink back to the link detail screen
                Intent i = new Intent();
                i.putExtra(LinkDetailFragment.ARG_REDDIT_LINK, mLink);
                this.setResult(Activity.RESULT_OK, i);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
