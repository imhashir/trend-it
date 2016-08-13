package com.hashirbaig.developer.textit.AsyncTasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.hashirbaig.developer.textit.Drawing.DrawView;
import com.hashirbaig.developer.textit.Helper.PictureUtils;

public class RotateImageAsync extends AsyncTask<Object, Void, Void> {

    private DrawView mDrawView;
    private Bitmap mUserImage;
    private Bitmap mFinalImage;

    @Override
    protected Void doInBackground(Object... key) {

        Integer tag = (Integer) key[0];
        mDrawView = (DrawView) key[1];
        mUserImage = (Bitmap) key[2];

        if(tag == DrawView.ROTATE_LEFT) {
            mFinalImage = PictureUtils.rotateBitmap(mUserImage, 90);
        } else {
            mFinalImage = PictureUtils.rotateBitmap(mUserImage, 270);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mDrawView.setUserOriginalImage(mFinalImage);
        mDrawView.invalidate();
    }
}
