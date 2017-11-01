package com.example.kyung.basicfirebase2.bbs.bbsview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kyung.basicfirebase2.Const;
import com.example.kyung.basicfirebase2.R;
import com.example.kyung.basicfirebase2.model.Bbs;

import java.util.List;

/**
 * Created by Kyung on 2017-10-31.
 */

public class BbsAdapter extends RecyclerView.Adapter<BbsAdapter.Holder> {
    Context context;
    List<Bbs> bbsList;
    IMoveDetail moveDetail;

    public BbsAdapter(Context context){
        this.context = context;
        if(context instanceof IMoveDetail){
            moveDetail = (IMoveDetail) context;
        }
    }
    // 데이터를 다시 세팅하고 홀더릴 재생성
    public void setDataAndRefresh(List<Bbs> bbsList){
        this.bbsList = bbsList;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bbs_list,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Bbs bbs = bbsList.get(position);
        holder.setBbs_id(bbs.bbs_id);
        holder.setTextName(bbs.username);
        holder.setTextDate(bbs.date);
        holder.setTextContent(bbs.content);
    }

    @Override
    public int getItemCount() {
        return bbsList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView textName;
        private TextView textDate;
        private TextView textContent;
        private String bbs_id;

        public Holder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textDate = itemView.findViewById(R.id.textDate);
            textContent = itemView.findViewById(R.id.textContent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveDetail.goDetail(bbs_id);
                }
            });
        }
        public void setBbs_id(String bbs_id){
            this.bbs_id = bbs_id;
        }

        public void setTextName(String name){
            textName.setText(name);
        }
        public void setTextDate(String date){
            textDate.setText(date);
        }
        public void setTextContent(String content){
            textContent.setText(content);
            textContent.setSingleLine(true);
        }
    }

    public interface IMoveDetail{
        public void goDetail(String bbs_id);
    }
}
