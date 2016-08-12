package com.hashirbaig.developer.textit.MainFragment;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hashirbaig.developer.textit.HostingActivities.ImageEditActivity;
import com.hashirbaig.developer.textit.Model.BackgroundImage;
import com.hashirbaig.developer.textit.Model.BackgroundsManager;
import com.hashirbaig.developer.textit.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class BackgroundListFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private PhotoAdapter mAdapter;

    public static BackgroundListFragment newInstance() {
        return new BackgroundListFragment();
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_background, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.list_container);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        updateUI();

        return v;
    }

    public void updateUI() {
        if(mAdapter == null) {
            mAdapter = new PhotoAdapter(BackgroundsManager.get(getActivity()).getList());
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class PhotoHolder extends RecyclerView.ViewHolder
                                    implements View.OnClickListener{

        private ImageView mImageView;
        private BackgroundImage mImage;

        public PhotoHolder(View v) {
            super(v);
            mImageView = (ImageView) v.findViewById(R.id.back_image);
            v.setOnClickListener(this);
        }

        public void bindImage(BackgroundImage image) {
            mImage = image;
            Glide.with(getActivity())
                    .load(Uri.parse(image.getFullPath()))
                    .into(mImageView);
        }

        @Override
        public void onClick(View view) {
            Intent i = ImageEditActivity.newIntent(getActivity(), mImage.getPath());
            startActivity(i);
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

        private List<BackgroundImage> mImages;

        public PhotoAdapter(List<BackgroundImage> images) {
            mImages = images;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.single_image_view, parent, false);

            return new PhotoHolder(v);
        }

        @Override
        public void onBindViewHolder(PhotoHolder holder, int position) {
            holder.bindImage(mImages.get(position));
        }

        @Override
        public int getItemCount() {
            return mImages.size();
        }
    }
}
