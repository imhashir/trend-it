package com.hashirbaig.developer.textit.Model;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FiltersManager {

    private List<FilterAlbum> mList;
    private static FiltersManager sFiltersManager;
    private Context mContext;
    private FilterAlbum mCurrentAlbum;

    private final String TAG = "BackgroundManager";

    private static final String IMAGE_FOLDER = "filter_images";

    private FiltersManager(Context context) {
        mList = new ArrayList<>();
        mContext = context;
        loadImages();
    }

    private void loadImages(){

        String[] foldersNames = null;
        AssetManager manager = mContext.getAssets();
        try {
            foldersNames = manager.list(IMAGE_FOLDER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<FilterImage> filterImages;
        for(String name : foldersNames) {
            String path =  IMAGE_FOLDER + "/" + name;
            FilterAlbum album = new FilterAlbum(path);
            album.setName(name);
            String[] filterImagesNames = null;
            try {
                filterImagesNames = manager.list(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            filterImages = new ArrayList<>();
            for(String filterName : filterImagesNames) {
                FilterImage image = new FilterImage(path + "/" + filterName);
                image.setName(filterName);
                filterImages.add(image);
            }
            album.setFilterImages(filterImages);
            mList.add(album);
            Log.i(TAG, path);
        }
    }

    public static FiltersManager get(Context context) {
        if(sFiltersManager == null) {
            sFiltersManager = new FiltersManager(context);
        }
        return sFiltersManager;
    }

    public List<FilterImage> getFramesList() {
        return mCurrentAlbum.getFilterImages();
    }

    public List<FilterAlbum> getList() {
        return mList;
    }

    public FilterAlbum getCurrentAlbum() {
        return mCurrentAlbum;
    }

    public void setCurrentAlbum(FilterAlbum currentAlbum) {
        mCurrentAlbum = currentAlbum;
    }
}
