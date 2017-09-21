package com.example.kyung.androidmemofile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kyung.androidmemofile.domain.Memo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Kyung on 2017-09-20.
 */

public class ListAdapter extends BaseAdapter{

    Context context;
    public static ArrayList<Memo> data;

    public ListAdapter(Context context, ArrayList<Memo> data){
        this.context=context;
        this.data=data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list,null);
            holder = new Holder(convertView);

            convertView.setTag(holder);
        } else{
            holder = (Holder) convertView.getTag();
        }

        Memo memo = data.get(position);
        holder.setNo(memo.getNo());
        holder.setTitle(memo.getTitle());
        holder.setAuthor(memo.getAuthor());
        holder.setDate(memo.getDatetime());
        holder.setContent(memo.getContent());
        holder.setPosition(position);

        return convertView;
    }

    class Holder{

        private int position;
        private String author;
        private String content;
        private TextView textNo;
        private TextView textTitle;
        private TextView textDate;

        public Holder(View view){
            textNo = (TextView) view.findViewById(R.id.textNo);
            textTitle = (TextView) view.findViewById(R.id.textTitle);
            textDate = (TextView) view.findViewById(R.id.textDate);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),DetailActivity.class);
                    intent.putExtra("position",position);
                    view.getContext().startActivity(intent);
                }
            });

        }

        public void setPosition(int position){
            this.position = position;
        }

        public void setAuthor(String author){
            this.author = author;
        }

        public void setContent(String content){
            this.content = content;
        }

        public void setNo(int no){
            textNo.setText(no+"");
        }

        public void setTitle(String title){
            textTitle.setText(title);
        }

        public void setDate(long datetime){
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd HH:mm");
            textDate.setText(sdf.format(datetime));
        }

    }
}
