package com.hashirbaig.developer.textit.HostingActivities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.hashirbaig.developer.textit.MainFragment.ImageEditFragment;
import com.hashirbaig.developer.textit.SingleFragmentActivity;

public class ImageEditActivity extends SingleFragmentActivity{

    public static final String EXTRA_PATH = "path_extra";

    public static Intent newIntent(Context context, String path) {
        Intent i = new Intent(context, ImageEditActivity.class);
        i.putExtra(EXTRA_PATH, path);
        return i;
    }

    @Override
    public Fragment createFragment() {
        return ImageEditFragment.newInstance();
    }
}
