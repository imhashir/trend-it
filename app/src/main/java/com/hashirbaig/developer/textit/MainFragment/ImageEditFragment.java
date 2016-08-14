package com.hashirbaig.developer.textit.MainFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hashirbaig.developer.textit.AsyncTasks.RotateImageAsync;
import com.hashirbaig.developer.textit.AsyncTasks.SaveImageAsync;
import com.hashirbaig.developer.textit.Dialogs.EnterNameDialog;
import com.hashirbaig.developer.textit.Drawing.DrawView;
import com.hashirbaig.developer.textit.HostingActivities.ImageEditActivity;
import com.hashirbaig.developer.textit.R;

public class ImageEditFragment extends Fragment{

    private DrawView mDrawView;
    private String mPath;
    private String mName;

    private static final String TAG = "ImageEditFragment";
    private static final String TAG_DIALOG_NAME = "enter_name_dialog_fragment";
    private static final int REQUEST_DIALOG_NAME = 1001;

    public static ImageEditFragment newInstance() {
        return new ImageEditFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPath = getActivity().getIntent().getStringExtra(ImageEditActivity.EXTRA_PATH);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_image, container, false);

        mDrawView = (DrawView) v.findViewById(R.id.draw_view);
        mDrawView.setImagePath(mPath);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_edit_image, menu);
    }

    private String getNameFromPath(String imagePath) {
        return imagePath.substring(imagePath.lastIndexOf("/") + 1, imagePath.lastIndexOf("."));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save_image:
                Log.i(TAG, "Save clicked");
                FragmentManager fm = getActivity().getSupportFragmentManager();
                EnterNameDialog dialog = EnterNameDialog.newInstance(getNameFromPath(mPath));
                dialog.setTargetFragment(this, REQUEST_DIALOG_NAME);
                dialog.show(fm, TAG_DIALOG_NAME);
                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case REQUEST_DIALOG_NAME:
                mName = data.getStringExtra(EnterNameDialog.EXTRA_NAME);
                Log.i(TAG, "Save Name Set");
                new SaveImageAsync().execute(getActivity(), mName, mDrawView.getFinalImage());
                break;
        }
    }
}
