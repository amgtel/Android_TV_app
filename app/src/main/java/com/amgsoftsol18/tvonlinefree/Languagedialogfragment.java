package com.amgsoftsol18.tvonlinefree;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

public class Languagedialogfragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        final SharePreferencesDefaultLanguage spdl = new SharePreferencesDefaultLanguage();
        final String[] items = getResources().getStringArray(R.array.lang_array);

        return new AlertDialog.Builder(getActivity())

                // Set Dialog Title
                .setTitle(R.string.titlelanguagedialog)

                // Set items
                .setSingleChoiceItems(items,-1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        spdl.savedPreferences(getResources().getStringArray(R.array.lang_array_firebase)[item],getActivity());
                        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                })

                .create();
    }
}
