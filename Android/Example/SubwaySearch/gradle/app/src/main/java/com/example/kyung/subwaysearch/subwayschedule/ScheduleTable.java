package com.example.kyung.subwaysearch.subwayschedule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.kyung.subwaysearch.R;
import com.example.kyung.subwaysearch.model.SubwayInfo.JsonSubwayInfo;
import com.example.kyung.subwaysearch.model.SubwayInfo.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-10-21.
 */

public class ScheduleTable extends FrameLayout {

    RecyclerView recyclerViewTime;
    TimeTableRecyclerAdapter adapter;
    View view;

    public ScheduleTable(Context context) {
        super(context);
        initView();
        setRecyclerAdapter();
    }

    private void initView(){
        view = LayoutInflater.from(getContext()).inflate(R.layout.subwayschedule_schduletable,null);
        addView(view);
    }

    private void setRecyclerAdapter(){
        adapter = new TimeTableRecyclerAdapter(this);
        recyclerViewTime = (RecyclerView) view.findViewById(R.id.recyclerViewTime);
        recyclerViewTime.setAdapter(adapter);
        recyclerViewTime.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setDataByAdapter(List<Row> rowUp, List<Row> rowDown){
        adapter.setData(rowUp,rowDown);
        adapter.notifyDataSetChanged();
    }
}
