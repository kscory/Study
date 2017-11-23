package com.example.kyung.googlemapfunction;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Kyung on 2017-11-23.
 */

public class PreCacheLayoutManager extends LinearLayoutManager {

    private int extraLayoutSpace = -1;

    public PreCacheLayoutManager(Context context) {
        super(context);
    }

    public void setRecyclerView(RecyclerView recyclerView){
        recyclerView.setLayoutManager(this);
    }

    public void setExtraLayoutSpace(int extraLayoutSpace) {
        this.extraLayoutSpace = extraLayoutSpace;
    }

    @Override
    protected int getExtraLayoutSpace(RecyclerView.State state) {
        if(extraLayoutSpace>0){
            return extraLayoutSpace;
        }
        return Const.DEFAULT_EXTRA_LAYOUT_SPACE;
    }
}
