package com.amgsoftsol18.tvonlinefree;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferencesWifiCheck {

    public void savedPreferences(Boolean check_state, Activity ap){
        SharedPreferences prefs = ap.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("checkbox_state", check_state);
        editor.apply();
    }

    public boolean loadPreferences(Activity ap){
        SharedPreferences prefs = ap.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        return prefs.getBoolean("checkbox_state", false);
    }
}
