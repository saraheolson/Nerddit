package com.nerdery.solson.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.nerdery.solson.R;
import com.nerdery.solson.fragment.EmptyListFragment;

import butterknife.InjectView;

public class NerdditActivity extends BaseActivity {

//    private EmptyListFragment mEmptyListFragment;
//
//    @InjectView(R.id.frame_topic_detail)
//    FrameLayout mDetailFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nerddit);

        super.setTitle("Nerddit Hot Topics");
//
//        mEmptyListFragment = EmptyListFragment.newInstance();
//        swapDetailView(mEmptyListFragment);
    }

//    private void swapDetailView(Fragment fragment) {
//        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
//        Log.d(this.getClass().getName(), "In swap detail view");
//        //Log.d(this.getClass().getName(), "Detail frame ID: " + mDetailFrame.getId());
//        t.replace(R.id.frame_topic_detail, fragment);
//        Log.d(this.getClass().getName(), "After replace");
//        t.commit();
//    }

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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
