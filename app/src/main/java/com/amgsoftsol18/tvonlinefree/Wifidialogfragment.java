package com.amgsoftsol18.tvonlinefree;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class Wifidialogfragment extends DialogFragment {

    private CheckBox dontaskagain;

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater().inflate(R.layout.wifidialogfragment, null);
        dontaskagain = (CheckBox) view.findViewById(R.id.checkbox);
        final SharePreferencesWifiCheck spm = new SharePreferencesWifiCheck();

        dontaskagain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                // Save the preference
                spm.savedPreferences(dontaskagain.isChecked(),getActivity());
            }
        });

        return new AlertDialog.Builder(getActivity())

                // Set view
                .setView(view)

                // Set Dialog Title
                .setTitle(R.string.titlewifidialog)

                // Set Dialog Message
                .setMessage(R.string.messagewifidialog)

                // Positive button
                .setPositiveButton(R.string.positivebuttonwifidialog, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Open wifi conection intent
                        startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));

                    }
                })

                // Negative Button
                .setNegativeButton(R.string.negativebuttonwifidialog, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int id) {

                    }
                }).create();
    }
}
