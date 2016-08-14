package com.hashirbaig.developer.textit.AsyncTasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveImageAsync extends AsyncTask<Object, Void, Void>{

    private Context mContext;
    private String mName;
    private Bitmap mBitmap;
    private boolean mSaved;

    @Override
    protected Void doInBackground(Object... objects) {
        mContext = (Context) objects[0];
        mName = (String) objects[1];
        mBitmap = (Bitmap) objects[2];

        mSaved = false;
        String folder = Environment.getExternalStorageDirectory() + "/TrendIt/";
        String ext = ".jpg";

        File directory = new File(folder);
        if(!directory.exists()) {
            directory.mkdirs();
        }

        File imageFile = new File(folder + mName + ext);

        FileOutputStream out = null;

        try {
            out = new FileOutputStream(imageFile.getPath());
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            mSaved = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(mSaved)
            Toast.makeText(mContext, "Image Saved Successfully", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(mContext, "Image Could not be Saved", Toast.LENGTH_LONG).show();
    }
}
