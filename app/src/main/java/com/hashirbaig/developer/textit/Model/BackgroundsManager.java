package com.hashirbaig.developer.textit.Model;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BackgroundsManager {

    private List<BackgroundImage> mList;
    private static BackgroundsManager sBackgroundsManager;
    private Context mContext;

    private final String TAG = "BackgroundManager";

    private static final String IMAGE_FOLDER = "background_images";

    private BackgroundsManager(Context context) {
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
            BackgroundImage image = new BackgroundImage(path);
            image.setName(name);
            mList.add(image);
            Log.i(TAG, path);
        }
    }

    public static BackgroundsManager get(Context context) {
        if(sBackgroundsManager == null) {
            sBackgroundsManager = new BackgroundsManager(context);
        }
        return sBackgroundsManager;
    }

    public void add(BackgroundImage image) {
        mList.add(image);
    }

    public List<BackgroundImage> getList() {
        return mList;
    }
}
