package com.hashirbaig.developer.textit.MainFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hashirbaig.developer.textit.AsyncTasks.RotateImageAsync;
import com.hashirbaig.developer.textit.Drawing.DrawView;
import com.hashirbaig.developer.textit.HostingActivities.ImageEditActivity;
import com.hashirbaig.developer.textit.R;

public class ImageEditFragment extends Fragment{

    private Button mRotateRight;
    private Button mRotateLeft;
    private DrawView mDrawView;
    private String mPath;
    private String mName;

    private static final String TAG_DIALOG_NAME = "enter_name_dialog_fragment";
    private static final int REQUEST_DIALOG_NAME = 1001;

    public static ImageEditFragment newInstance() {
        return new ImageEditFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPath = getActivity().getIntent().getStringExtra(ImageEditActivity.EXTRA_PATH);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_image, container, false);

        mDrawView = (DrawView) v.findViewById(R.id.draw_view);
        mRotateRight = (Button) v.findViewById(R.id.button_rotate_right);
        mRotateLeft = (Button) v.findViewById(R.id.button_rotate_left);

        mRotateLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RotateImageAsync().execute(DrawView.ROTATE_LEFT, mDrawView, mDrawView.getUserOriginalImage());
            }
        });

        mRotateRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RotateImageAsync().execute(DrawView.ROTATE_RIGHT, mDrawView, mDrawView.getUserOriginalImage());
            }
        });

        mDrawView.setImagePath(mPath);

        return v;
    }
}
