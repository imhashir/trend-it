package com.hashirbaig.developer.textit.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hashirbaig.developer.textit.HostingActivities.BackgroundListActivity;
import com.hashirbaig.developer.textit.Model.UserData;
import com.hashirbaig.developer.textit.R;

public class DialogChooseImage extends DialogFragment{

    private Button mButtonChooseImage;
    private ImageView mUserImage;
    private String mPath;

    private static final int REQUEST_IMAGE = 1002;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_choose_image, null);

        mButtonChooseImage = (Button) v.findViewById(R.id.button_choose_image);
        mUserImage = (ImageView) v.findViewById(R.id.user_image);

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQUEST_IMAGE);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        UserData.get(getActivity()).setImagePath(mPath);
                        Intent intent = BackgroundListActivity.newIntent(getActivity());
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case REQUEST_IMAGE:
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mPath = cursor.getString(columnIndex);
                cursor.close();
                Glide.with(getActivity())
                        .load(mPath)
                        .into(mUserImage);
                break;
        }
    }

    public static DialogChooseImage newInstance() {
        return new DialogChooseImage();
    }
}
