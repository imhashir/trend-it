package com.hashirbaig.developer.textit.Helper;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.PointF;

public class TextUtils {

    public static PointF getTextPlace(Paint paint, String text, Bitmap bitmap) {
        PointF pointF = new PointF();

        pointF.x = (bitmap.getWidth() / 2) - (paint.measureText(text) / 2);
        pointF.y = (bitmap.getHeight() / 2) - ((paint.ascent() + paint.descent()) / 2);

        return pointF;
    }

}
