package com.amgsoftsol18.tvonlinefree;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;


public class NavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {

    private Context Context;
    private String share;
    private Intent intent;

    NavigationItemSelectedListener(Context ctx){
        Context = ctx;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            /*case R.id.menu_option_1:
                //uselinkpro to buy, pro version delete option (this is free)
                     share = Context.getResources().getString(R.string.linkapp_pro);
                     shareTextUrl(share);

                break;*/


            case R.id.media_action_2:
                // Dialog Fragment, defaultlanguage, savedinstance
                FragmentManager fragmentManager = ((Activity) Context).getFragmentManager();
                Languagedialogfragment languagedialogfragment = new Languagedialogfragment();
                languagedialogfragment.show(fragmentManager,"langdialog");
                languagedialogfragment.setCancelable(false);

                break;

            case R.id.menu_option_2:
                //Usar linkfree o pro para valoracion,(this is free)
                share = Context.getResources().getString(R.string.linkappfree);
                Context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(share)));


                break;

            case R.id.menu_option_3:
                //usar linkfree o linkpro segun version para compartir (this is pro, use freelink)
                share = Context.getResources().getString(R.string.linkappfree);
                String blink = Context.getResources().getString(R.string.beforelinkappfree);
                shareTextUrl(blink.concat(" ").concat(share));
                break;
            case R.id.menu_opcion_4:
                showcopyright();

                break;

            case R.id.menu_opcion_5:
                showAbout();
                break;

        }

        return true;
    }

    private void shareTextUrl(String url) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, url);
        sendIntent.setType("text/plain");
        Context.startActivity(sendIntent);
    }

    private void showAbout() {

        // Inflate the about message contents
        LayoutInflater inflater = LayoutInflater.from(Context);

        View messageView = inflater.inflate(R.layout.about, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(Context);
        builder.setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                .setView(messageView)

                .setPositiveButton(R.string.positivebuttonloadaboutdialog, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })

                .create()
                .show();
    }

    private void showcopyright() {

        // Inflate the about message contents
        LayoutInflater inflater = LayoutInflater.from(Context);

        View messageView = inflater.inflate(R.layout.copyright, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(Context);
        builder.setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                .setView(messageView)

                .setPositiveButton(R.string.positivebuttonloadaboutdialog, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })

                .create()
                .show();
    }
}

