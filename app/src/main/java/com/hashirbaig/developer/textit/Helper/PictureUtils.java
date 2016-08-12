package com.hashirbaig.developer.textit.Helper;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.PointF;

public class PictureUtils {

    public static Bitmap getScaledBitmap(Bitmap bitmap, float destWidth, float destHeight) {
        float ratio = 0;
        if(bitmap.getWidth() > bitmap.getHeight()) {
            ratio = bitmap.getWidth() / destWidth;
        } else {
            ratio = bitmap.getHeight() / destHeight;
        }
        int outWidth = (int) Math.floor(bitmap.getWidth() / ratio);
        int outHeight = (int) Math.floor(bitmap.getHeight() / ratio);

        return Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, false);
    }

    public static PointF getBitmapPlace(Bitmap bitmap, float viewWidth, float viewHeight) {
        PointF pointF = new PointF();
        if(bitmap.getHeight() < viewHeight) {
            pointF.x = (viewHeight / 2) - (bitmap.getHeight() / 2);
        }
        if(bitmap.getWidth() < viewWidth) {
            pointF.y = (viewWidth / 2) - (bitmap.getWidth() / 2);
        }
        return pointF;
    }

}
