package com.hashirbaig.developer.textit.MainFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hashirbaig.developer.textit.Dialogs.DialogChooseImage;
import com.hashirbaig.developer.textit.R;

public class HomeFragment extends Fragment{

    private ImageView mBrowseButton;

    private static final String TAG_IMAGE_CHOOSER = "image_chooser_dialog";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
