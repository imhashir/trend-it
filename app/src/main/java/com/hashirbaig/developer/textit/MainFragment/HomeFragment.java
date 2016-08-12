package com.hashirbaig.developer.textit.MainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hashirbaig.developer.textit.HostingActivities.BackgroundListActivity;
import com.hashirbaig.developer.textit.R;

public class HomeFragment extends Fragment{

    private Button mBrowseButton;

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

        mBrowseButton = (Button) v.findViewById(R.id.button_browse_backgrounds);

        mBrowseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = BackgroundListActivity.newIntent(getActivity());
                startActivity(i);
            }
        });

        return v;
    }
}
