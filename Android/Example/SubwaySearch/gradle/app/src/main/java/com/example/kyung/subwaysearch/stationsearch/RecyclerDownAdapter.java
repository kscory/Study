package com.example.kyung.subwaysearch.stationsearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.kyung.subwaysearch.R;
import com.example.kyung.subwaysearch.model.SubwayInfo.Row;

import java.util.List;

/**
 * Created by Kyung on 2017-10-20.
 */

public class RecyclerDownAdapter extends RecyclerView.Adapter<RecyclerDownAdapter.Holder> {
    FrameLayout frameLayout;
    Context context;
    List<Row> rowList;

    public RecyclerDownAdapter(FrameLayout frameLayout, List<Row> rowList){
        this.frameLayout = frameLayout;
        this.rowList = rowList;
    }

    @Override
    public RecyclerDownAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_list,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Row row = rowList.get(position);
        String des = row.getSUBWAYENAME()+"행";
        holder.setTextDesStation(des);
        holder.setTextTime(row.getARRIVETIME());
    }

    @Override
    public int getItemCount() {
        return rowList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView textTime;
        TextView textDesStation;

        public Holder(View itemView) {
            super(itemView);

            textTime = (TextView) itemView.findViewById(R.id.textTime);
            textDesStation = (TextView) itemView.findViewById(R.id.textDesStation);
        }
        public void setTextTime(String time){
            textTime.setText(time);
        }
        public void setTextDesStation(String desSt){
            textDesStation.setText(desSt);
        }
    }
}
