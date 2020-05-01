package com.amgsoftsol18.tvonlinefree;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferencesDefaultLanguage {

    public void savedPreferences(String def_lang, Activity ap){
        SharedPreferences prefs = ap.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("default_language", def_lang);
        editor.apply();
    }

    public String loadPreferences(Activity ap){
        SharedPreferences prefs = ap.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        return prefs.getString("default_language",null);
    }
}
