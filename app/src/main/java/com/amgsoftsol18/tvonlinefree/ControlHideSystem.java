package com.amgsoftsol18.tvonlinefree;

//import android.app.ActionBar;
import android.view.View;


public class ControlHideSystem {

    private int ui;

    public void  hidestatusbarandbuttons(View dView){

        //Do not show navigation buttons to press screen View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        // Hide the status bar.
        ui = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        dView.setSystemUiVisibility(ui);

    }

    /*Optional
    public void  hidenavigationbar(ActionBar ab){
        //Hide actionbar
        ab.hide();

    }
    */

    public void showstatusbarandbuttons(View dView){
        ui = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        dView.setSystemUiVisibility(ui);
    }

}
