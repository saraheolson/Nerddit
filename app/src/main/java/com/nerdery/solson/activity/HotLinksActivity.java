package com.nerdery.solson.activity;

import com.nerdery.solson.R;
import com.nerdery.solson.fragment.CommentsFragment;
import com.nerdery.solson.fragment.EmptyDetailsFragment;
import com.nerdery.solson.model.RedditLink;
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

public class HotLinksActivity extends BaseActivity implements LinkDetailsController, LinkCommentsController {

    /** The Details frame */
    @InjectView(R.id.frame_link_detail)
    FrameLayout mDetailFrame;

    /** The fragments used in tablet mode */
    private LinkDetailFragment mDetailFragment;
    private EmptyDetailsFragment mEmptyDetailsFragment;
    private CommentsFragment mCommentsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_list);

        // Set the title
        super.setTitle(R.string.activity_hotlinks_title);

        // If the detail view appears, we're on a tablet
        if (findViewById(R.id.frame_link_detail) != null) {

            // Set the tablet property to true
            setTablet(true);

            // Create the initial details fragment to display next to the link list
            mEmptyDetailsFragment = EmptyDetailsFragment.newInstance();
            swapDetailView(mEmptyDetailsFragment);

        } else {

            // Set the tablet property to false
            setTablet(false);
        }
    }

    /**
     * On tablets, swap out the detail view with the provided fragment.
     *
     * @param fragment The details fragment.
     */
    private void swapDetailView(Fragment fragment) {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.frame_link_detail, fragment);
        t.commit();
    }

    /**
     * On tablets, swap out the comments view with the provided fragment.
     *
     * @param fragment The comments fragment.
     */
    private void swapCommentsView(Fragment fragment) {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.frame_link_comments, fragment);
        t.commit();
    }

    /**
     * Add the Nerddit menu to the app.
     *
     * @param menu The menu object.
     *
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nerddit, menu);
        return true;
    }

    /**
     * Handle the action bar menu items functionality.
     *
     * @param item The selected menu item.
     *
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_item_refresh) {

            // Refresh item selected - refresh the list of links.
            LinkListFragment linkListFragment = (LinkListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_link_list);
            linkListFragment.refreshLinks();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Displays the details of a selected link.
     *
     * @param link The selected RedditLink object.
     */
    @Override
    public void updateDetails(RedditLink link) {

        if (isTablet()) {

            // Update the details fragment
            mDetailFragment = LinkDetailFragment.newInstance(link);
            swapDetailView(mDetailFragment);

            //Update the comments fragment
            mCommentsFragment = CommentsFragment.newInstance(link);
            swapCommentsView(mCommentsFragment);

        } else {

            // On phones, navigate to the LinkDetails activity instead
            Intent detailIntent = new Intent(this, LinkDetailActivity.class);
            detailIntent.putExtra(LinkDetailFragment.ARG_REDDIT_LINK, link);
            startActivity(detailIntent);
        }
    }

    @Override
    public void updateComments(RedditLink link) {
        //not used in this activity
    }
}
