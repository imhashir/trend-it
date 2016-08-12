package com.hashirbaig.developer.textit.HostingActivities;

import android.support.v4.app.Fragment;

import com.hashirbaig.developer.textit.MainFragment.HomeFragment;
import com.hashirbaig.developer.textit.SingleFragmentActivity;

public class HomeActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return HomeFragment.newInstance();
    }
}
