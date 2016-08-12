package com.hashirbaig.developer.textit.Model;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FiltersManager {

    private List<FilterImage> mList;
    private static FiltersManager sFiltersManager;
    private Context mContext;

    private final String TAG = "BackgroundManager";

    private static final String IMAGE_FOLDER = "background_images";

    private FiltersManager(Context context) {
        mList = new ArrayList<>();
        mContext = context;
        loadImages();
    }

    private void loadImages(){

        String[] imagesNames = null;
        AssetManager manager = mContext.getAssets();
        try {
            imagesNames = manager.list(IMAGE_FOLDER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String name : imagesNames) {
            String path =  IMAGE_FOLDER + "/" + name;
            FilterImage image = new FilterImage(path);
            image.setName(name);
            mList.add(image);
            Log.i(TAG, path);
        }
    }

    public static FiltersManager get(Context context) {
        if(sFiltersManager == null) {
            sFiltersManager = new FiltersManager(context);
        }
        return sFiltersManager;
    }

    public void add(FilterImage image) {
        mList.add(image);
    }

    public List<FilterImage> getList() {
        return mList;
    }
}
