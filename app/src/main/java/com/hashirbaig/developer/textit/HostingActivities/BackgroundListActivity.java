package com.hashirbaig.developer.textit.HostingActivities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.hashirbaig.developer.textit.MainFragment.BackgroundListFragment;
import com.hashirbaig.developer.textit.SingleFragmentActivity;

/**
 * Created by Hashir on 8/12/2016.
 */
public class BackgroundListActivity extends SingleFragmentActivity{

    @Override
    public Fragment createFragment() {
        return BackgroundListFragment.newInstance();
    }

    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, BackgroundListActivity.class);
        return i;
    }
}
