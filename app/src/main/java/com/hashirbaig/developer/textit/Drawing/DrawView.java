package com.hashirbaig.developer.textit.Drawing;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.hashirbaig.developer.textit.Helper.PictureUtils;
import com.hashirbaig.developer.textit.Model.UserData;

import java.io.IOException;
import java.io.InputStream;

public class DrawView extends SurfaceView{

    private static final String TAG = "DrawView";

    private Context mContext;
    private String mImagePath;
    private Canvas mCanvas, mViewCanvas;
    private Bitmap mBitmap, mOriginalBitmap, mUserBitmap, mUserOriginalImage;
    private String mName;
    private boolean mOriginalChanged;

    public static final int ROTATE_LEFT = 501;
    public static final int ROTATE_RIGHT = 502;

    private PointF picturePoint;

    public DrawView(Context context) {
        super(context, null);
        init(context);
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        mContext = context;
        setWillNotDraw(false);
        picturePoint = new PointF(0, 0);
        mOriginalChanged = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if(mBitmap.getWidth() > mBitmap.getHeight()) {
                    if(event.getY() >= 0 && event.getY() < mUserBitmap.getHeight() && mCanvas.getHeight() != mUserBitmap.getHeight())
                        picturePoint.y = event.getY();
                } else {
                    if(event.getX() >= 0 && event.getX() < mUserBitmap.getWidth() && mCanvas.getWidth() != mUserBitmap.getWidth())
                        picturePoint.x = event.getX();
                }
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint backPaint = new Paint();
        backPaint.setColor(Color.WHITE);
        backPaint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(backPaint);

        if(mCanvas == null) {
            mOriginalBitmap = mBitmap;
            mBitmap = PictureUtils.getScaledBitmap(mOriginalBitmap, 0, 0, getWidth(), getHeight(), PictureUtils.SCALE_FIT);
            mUserOriginalImage = BitmapFactory.decodeFile(UserData.get(mContext).getImagePath()).copy(Bitmap.Config.ARGB_8888, true);
        }
        if(mOriginalChanged) {
            picturePoint.set(0, 0);
            mOriginalChanged = false;
        }
        mUserBitmap = PictureUtils.getScaledBitmap(mUserOriginalImage, (int)picturePoint.x, (int)picturePoint.y, mBitmap.getWidth(), mBitmap.getHeight(), PictureUtils.SCALE_FILL);

        mCanvas = new Canvas(mUserBitmap);

        Log.i(TAG, "Canvas = " + mCanvas.getWidth() + "x" + mCanvas.getHeight());
        Log.i(TAG, "FrameBitmap = " + mBitmap.getWidth() + "x" + mBitmap.getHeight());
        Log.i(TAG, "UserBitmap = " + mUserBitmap.getWidth() + "x" + mUserBitmap.getHeight());

        Paint paintImage = new Paint();
        paintImage.setAntiAlias(true);
        paintImage.setFilterBitmap(true);
        paintImage.setDither(true);
        PointF placePoints = PictureUtils.getBitmapPlace(mBitmap, getWidth(), getHeight());

        mCanvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.drawBitmap(mUserBitmap, placePoints.y, placePoints.x, paintImage);
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

    public Bitmap getUserOriginalImage() {
        return mUserOriginalImage;
    }

    public void setUserOriginalImage(Bitmap userOriginalImage) {
        mUserOriginalImage = userOriginalImage;
        mOriginalChanged = true;
    }
}
