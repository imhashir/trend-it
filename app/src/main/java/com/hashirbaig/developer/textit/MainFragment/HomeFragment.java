package com.hashirbaig.developer.textit.MainFragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hashirbaig.developer.textit.Dialogs.DialogChooseImage;
import com.hashirbaig.developer.textit.R;

public class HomeFragment extends Fragment{

    private ImageView mBrowseButton;

    private static final String TAG_IMAGE_CHOOSER = "image_chooser_dialog";
    private static final int REQUEST_STORAGE_PERMISSION = 2001;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_STORAGE_PERMISSION) {
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                getActivity().finishAffinity();
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION);
        }
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mBrowseButton = (ImageView) v.findViewById(R.id.create_your_image);

        mBrowseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DialogChooseImage dialog = DialogChooseImage.newInstance();
                dialog.show(fm, TAG_IMAGE_CHOOSER);
            }
        });

        return v;
    }
}
