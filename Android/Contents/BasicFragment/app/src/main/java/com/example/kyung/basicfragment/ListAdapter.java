package com.example.kyung.basicfragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kyung.basicfragment.domain.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-10-02.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.Holder>{
    List<Contact> data = new ArrayList<>();
    ListFragment.CallbackDetail callbackDetail;
    Context context;

    public ListAdapter(Context context, final ListFragment.CallbackDetail callbackDetail){
        this.context = context;
        this.callbackDetail = callbackDetail;
    }

    public void setData(List<Contact> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.number_list,parent,false);
        return new Holder(view,callbackDetail);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Contact contact = data.get(position);
        holder.setTextNameList(contact.getName());
        holder.setId(contact.getId());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView textNameList;
        int id;
        public Holder(View itemView, final ListFragment.CallbackDetail callback) {
            super(itemView);

            textNameList = (TextView) itemView.findViewById(R.id.textNameList);
            // onClick하면 Detail Fragment 호출 (callback 함수 이용)
            textNameList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.showDetail(id);
                }
            });
        }

        public void setTextNameList(String name){
            textNameList.setText(name);
        }
        public void setId(int id){
            this.id = id;
        }
    }
}
