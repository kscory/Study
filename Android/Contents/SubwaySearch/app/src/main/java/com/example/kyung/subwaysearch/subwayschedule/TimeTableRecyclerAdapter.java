package com.example.kyung.subwaysearch.subwayschedule;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.kyung.subwaysearch.R;
import com.example.kyung.subwaysearch.model.SubwayInfo.Row;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Kyung on 2017-10-21.
 */

public class TimeTableRecyclerAdapter extends RecyclerView.Adapter<TimeTableRecyclerAdapter.Holder> {

    FrameLayout frameLayout;
    List<Row> rowUpList;
    List<Row> rowDownList;

    public TimeTableRecyclerAdapter(FrameLayout frameLayout, List<Row> rowUpList, List<Row> rowDownList){
        this.frameLayout = frameLayout;
        this.rowUpList = rowUpList;
        this.rowDownList = rowDownList;
        notifyDataSetChanged();
    }

    private int checkCount(){
        if(rowUpList.size()>=rowUpList.size()){
            return rowDownList.size();
        } else{
            return rowDownList.size();
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subwayschedule_timeunit,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 상행 체크
        if(rowUpList.size()>position){
            Row rowUp = rowUpList.get(position);
            holder.setTextUpTime(rowUp.getARRIVETIME());
            holder.settextUpDes(rowUp.getSUBWAYENAME() +" 행");
        } else{
            holder.setTextUpTime("");
            holder.settextUpDes("");
        }
        // 하행 체크
        if(rowDownList.size()>position){
            Row rowDown = rowDownList.get(position);
            holder.settextDownTime(rowDown.getARRIVETIME());
            holder.settextDownDes(rowDown.getSUBWAYENAME() +" 행");
        } else{
            holder.settextDownTime("");
            holder.settextDownDes("");
        }
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return checkCount();
    }

    class Holder extends RecyclerView.ViewHolder{
        int position;
        TextView textUpTime;
        TextView textUpDes;
        TextView textDownTime;
        TextView textDownDes;

        public Holder(View itemView) {
            super(itemView);
            textUpTime = (TextView) itemView.findViewById(R.id.textUpTime);
            textUpDes = (TextView) itemView.findViewById(R.id.textUpDes);
            textDownTime = (TextView) itemView.findViewById(R.id.textDownTime);
            textDownDes = (TextView) itemView.findViewById(R.id.textDownDes);
        }

        public void setPosition(int position) {
            this.position = position;
        }
        public void setTextUpTime(String time) {
            textUpTime.setText(time);
        }
        public void settextUpDes(String des) {
            textUpDes.setText(des);
        }
        public void settextDownTime(String time) {
            textDownTime.setText(time);
        }
        public void settextDownDes(String des) {
            textDownDes.setText(des);
        }
    }
}

/*
notifyDataSetChanged : 데이터가 전체 바뀌었을 때 호출. 즉, 처음 부터 끝까지 전부 바뀌었을 경우
notifyItemChanged : 특정 Position의 위치만 바뀌었을 경우. position 4 번 위치만 데이터가 바뀌었을 경우 사용 하면 된다.
notifyItemRangeChanged : 특정 영역을 데이터가 바뀌었을 경우. position 3~10번까지의 데이터만 바뀌었을 경우 사용 하면 된다.
notifyItemInserted : 특정 Position에 데이터 하나를 추가 하였을 경우. position 3번과 4번 사이에 넣고자 할경우 4를 넣으면 된다.
notifyItemRangeInserted : 특정 영역에 데이터를 추가할 경우. position 3~10번 자리에 7개의 새로운 데이터를 넣을 경우
notifyItemRemoved : 특정 Position에 데이터를 하나 제거할 경우.
notifyItemRangeRemoved : 특정 영역의 데이터를 제거할 경우
notifyItemMoved : 특정 위치를 교환할 경우
 */
