package com.example.kyung.remotebbs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kyung.remotebbs.model.Data;

import java.lang.reflect.Array;

/**
 * Created by Kyung on 2017-10-27.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {
    Data datas[];

    public RecyclerAdapter(Data datas[]){
        this.datas = datas;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Data data = datas[position];
        holder.textTitle.setText(data.getTitle());
        holder.textDate.setText(data.getDate());
    }

    @Override
    public int getItemCount() {
        return datas.length;
    }

    public void addDataAndRefresh(Data[] data) {
        // 기존 데이터에 신규 데이터를 합친다.
        Data temp[] = new Data[datas.length + data.length];
        System.arraycopy(datas, 0, temp, 0, datas.length);
        System.arraycopy(data,0, temp, datas.length, data.length);
        datas = temp;
        // 데이터를 갱신한다.
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView textTitle;
        TextView textDate;
        public Holder(View view){
            super(view);
            textTitle = view.findViewById(R.id.textTitle);
            textDate = view.findViewById(R.id.textDate);
        }
    }
}
