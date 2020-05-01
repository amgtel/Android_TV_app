package com.amgsoftsol18.tvonlinefree;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Objects;

//Googleads Ads
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;

//Banner ads
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {


    private CheckConnection cn;
    private SharePreferencesWifiCheck spm;
    private SharePreferencesDefaultLanguage spdl;
    private Toolbar toolbar;
    private Spinner cmbToolbarGen;
    private SpinnerHelper s_adapter_gen;
    private int optiong_index;
    private ChannelsFragment chfragment;
    private Boolean inicio;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private String language;

    //Banner view
    private AdView bannerview;


    @Override
    public void onBackPressed() {
        //filter(optiong_index); Maybe i can use it
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Googe ads
        MobileAds.initialize(this, "admob pub");


        //First time
        inicio = true;

        //Check Connectiont

        cn = new CheckConnection();
        if(!cn.isConnected(getApplicationContext())){
            finish();
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.errorconnectionmessage), Toast.LENGTH_LONG).show();
        }
        else {

            // Check button donotaskagain
            spm = new SharePreferencesWifiCheck();
            if(!spm.loadPreferences(this)){

                // Check WIFI
                if(!cn.isConnectedWifi(getApplicationContext())){

                    // Dialog Fragment, checkbox, savedinstance
                    Wifidialogfragment mywifiDialog = new Wifidialogfragment();
                    mywifiDialog.show(getFragmentManager(),"dialog");
                    mywifiDialog.setCancelable(false);
                }
            }
        }


        //Check default language
        spdl = new SharePreferencesDefaultLanguage();
        if(spdl.loadPreferences(this) == null){ //No choose default language
            // Dialog Fragment, defaultlanguage, savedinstance
            Languagedialogfragment languagedialogfragment = new Languagedialogfragment();
            languagedialogfragment.show(getFragmentManager(),"langdialog");
            languagedialogfragment.setCancelable(false);
        }
        else{
            language = spdl.loadPreferences(this);
        }


        // Iniciate banner ad
        bannerview = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerview.loadAd(adRequest);


        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Drawer layout and navigationview
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navView = (NavigationView)findViewById(R.id.navview);
        navView.setNavigationItemSelectedListener(new NavigationItemSelectedListener(this));


        //Appbar page filter gen
        cmbToolbarGen = (Spinner) findViewById(R.id.CmbGen);
        s_adapter_gen = new SpinnerHelper(getApplicationContext(), R.array.gen_array);
        cmbToolbarGen.setAdapter(s_adapter_gen.getAdapter());



        //Listener spinners to chose filter options

        cmbToolbarGen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(!inicio) {
                    //Get option language and option genre
                    optiong_index = i;
                    filter(optiong_index);
                }
                else{
                    firsttime();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();

        if(!inicio) {
            filter(optiong_index);
        }
        else{
            firsttime();
        }

    }


    private void firsttime(){

        // Call filter for the first time and recreate fragment optiong_index(=0, all)

        optiong_index = 0;
        cmbToolbarGen.setSelection(optiong_index);


        //getResources().getString(R.string.languagechannels)
        chfragment = ChannelsFragment.newInstance("lstChannels", language, optiong_index);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.channels_fragment, chfragment).commit();


        inicio = false;
    }


    public void filter(int optiong){

        chfragment = ChannelsFragment.newInstance("lstChannels",language,optiong);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.channels_fragment, chfragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
