package com.hashirbaig.developer.textit.Model;

import android.content.Context;

public class UserData {

    private static UserData sUserData;
    private String mImagePath;
    private String mImageName;
    private String mCroppedImagePath;

    private UserData(Context context) {

    }

    public static UserData get(Context context) {
        if (sUserData == null) {
            sUserData = new UserData(context);
        }
        return sUserData;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    public String getImageName() {
        return mImageName;
    }

    public String getCroppedImagePath() {
        return mCroppedImagePath;
    }

    public void setCroppedImagePath(String croppedImagePath) {
        mCroppedImagePath = croppedImagePath;
    }
}
