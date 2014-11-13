package com.nerdery.solson.activity;

import com.nerdery.solson.R;
import com.nerdery.solson.api.reddit.model.RedditLink;
import com.nerdery.solson.fragment.LinkDetailFragment;

import android.os.Bundle;

public class LinkDetailActivity extends BaseActivity {

    private LinkDetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_detail);

        //pass the RedditLink object on to the fragment
        if (savedInstanceState == null) {
            RedditLink link = (RedditLink) getIntent().getSerializableExtra(LinkDetailFragment.ARG_REDDIT_LINK);
            detailFragment = LinkDetailFragment.newInstance(link);
            getSupportFragmentManager().beginTransaction().add(R.id.link_detail_container, detailFragment).commit();
        }
    }
}
