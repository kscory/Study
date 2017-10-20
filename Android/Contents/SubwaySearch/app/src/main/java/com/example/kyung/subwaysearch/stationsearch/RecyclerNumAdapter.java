package com.example.kyung.subwaysearch.stationsearch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kyung.subwaysearch.R;
import com.example.kyung.subwaysearch.model.SubwayNameService.Row;
import com.example.kyung.subwaysearch.util.SearchSubway;

import java.util.List;

/**
 * Created by Kyung on 2017-10-19.
 */

public class RecyclerNumAdapter extends RecyclerView.Adapter<RecyclerNumAdapter.Holder>{
    FrameLayout frameLayout;
    String stationName;
    List<Row> rowList;
    SearchInfo searchInfo;

    public RecyclerNumAdapter(FrameLayout frameLayout, String stationName , List<Row> rowList){
        this.frameLayout = frameLayout;
        this.rowList = rowList;
        this.stationName = stationName;
        if(frameLayout instanceof SearchInfo)
            searchInfo = (SearchInfo) frameLayout;
        else
            throw new RuntimeException("must implement SearchInfo!!!!!");
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_lineselect,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Row row = rowList.get(position);
        // 1~9: 1~9호선, I: 인천1호선, I2: 인천2호선, K: 경의중앙선, KK: 경강선 B: 분당선, A: 공항철도, G: 경춘선, S:신분당선, SU:수인선
        String line="";
        switch (row.getLINE_NUM()){
            case "I": line="인천1호선"; break;
            case "I2": line="인천2호선" ; break;
            case "K": line="경의중앙선"; break;
            case "KK": line="경강선"; break;
            case "B": line="분당선"; break;
            case "A": line="공항철도"; break;
            case "G": line="경춘선"; break;
            case "S": line="신분당선"; break;
            case "SU": line="수인선"; break;
            default: line=row.getLINE_NUM()+"호선"; break;

        }
        String text = stationName +"( " + line + " )";
        holder.setTextStation(text);
        holder.setF_CODE(row.getFR_CODE());
        holder.setImageView(row.getLINE_NUM());
    }

    @Override
    public int getItemCount() {
        return rowList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        private String F_CODE;
        private ImageView imageView;
        private TextView textStation;
        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textStation = (TextView) itemView.findViewById(R.id.textStation);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchInfo.SearchSubwayInfoUpByFR_CODE(F_CODE);
                }
            });
        }
        public void setImageView(String LineNumber){

        }
        public void setTextStation(String stationName){
            textStation.setText(stationName);
        }
        public void setF_CODE(String code){
            F_CODE=code;
        }
    }

    public interface SearchInfo{
        public void SearchSubwayInfoUpByFR_CODE(String FR_CODE);
    }
}
