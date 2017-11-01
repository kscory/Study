package com.example.kyung.basicfirebase2.bbs.bbsview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.kyung.basicfirebase2.model.Bbs;

import java.util.List;

/**
 * Created by Kyung on 2017-10-31.
 */

public class BbsAll extends FrameLayout{

    RecyclerView recyclerView;
    BbsAdapter adapter;

    public BbsAll(@NonNull Context context) {
        super(context);
        init();
        adapter = new BbsAdapter(context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public void setDataAll(List<Bbs> bbsList){
        adapter.setDataAndRefresh(bbsList);
    }

    private void init(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_list,null);
        recyclerView = view.findViewById(R.id.recyclerView);
        addView(view);
    }
}
