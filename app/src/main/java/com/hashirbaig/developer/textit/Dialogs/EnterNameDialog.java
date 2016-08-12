package com.hashirbaig.developer.textit.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.hashirbaig.developer.textit.R;

public class EnterNameDialog extends DialogFragment{

    private static final String KEY_NAME = "key_name_user";
    public static final String EXTRA_NAME = "extra_name_user";

    private String mName;
    private EditText mTextField;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_enter_name, null);
        String passedName = getArguments().get(KEY_NAME) != null ? getArguments().get(KEY_NAME).toString() : null;
        mTextField = (EditText) v.findViewById(R.id.dialog_user_name);

        if(passedName != null) {
            mTextField.setText(passedName);
        }

        mTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mName = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(getResources().getString(R.string.text_button_enter_name_dialog))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent data = new Intent();
                        data.putExtra(EXTRA_NAME, mName);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
                    }
                })
                .create();
    }

    public static EnterNameDialog newInstance(String name) {
        Bundle args = new Bundle();
        args.putString(KEY_NAME, name);

        EnterNameDialog fragment = new EnterNameDialog();
        fragment.setArguments(args);

        return fragment;
    }


}
