package org.techtown.reducetheuseofplastic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MyCustomDialogFragment extends DialogFragment {
    public static final String DIALOG_MESSAGE="dialogMessage";
    String dialogMessage="not set";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Bundle bundle=getArguments();
        if (bundle!=null){
            dialogMessage=bundle.getString(DIALOG_MESSAGE);
        }

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

        builder.setMessage(dialogMessage).setPositiveButton("Positive", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                {

                }
            }
        }).setNegativeButton("Negative", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}
