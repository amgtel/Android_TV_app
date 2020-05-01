package com.amgsoftsol18.tvonlinefree;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;


public class LoadErrorDialogFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())


                // Set Dialog Title
                .setTitle(R.string.titleloaderrordialog)

                // Set Dialog Message
                .setMessage(R.string.messageloaderrordialog)

                // Positive button
                .setPositiveButton(R.string.positivebuttonloaderrordialog, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                        getActivity().recreate();
                    }
                })

                // Negative Button
                .setNegativeButton(R.string.negativebuttonloaderrordialog, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int id) {
                            getActivity().finish();
                    }
                }).create();
    }
}
