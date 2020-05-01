package com.amgsoftsol18.tvonlinefree;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amgsoftsol18.tvonlinefree.exoplayer.PlayerActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

//Google Ads


public class ChannelsFragment extends Fragment {

    private View v;
    private FirestoreRecyclerAdapter<Channel, ChannelHolder> adapter;
    private RecyclerView recycler;
    //private RecyclerView.LayoutManager mlmanager;
    private String list, lang, gen;
    private Query query;
    private Integer gen_index;
    //private Bundle mBundleRecyclerViewState;
    //private Parcelable mListState;
    //private final String KEY_RECYCLER_STATE = "recycler_state";
    private GridLayoutManager mlmanager;


    //Interstitial ads
    private InterstitialAd interstitialad;


    public ChannelsFragment(){

    }


    public static ChannelsFragment newInstance(String param1, String param2, int param3) {
        ChannelsFragment fragment = new ChannelsFragment();
        Bundle args = new Bundle();
        args.putString("ARG_PARAM1", param1);
        args.putString("ARG_PARAM2", param2);
        args.putInt("ARG_PARAM3", param3);

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list = getArguments().getString("ARG_PARAM1");
            lang = getArguments().getString("ARG_PARAM2");
            gen_index = getArguments().getInt("ARG_PARAM3");

        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        v = inflater.inflate(R.layout.channels_fragment, container, false);


        // Grab  RecyclerView
        recycler = v.findViewById(R.id.channels_recyclerview);
        recycler.setHasFixedSize(true);

        //Numbers of columns depends on screen
        mlmanager = new GridLayoutManager(getContext(),getNumberOfColums());
        recycler.setLayoutManager(mlmanager);


        //Refrence storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReference();


        // The root reference to Firestore database and  Query
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();

        gen = getResources().getStringArray(R.array.gen_array_firebase)[gen_index];

        if(gen_index == 0){

            query = rootRef.collection(list).whereEqualTo("language", lang).orderBy("name");
        }
        else if(gen_index == 4){ //Show all music channels

            query = rootRef.collection(list).whereEqualTo("genre",gen);

        }
        else{
            query = rootRef.collection(list).whereEqualTo("language", lang).whereEqualTo("genre",gen)
                    .orderBy("name");
        }


        FirestoreRecyclerOptions<Channel> options = new FirestoreRecyclerOptions.Builder<Channel>()
                .setQuery(query, Channel.class)
                .build();

        // Define adapter and listen
        adapter = new FirestoreRecyclerAdapter<Channel, ChannelHolder>(options) {

                @Override
                public void onBindViewHolder(@NonNull final ChannelHolder holder, final int position, @NonNull final Channel cn) {
                    // Bind the  object to the Holder
                    holder.setNameChannel(cn.getName());
                    holder.setImageChannel(storageRef.child(cn.getName().concat(".png")), getContext());

                    // Listener item
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            //Load add
                            interstitialad = new InterstitialAd(Objects.requireNonNull(getContext()));
                            interstitialad.setAdUnitId(getResources().getString(R.string.interstitialpusarcanal));
                            interstitialad.loadAd(new AdRequest.Builder().build());

                            final ProgressDialog pd = new ProgressDialog(getActivity());
                            pd.setMessage(getResources().getString(R.string.messagepdad));
                            pd.show();
                            pd.setCancelable(false);
                            pd.setCanceledOnTouchOutside(false);

                            interstitialad.setAdListener(new AdListener() {
                                @Override
                                public void onAdLoaded() {
                                    // Code to be executed when an ad finishes loading.
                                    pd.dismiss();
                                    interstitialad.show();

                                }

                                @Override
                                public void onAdFailedToLoad(int errorCode) {
                                    // Code to be executed when an ad request fails.
                                    pd.dismiss();

                                }

                                @Override
                                public void onAdOpened() {
                                    // Code to be executed when the ad is displayed.
                                    pd.dismiss();



                                }

                                @Override
                                public void onAdLeftApplication() {
                                    // Code to be executed when the user has left the app.
                                    pd.dismiss();

                                }

                                @Override
                                public void onAdClosed() {
                                    // Code to be executed when when the interstitial ad is closed.
                                    // Start activity mediaplayer

                                    //Start Exoplayer
                                    Intent intent = new Intent(getContext(), PlayerActivity.class);
                                    intent.putExtra("url", cn.getUrldirection());
                                    startActivity(intent);

                                }
                            });

                        }
                    });
                }


                @NonNull
                @Override
                public ChannelHolder onCreateViewHolder(@NonNull final ViewGroup group, int i) {
                    // Create a new instance of the ViewHolder,
                    View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_channel, group, false);

                    return new ChannelHolder(view);
                }

            };

        recycler.setAdapter(adapter);


        //Call start adapter
        //adapter.startListening();

        return  v;
    }



   /* @Override
    public void onDestroyView() {
            super.onDestroyView();

            if (adapter != null) {
            adapter.stopListening();
            }
    }*/



    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            mlmanager.setSpanCount(getNumberOfColums());

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            mlmanager.setSpanCount(getNumberOfColums());

        }
        recycler.setLayoutManager(mlmanager);
    }


    private int getNumberOfColums(){
        int noOfColumns;

        DisplayMetrics displayMetrics = Objects.requireNonNull(getContext()).getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        noOfColumns = (int) (dpWidth / 170);

        return noOfColumns;
    }

}
