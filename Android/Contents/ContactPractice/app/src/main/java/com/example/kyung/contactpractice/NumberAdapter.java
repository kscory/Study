package com.example.kyung.contactpractice;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.INotificationSideChannel;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kyung.contactpractice.domain.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.InflaterInputStream;

/**
 * Created by Kyung on 2017-09-27.
 */

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.Holder> {
    List<Contact> data = new ArrayList<>();

    public void setDataAndRefresh(List<Contact> data){
        this.data = data;
        // 데이터 변경을 알린다.
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_number,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Contact contact = data.get(position);
        holder.setNo(contact.getId());
        holder.setName(contact.getName());
        holder.setNumber(contact.getNumber());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        private TextView textNo, textNumber, textName;
        private ImageView imagebtnCall;
        private String number;

        public Holder(final View itemView) {
            super(itemView);
            initView(itemView);

            imagebtnCall.setOnClickListener(new View.OnClickListener() {
                @SuppressWarnings("MissingPermission")
                @Override
                public void onClick(View v) {
                    String num = "tel:" +number;
                    Uri uri = Uri.parse(num);
                    Intent intent = new Intent(Intent.ACTION_CALL,uri);
                    v.getContext().startActivity(intent);
                }
            });
        }

        private void initView(View view){
            textNo = (TextView) view.findViewById(R.id.textNo);
            textNumber = (TextView) view.findViewById(R.id.textNumber);
            textName = (TextView) view.findViewById(R.id.textName);
            imagebtnCall = (ImageButton) view.findViewById(R.id.imagebtnCall);
        }

        public void setNo(int no){
            textNo.setText(no+"");
        }
        public void setNumber(String number){
            textNumber.setText(number);
            this.number = number;
        }
        public void setName(String name){
            textName.setText(name);
        }


    }
}
