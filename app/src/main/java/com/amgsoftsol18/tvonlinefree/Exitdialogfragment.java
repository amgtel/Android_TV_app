package com.amgsoftsol18.tvonlinefree;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

public class Exitdialogfragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {


        return new AlertDialog.Builder(getActivity())

                // Set Dialog Title
                .setTitle(R.string.titleexitdialog)

                // Set Dialog Message
                .setMessage(R.string.messageexitdialog)

                // Positive button
                .setPositiveButton(R.string.positivebuttonexitdialog, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().finish();

                    }
                })

                // Negative Button
                .setNegativeButton(R.string.negativebuttonexitdialog, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int id) {

                    }
                }).create();
    }
}
