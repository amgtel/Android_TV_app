package com.amgsoftsol18.tvonlinefree;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amgsoftsol18.tvonlinefree.glideModule.GlideApp;
import com.google.firebase.storage.StorageReference;

public class ChannelHolder extends RecyclerView.ViewHolder {

    private View mView;


    public ChannelHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setImageChannel(StorageReference urlimagechannel, Context ctx){
        ImageView cn_image = (ImageView) mView.findViewById(R.id.image_channel);

        /*Glide.with(ctx).using(new FirebaseImageLoader())  //With verssion firebaseui 0.6.0
                .load(urlimagechannel)
                .into(cn_image);*/
        GlideApp.with(ctx)
                .load(urlimagechannel)
                .into(cn_image);
    }


    public void setNameChannel(String name) {
        TextView cn_name = (TextView) mView.findViewById(R.id.name_channel);
        cn_name.setText(name);
    }

}
