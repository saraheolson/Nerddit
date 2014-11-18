package com.nerdery.solson.activity;

import com.nerdery.solson.R;
import com.nerdery.solson.fragment.CommentsFragment;
import com.nerdery.solson.model.RedditLink;
import com.nerdery.solson.fragment.EmptyListFragment;
import com.nerdery.solson.fragment.LinkDetailFragment;
import com.nerdery.solson.fragment.LinkListFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import butterknife.InjectView;

public class HotLinksActivity extends BaseActivity implements MasterDetailController {

    @InjectView(R.id.frame_link_detail)
    FrameLayout mDetailFrame;

    private LinkDetailFragment mDetailFragment;
    private EmptyListFragment mEmptyListFragment;
    private CommentsFragment mCommentsFragment;

    private boolean mIsTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_list);

        super.setTitle("Nerddit Hot Topics");

        if (findViewById(R.id.frame_link_detail) != null) {
            Log.d("HotLinksActivity", "Is tablet");
            mIsTablet = true;
            mEmptyListFragment = EmptyListFragment.newInstance();
            swapDetailView(mEmptyListFragment);

            mCommentsFragment = new CommentsFragment();
            swapCommentsView(mCommentsFragment);
        } else {
            Log.d("HotLinksActivity", "Is not tablet");
        }
    }

    private void swapDetailView(Fragment fragment) {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.frame_link_detail, fragment);
        t.commit();
    }

    private void swapCommentsView(Fragment fragment) {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.frame_link_comments, fragment);
        t.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nerddit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            //TODO remove this
            return true;
        } else if (id == R.id.menu_item_refresh) {

            /** Refresh the list of links. **/
            LinkListFragment linkListFragment = (LinkListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_link_list);
            linkListFragment.retrieveData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateDetails(RedditLink link) {

        if (mIsTablet) {

            // Update the details
            mDetailFragment = LinkDetailFragment.newInstance(link);
            swapDetailView(mDetailFragment);

            //Update the comments
            mCommentsFragment = CommentsFragment.newInstance(link.getId());
            swapCommentsView(mCommentsFragment);

        } else {
            Intent detailIntent = new Intent(this, LinkDetailActivity.class);
            detailIntent.putExtra(LinkDetailFragment.ARG_REDDIT_LINK, link);
            startActivity(detailIntent);
        }
    }

    @Override
    public void setActionBarTitle(String title) {
        super.setTitle(title);
    }
}
