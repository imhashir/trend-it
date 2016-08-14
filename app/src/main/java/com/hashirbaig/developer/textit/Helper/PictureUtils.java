package com.hashirbaig.developer.textit.Helper;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;

public class PictureUtils {

    public static final int SCALE_FILL = 1001;
    public static final int SCALE_FIT = 1002;

    public static Bitmap getScaledBitmap(Bitmap bitmap, int x, int y, float destWidth, float destHeight, int flag) {
        float ratio;
        if(flag == SCALE_FIT) {
            if (bitmap.getWidth() >= bitmap.getHeight()) {
                ratio = bitmap.getWidth() / destWidth;
            } else {
                ratio = bitmap.getHeight() / destHeight;
            }
            int outWidth = (int) Math.floor(bitmap.getWidth() / ratio);
            int outHeight = (int) Math.floor(bitmap.getHeight() / ratio);
            return Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, false);
        } else if(flag == SCALE_FILL){
            if (bitmap.getWidth() <= bitmap.getHeight()) {
                ratio = bitmap.getWidth() / destWidth;
            } else {
                ratio = bitmap.getHeight() / destHeight;
            }

            int outWidth, outHeight;

            do {
                outWidth = (int) Math.ceil(bitmap.getWidth() / ratio);
                outHeight = (int) Math.ceil(bitmap.getHeight() / ratio);

                if(outWidth < destWidth || outHeight < destHeight) {
                    ratio-=0.5;
                }
            } while (outWidth < destWidth || outHeight < destHeight);

            Bitmap temp = Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, false);
            return temp;
        }
        return null;
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

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

}
