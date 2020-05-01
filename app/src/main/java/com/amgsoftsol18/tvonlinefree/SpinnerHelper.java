package com.amgsoftsol18.tvonlinefree;

import android.content.Context;
import android.widget.ArrayAdapter;

public class SpinnerHelper {

    private  ArrayAdapter<CharSequence> adapter;


    SpinnerHelper(Context ctx, int resource){

        addItemsOnSpinner(ctx,resource);

    }

    private void addItemsOnSpinner(Context ctx, int resource){

        adapter =
                ArrayAdapter.createFromResource(ctx,
                        resource,
                        R.layout.appbar_filter_title);

        adapter.setDropDownViewResource(R.layout.appbar_filter_list);

    }

    public ArrayAdapter<CharSequence> getAdapter() {
        return adapter;
    }

}
