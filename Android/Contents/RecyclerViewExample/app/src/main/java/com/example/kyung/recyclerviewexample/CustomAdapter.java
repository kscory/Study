package com.example.kyung.recyclerviewexample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.InflaterInputStream;

/**
 * Created by Kyung on 2017-09-25.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {
    // 1. 데이터 저장소
    List<String> data;
    Context context;
    // 생성자
    public CustomAdapter(Context context, List<String> data){
        this.data = data;
        this.context = context;
    }
    // 2. 목록의 전체 길이 설정
    @Override
    public int getItemCount() {
        return data.size();
    }

    // 3. 목록에서 아이템이 최초 요청되면 View Holder를 생성(홀더 생성)
    @Override
    public CustomAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    // 4. 생성된 View Holder를 RecyclerView 에 넘김 (홀더 사용)
    @Override
    public void onBindViewHolder(CustomAdapter.Holder holder, int position) {
        holder.setText(data.get(position));
    }

    // 0. 홀더 만들기
    public class Holder extends RecyclerView.ViewHolder{

        private TextView textExample;

        public Holder(final View itemView) {
            super(itemView);
            textExample = (TextView) itemView.findViewById(R.id.textExample);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("keys", textExample.getText());
                    v.getContext().startActivity(intent);
                }
            });
        }

        public void setText(String text){
            textExample.setText(text);
        }
    }
}
