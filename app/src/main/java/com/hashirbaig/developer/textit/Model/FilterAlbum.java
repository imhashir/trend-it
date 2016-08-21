package com.hashirbaig.developer.textit.Model;

import java.util.List;

public class FilterAlbum {

    private String mName;
    private String mPath;
    private String mFullPath;
    private List<FilterImage> mFilterImages;

    public FilterAlbum(String path) {
        mPath = path;
        mFullPath = "file:///android_asset/" + path;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public List<FilterImage> getFilterImages() {
        return mFilterImages;
    }

    public void setFilterImages(List<FilterImage> filterImages) {
        mFilterImages = filterImages;
    }

    public String getFullPath() {
        return mFullPath;
    }

    public void setFullPath(String fullPath) {
        mFullPath = fullPath;
    }
}
