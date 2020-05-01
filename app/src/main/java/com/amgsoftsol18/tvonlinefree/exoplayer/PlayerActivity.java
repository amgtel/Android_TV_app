package com.amgsoftsol18.tvonlinefree.exoplayer;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.amgsoftsol18.tvonlinefree.ControlHideSystem;
import com.amgsoftsol18.tvonlinefree.LoadErrorDialogFragment;
import com.amgsoftsol18.tvonlinefree.R;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class PlayerActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private SimpleExoPlayer player;
    private boolean statebtnplaypause = true; //statebtnplaypause (true play, false not play);
    private boolean firstscroll = true;
    private String urlchannel;
    private ControlHideSystem controlhide;
    private ImageButton btnplaypause;
    private ImageView btnvolume;
    private PlayerView exoPlayerView;
    private String TAG = "PlayerState";
    private ProgressDialog pd;
    private AudioManager am;

    //Seekbar to control volume and bright
    private SeekBar seekbarvol, seekbarBright;
    private TextView volumeTextView;


    //Banner view
    private AdView bannerview;
    private int audiolevel;
    private int progressvolbar;

    //Gesturedetector
    private GestureDetectorCompat gestureDetectorCompat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exoplayer);


        //Hide status and navigation and control
        controlhide = new ControlHideSystem();
        controlhide.hidestatusbarandbuttons(getWindow().getDecorView());

        //Recover url
        urlchannel = getIntent().getStringExtra("url");

        //Create player
        CreatePlayer();

        //Create Data Source
        CreateDataSource(urlchannel);

        //Handle player listener
        HandlePlayer();

        //Handle mediacontrol
        HandleMediaControl();

        //Prepare banner
        //Iniciate banner ad
        bannerview = findViewById(R.id.adViewExo);
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerview.loadAd(adRequest);
        bannerview.setVisibility(View.INVISIBLE);

        //Gesture control
         this.gestureDetectorCompat =  new GestureDetectorCompat(this,this);

    }

    //Create player instance and its view
    private void CreatePlayer() {

        // Create Simple Exoplayer Player
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        exoPlayerView = findViewById(R.id.simple_player);
        exoPlayerView.setPlayer(player);
        exoPlayerView.setControllerShowTimeoutMs(2500); //Time it stays visible in msec


    }

    //Create dataource to play (HLS)
    private void CreateDataSource(String url) {

        //Version 2.7.3
        HlsMediaSource videoSource;
        // Produces DataSource instances through which media data is loaded.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "TVOnlineFree"), bandwidthMeter);

        videoSource = new HlsMediaSource.Factory(dataSourceFactory)
                .setAllowChunklessPreparation(true)
                .createMediaSource(Uri.parse(url));
        player.prepare(videoSource);
    }

    //Control player
    private void HandlePlayer() {

        player.addListener(new Player.EventListener() {

            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                switch (playbackState) {

                    case Player.STATE_IDLE:
                        Log.v(TAG, "STATE IDLE");
                        break;

                    case Player.STATE_BUFFERING:
                        Log.v(TAG, "STATE BUFFERING");
                        pd = new ProgressDialog(PlayerActivity.this);
                        pd.setMessage(getResources().getString(R.string.messagepd));
                        pd.show();

                        break;

                    case Player.STATE_READY:
                        Log.v(TAG, "STATE READY");
                        controlhide.hidestatusbarandbuttons(getWindow().getDecorView());
                        pd.dismiss();

                        break;

                    case Player.STATE_ENDED:
                        Log.v(TAG, "STATE ENDED");
                        break;

                    default:
                        break;
                }

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                pd.dismiss();
                player.stop();
                LoadErrorDialogFragment myerrorDialog = new LoadErrorDialogFragment();
                myerrorDialog.show(getFragmentManager(), "dialog");

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }


            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }

        });
    }

    //Handle buttons and seekbar
    private void HandleMediaControl() {

        initControls();

        btnplaypause = findViewById(R.id.button_playpause);

        btnplaypause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (statebtnplaypause) {
                    pausePlayer();
                    btnplaypause.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow));
                    bannerview.setVisibility(View.VISIBLE);
                    statebtnplaypause = false;
                } else {
                    player.setPlayWhenReady(true);
                    btnplaypause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
                    bannerview.setVisibility(View.GONE);
                    statebtnplaypause = true;
                }
                controlhide.showstatusbarandbuttons(getWindow().getDecorView());

            }
        });


        exoPlayerView.setControllerVisibilityListener(new PlayerControlView.VisibilityListener() {
            @Override
            public void onVisibilityChange(int visibility) {
                if (visibility == View.VISIBLE) {
                    controlhide.showstatusbarandbuttons(getWindow().getDecorView());
                } else {
                    controlhide.hidestatusbarandbuttons(getWindow().getDecorView());
                }

            }
        });
    }


    //Init controllers mediaplayer view
    private void initControls() {

        //Seekbar bright
        seekbarBright = findViewById(R.id.seekBarBright);


        //Seekbar volume
        btnvolume = findViewById(R.id.img_volume);
        seekbarvol = findViewById(R.id.seekBarVol);
        volumeTextView = findViewById(R.id.text_volume);
        am = (AudioManager) getSystemService(AUDIO_SERVICE);

        //Initbtnvolume
        initvolumen();

        //initseekbarvolume
        initbar(seekbarvol, AudioManager.STREAM_MUSIC);

        //initseekbarbright
        initbarBright(seekbarBright);

    }


    //Init volume and buttons volume
    private void initvolumen() {

        int volume_level = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        progressvolbar = seekbarvol.getProgress();

        // Initialize
        volumeTextView.setText(String.valueOf(volume_level));

        if (volume_level == 0) {
            btnvolume.setImageResource(R.drawable.ic_volume_off_black_45dp);
        }

        btnvolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (seekbarvol.getProgress() != 0) {
                    audiolevel = am.getStreamVolume(AudioManager.STREAM_MUSIC);
                    am.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

                    progressvolbar = seekbarvol.getProgress();
                    seekbarvol.setProgress(0);

                    btnvolume.setImageResource(R.drawable.ic_volume_off_black_45dp);
                } else {
                    am.setStreamVolume(AudioManager.STREAM_MUSIC, audiolevel, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                    btnvolume.setImageResource(R.drawable.ic_volume_up_black_45);
                    seekbarvol.setProgress(progressvolbar);
                }

            }
        });

    }


    //Init seekbar volume
    private void initbar(SeekBar bar, final int stream) {

        int vol = getPreferences(MODE_PRIVATE).getInt("volumen", am.getStreamVolume(AudioManager.STREAM_MUSIC));

        bar.setMax(30);
        bar.setProgress(vol);
        volumeTextView.setText(String.valueOf(vol));

        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar bar, int progress,
                                          boolean fromUser) {
                am.setStreamVolume(stream, progress/2,
                        AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

                if (progress == 0) {
                    btnvolume.setImageResource(R.drawable.ic_volume_off_black_45dp);
                } else {
                    btnvolume.setImageResource(R.drawable.ic_volume_up_black_45);
                }
                controlhide.showstatusbarandbuttons(getWindow().getDecorView());
                exoPlayerView.showController();

                volumeTextView.setText(String.valueOf(seekbarvol.getProgress()));
                getPreferences(MODE_PRIVATE).edit().putInt("volumen",seekbarvol.getProgress()).apply();

            }

            public void onStartTrackingTouch(SeekBar bar) {
                // no-op
            }

            public void onStopTrackingTouch(SeekBar bar) {
                // no-op
            }
        });
    }


    //Init seekbar bright
    private void initbarBright(SeekBar seekbarBright) {
        seekbarBright.setMax(10);
        seekbarBright.setProgress(5);
        seekbarBright.setEnabled(false);
    }



    //Pause exoplayer
    private void pausePlayer() {
        player.setPlayWhenReady(false);
    }


    //Start exoplayer
    private void startPlayer() {
        if (statebtnplaypause) {
            player.setPlayWhenReady(true);
        }
    }


    /**
     * *  Override android methods activity
     **/


    @Override
    protected void onPause() {
        super.onPause();
        pausePlayer();
    }


    @Override
    protected void onResume() {
        super.onResume();

        controlhide.hidestatusbarandbuttons(getWindow().getDecorView());
        startPlayer();
        if (statebtnplaypause) {
            btnplaypause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
        } else {
            exoPlayerView.showController();
            controlhide.showstatusbarandbuttons(getWindow().getDecorView());

        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            int index = seekbarvol.getProgress();
            seekbarvol.setProgress(index + 1);
            audiolevel = am.getStreamVolume(AudioManager.STREAM_MUSIC);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            int index = seekbarvol.getProgress();
            seekbarvol.setProgress(index - 1);
            audiolevel = am.getStreamVolume(AudioManager.STREAM_MUSIC);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return  super.onTouchEvent(event);
    }


    //Methods gesturelistener (only use onscroll at the moment)
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        Log.d("Scroll", "SCROLLLLL");

        if(distanceY > 0){
            Log.d("Scroll", "UPPP");

        }
        else{
            Log.d("Scroll", "DOWNN");
        }

        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

       return false;
    }


} //End Class PlayerActivity








