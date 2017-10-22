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

import java.util.List;

/**
 * Created by Kyung on 2017-10-21.
 */

public class ScheduleTable extends FrameLayout {

    List<Row> rowUp;
    List<Row> rowDown;
    RecyclerView recyclerViewTime;
    TimeTableRecyclerAdapter adapter;
    View view;

    public ScheduleTable(Context context) {
        super(context);
        initView();
    }

    private void initView(){
        view = LayoutInflater.from(getContext()).inflate(R.layout.subwayschedule_schduletable,null);
    }

    private void setRecyclerAdapter(){
        adapter = null;
        adapter = new TimeTableRecyclerAdapter(this,rowUp,rowDown);
        recyclerViewTime = null;
        recyclerViewTime = (RecyclerView) view.findViewById(R.id.recyclerViewTime);
        recyclerViewTime.setAdapter(adapter);
        recyclerViewTime.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setDataAndSetAdapter(List<Row> rowUp, List<Row> rowDown){
        this.rowUp = rowUp;
        this.rowDown = rowDown;
        setRecyclerAdapter();
    }
}
