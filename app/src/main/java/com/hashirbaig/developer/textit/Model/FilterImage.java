package com.hashirbaig.developer.textit.Model;

public class FilterImage {

    private String mFullPath;
    private String mPath;
    private String mName;

    public FilterImage(String path) {
        mPath = path;
        mFullPath = "file:///android_asset/" + path;
    }

    public String getPath() {
        return mPath;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getFullPath() {
        return mFullPath;
    }
}
