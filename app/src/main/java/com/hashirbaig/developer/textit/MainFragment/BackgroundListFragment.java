package com.hashirbaig.developer.textit.MainFragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hashirbaig.developer.textit.HostingActivities.ImageEditActivity;
import com.hashirbaig.developer.textit.Model.FilterImage;
import com.hashirbaig.developer.textit.Model.FiltersManager;
import com.hashirbaig.developer.textit.Model.UserData;
import com.hashirbaig.developer.textit.R;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class BackgroundListFragment extends Fragment{

    private static final String TAG = "BackgroundListFragment";
    private RecyclerView mRecyclerView;
    private PhotoAdapter mAdapter;
    private String mFramePath;
    private Fragment mFragment;

    public static BackgroundListFragment newInstance() {
        return new BackgroundListFragment();
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
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
            mAdapter = new PhotoAdapter(FiltersManager.get(getActivity()).getList());
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class PhotoHolder extends RecyclerView.ViewHolder
                                    implements View.OnClickListener{

        private ImageView mImageView;
        private FilterImage mImage;

        public PhotoHolder(View v) {
            super(v);
            mImageView = (ImageView) v.findViewById(R.id.back_image);
            v.setOnClickListener(this);
        }

        public void bindImage(FilterImage image) {
            mImage = image;
            Glide.with(getActivity())
                    .load(Uri.parse(image.getFullPath()))
                    .into(mImageView);
        }

        @Override
        public void onClick(View view) {

            String folder = Environment.getExternalStorageDirectory() + "/TrendIt/Cropped/";
            String croppedImage = "raw.jpg";
            File folderFile = new File(folder);
            if(!folderFile.exists()) {
                folderFile.mkdirs();
            }
            AssetManager assetManager = getActivity().getAssets();

            InputStream iStream = null;
            try {
                iStream = assetManager.open(mImage.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            mFramePath = mImage.getPath();
            Bitmap bitmap = BitmapFactory.decodeStream(iStream).copy(Bitmap.Config.ARGB_8888, true);
            Uri destinationUri = Uri.fromFile(new File(folder + croppedImage));

            UCrop.Options uCropOptions = new UCrop.Options();
            uCropOptions.setToolbarColor(getResources().getColor(R.color.colorPrimary));
            uCropOptions.withAspectRatio(bitmap.getWidth(), bitmap.getHeight());
            uCropOptions.withMaxResultSize(bitmap.getWidth(), bitmap.getHeight());
            uCropOptions.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            UCrop.of(Uri.fromFile(new File(UserData.get(getActivity()).getImagePath())), destinationUri)
                    .withOptions(uCropOptions)
                    .start(getActivity(), mFragment, UCrop.REQUEST_CROP);

        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

        private List<FilterImage> mImages;

        public PhotoAdapter(List<FilterImage> images) {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "Entered onActivityResult");
        if(resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case UCrop.REQUEST_CROP:
                Log.i(TAG, "Result: Image Cropped");
                Uri resultUri = UCrop.getOutput(data);
                UserData.get(getActivity()).setCroppedImagePath(resultUri.getPath());
                Intent i = ImageEditActivity.newIntent(getActivity(), mFramePath);
                startActivity(i);
                break;
            case UCrop.RESULT_ERROR:
                Log.e(TAG, "Result: Image Not Cropped");
                break;
        }
    }
}
