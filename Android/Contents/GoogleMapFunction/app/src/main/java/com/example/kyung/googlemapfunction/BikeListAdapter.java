package com.example.kyung.googlemapfunction;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kyung.googlemapfunction.contract.Contract;
import com.example.kyung.googlemapfunction.domain.bikeconvention.Row;
import com.example.kyung.googlemapfunction.util.ImageLoadUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-11-23.
 */

public class BikeListAdapter extends RecyclerView.Adapter<BikeListAdapter.Holder> {

    Context context;
    List<Row> data = new ArrayList<>();

    public BikeListAdapter(Context context) {
        this.context = context;
    }

    public void setRecyclerView(RecyclerView recyclerView){
        recyclerView.setAdapter(this);
    }

    public void setDateAndRefresh(List<Row> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Row row = data.get(position);

        holder.setObjId(row.getOBJECTID());
        holder.setImageView(row.getFILENAME());
        holder.setTextAddress(row.getADDRESS());
        holder.setTypeName(row.getCLASS());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private String objId;
        private ImageView imageView;
        private TextView typeName;
        private TextView textAddress;

        public Holder(View itemView) {
            super(itemView);

            initView(itemView);
            itemView.setOnClickListener(v ->{
                ((Contract.IClickAction)context).showDetail(objId);
            });
        }

        private void initView(View view) {
            imageView = view.findViewById(R.id.imageView);
            typeName = view.findViewById(R.id.typeName);
            textAddress = view.findViewById(R.id.textAddress);
        }

        public void setObjId(String objId){
            this.objId = objId;
        }
        public void setImageView(String imageUrl){
            ImageLoadUtil.loadImageByAQuery(context,imageUrl, imageView);
        }
        public void setTypeName(String type){
            typeName.setText(type);
        }
        public void setTextAddress(String address){
            textAddress.setText(address);
        }
    }
}
