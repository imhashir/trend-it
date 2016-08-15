package com.hashirbaig.developer.textit.HostingActivities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ConfigurationHelper;

import com.hashirbaig.developer.textit.MainFragment.AboutFragment;
import com.hashirbaig.developer.textit.SingleFragmentActivity;

public class AboutActivity extends SingleFragmentActivity{
    @Override
    public Fragment createFragment() {
        return AboutFragment.newInstance();
    }

    public static Intent newIntent(Context context) {
        Intent i = new Intent(context, AboutActivity.class);
        return i;
    }
}
