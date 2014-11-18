package com.nerdery.solson.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.nerdery.solson.R;
import com.nerdery.solson.fragment.LinkDetailFragment;
import com.nerdery.solson.model.RedditLink;

public class CommentsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_link_detail);
//
//        //pass the RedditLink object on to the fragment
//        if (savedInstanceState == null) {
//            RedditLink link = (RedditLink) getIntent().getSerializableExtra(LinkDetailFragment.ARG_REDDIT_LINK);
//            detailFragment = LinkDetailFragment.newInstance(link);
//            getSupportFragmentManager().beginTransaction().add(R.id.link_detail_container, detailFragment).commit();
//        }
//    }

}
