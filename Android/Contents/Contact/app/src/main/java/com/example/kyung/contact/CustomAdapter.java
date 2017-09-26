package com.example.kyung.contact;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Kyung on 2017-09-26.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {

    List<Contact> data;

    public void setData(List<Contact> data){
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_number,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Contact contact = data.get(position);
        holder.setTextName(contact.getName());
        holder.setTextNumber(contact.getNumber());
    }

    public class Holder extends RecyclerView.ViewHolder{
        private String number;
        private TextView textName, textNumber;
        private Button btnCall;

        public Holder(View itemView) {
            super(itemView);
            initView(itemView);

            btnCall.setOnClickListener(new View.OnClickListener() {
                @SuppressWarnings("MissingPermission")
                @Override
                public void onClick(View v) {
                    String num = "tel:" + number;
                    Uri uri = Uri.parse(num);
                    Intent intent = new Intent(Intent.ACTION_CALL, uri);
                    v.getContext().startActivity(intent);
                }
            });
        }

        public void setTextName(String name) {
            textName.setText(name);
        }
        public void setTextNumber(String number) {
            this.number=number;
            textNumber.setText(number);
        }
        private void initView(View view){
            textName = (TextView) view.findViewById(R.id.textName);
            textNumber = (TextView) view.findViewById(R.id.textNumber);
            btnCall = (Button) view.findViewById(R.id.btnCall);
        }
    }
}

