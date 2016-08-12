package com.hashirbaig.developer.textit.Drawing;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.hashirbaig.developer.textit.Helper.PictureUtils;
import com.hashirbaig.developer.textit.Helper.TextUtils;

import java.io.IOException;
import java.io.InputStream;

public class DrawView extends SurfaceView{

    private static final String TAG = "DrawView";

    private Context mContext;
    private String mImagePath;
    private Canvas mCanvas, mViewCanvas;
    private Bitmap mBitmap, mOriginalBitmap;
    private String mName;

    public DrawView(Context context) {
        super(context, null);
        mContext = context;
        setWillNotDraw(false);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setWillNotDraw(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint backPaint = new Paint();
        backPaint.setColor(Color.WHITE);
        backPaint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(backPaint);

        if(mCanvas == null) {
            mOriginalBitmap = mBitmap;
            mBitmap = PictureUtils.getScaledBitmap(mOriginalBitmap, getWidth(), getHeight());
        }
        mCanvas = new Canvas(mBitmap);

        Log.i(TAG, "Canvas = " + mCanvas.getWidth() + "x" + mCanvas.getHeight());
        Log.i(TAG, "Bitmap = " + mBitmap.getWidth() + "x" + mBitmap.getHeight());

        Paint paintImage = new Paint();
        paintImage.setAntiAlias(true);
        paintImage.setFilterBitmap(true);
        paintImage.setDither(true);
        PointF placePoints = PictureUtils.getBitmapPlace(mBitmap, getWidth(), getHeight());
        if (mName != null) {
            Paint textPaint = new Paint();
            textPaint.setTextSize(50);
            textPaint.setColor(Color.WHITE);
            textPaint.setStyle(Paint.Style.FILL);
            PointF textPlace = TextUtils.getTextPlace(textPaint, mName, mBitmap);
            mCanvas.drawText(mName, textPlace.x, textPlace.y, textPaint);
        }
        canvas.drawBitmap(mBitmap, placePoints.y, placePoints.x, paintImage);
    }

    public void setImagePath(String path) {
        mImagePath = path;
        AssetManager assetManager = mContext.getAssets();

        InputStream iStream = null;
        try {
            iStream = assetManager.open(mImagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mBitmap = BitmapFactory.decodeStream(iStream).copy(Bitmap.Config.ARGB_8888, true);
        mBitmap.setHasAlpha(true);
    }

    public void setName(String name) {
        mName = name;
    }
}
