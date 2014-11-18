package com.nerdery.solson.activity;

import com.nerdery.solson.R;
import com.nerdery.solson.fragment.CommentsFragment;

import android.os.Bundle;

public class CommentsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        //pass the RedditLink ID on to the fragment
        if (savedInstanceState == null) {
            String linkId = getIntent().getStringExtra(CommentsFragment.ARG_REDDIT_LINK_ID);
            CommentsFragment commentsFragment = CommentsFragment.newInstance(linkId);
            getSupportFragmentManager().beginTransaction().add(R.id.link_comments_container, commentsFragment).commit();
        }
    }
}
