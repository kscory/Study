package com.example.kyung.firebasebasic2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-10-31.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Holder>{
    // 유저 데이터
    List<User> data = new ArrayList<>();
    Callback callback;

    public UserAdapter(Callback callback){
        this.callback = callback;
    }
    // 중간에 데이터를 받아서 갱신한다.
    public void setDataAndRefresh(List<User> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        User user = data.get(position);
        holder.textId.setText(user.email);
        holder.token = user.token;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView textId;
        String token;
        public Holder(View itemView) {
            super(itemView);
            // 안드로이드의 기본 레이아웃 android.R.layout.simple_list_item_1 안에 있는 id를 참조
            textId = itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.setIdAndToken(textId.getText().toString(), token);
                }
            });
        }
    }

    public interface Callback{
        public void setIdAndToken(String id, String token);
    }
}
