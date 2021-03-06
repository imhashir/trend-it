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
    private Canvas mCanvas;
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
            mUserOriginalImage = BitmapFactory.decodeFile(UserData.get(mContext).getCroppedImagePath()).copy(Bitmap.Config.ARGB_8888, true);
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

    public Bitmap getUserBitmap() {
        return mUserBitmap;
    }

    public Bitmap getFinalImage() {
        Bitmap mHighResUserImage = null;
        mHighResUserImage = PictureUtils.getScaledBitmap(mUserOriginalImage, 0, 0, mOriginalBitmap.getWidth(), mOriginalBitmap.getHeight(), PictureUtils.SCALE_FIT);

        Canvas canvas = new Canvas(mHighResUserImage);
        canvas.drawBitmap(mOriginalBitmap, 0, 0, null);
        return mHighResUserImage;
    }

    public void setUserBitmap(Bitmap userBitmap) {
        mUserBitmap = userBitmap;
    }

    public Bitmap getUserOriginalImage() {
        return mUserOriginalImage;
    }

    public void setUserOriginalImage(Bitmap userOriginalImage) {
        mUserOriginalImage = userOriginalImage;
        mOriginalChanged = true;
    }
}
