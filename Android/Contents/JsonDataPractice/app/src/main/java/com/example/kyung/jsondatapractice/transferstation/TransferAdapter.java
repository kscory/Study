package com.example.kyung.jsondatapractice.transferstation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kyung.jsondatapractice.R;
import com.example.kyung.jsondatapractice.transferstation.model.JsonTransferClass;
import com.example.kyung.jsondatapractice.transferstation.model.Row;

import java.util.Arrays;
import java.util.List;
import java.util.zip.InflaterInputStream;

/**
 * Created by Kyung on 2017-10-17.
 */

public class TransferAdapter extends BaseAdapter {

    List<Row> rows;
    JsonTransferClass json;
    Context context;

    public TransferAdapter(Context context, JsonTransferClass json){
        this.context = context;
        this.json = json;
        Row[] tempRow = json.getStationDayTrnsitNmpr().getRow();
        rows = Arrays.asList(tempRow);
    }

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Object getItem(int position) {
        return rows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transferstation_list,parent,false);
            holder = new Holder();
            holder.setInitView(convertView);
            convertView.setTag(holder);
        } else{
            holder = (Holder) convertView.getTag();
        }

        Row row = rows.get(position);
        holder.setTextSN(row.getSN());
        holder.setTextSTATN_NM(row.getSTATN_NM());
        holder.setTextWKDAY(row.getWKDAY());
        holder.setTextSATDAY(row.getSATDAY());
        holder.setTextSUNDAY(row.getSUNDAY());
        return convertView;
    }
}
class Holder{
    TextView textSN;
    TextView textSTATN_NM;
    TextView textWKDAY;
    TextView textSATDAY;
    TextView textSUNDAY;

    public void setInitView(View view){
        textSN = (TextView) view.findViewById(R.id.textSN);
        textSTATN_NM = (TextView) view.findViewById(R.id.textSTATN_NM);
        textWKDAY = (TextView) view.findViewById(R.id.textWKDAY);
        textSATDAY = (TextView) view.findViewById(R.id.textSATDAY);
        textSUNDAY = (TextView) view.findViewById(R.id.textSUNDAY);
    }

    public void setTextSN(String SN){
        textSN.setText(SN);
    }
    public void setTextSTATN_NM(String SN){
        textSTATN_NM.setText(SN);
    }
    public void setTextWKDAY(String SN){
        textWKDAY.setText(SN);
    }
    public void setTextSATDAY(String SN){
        textSATDAY.setText(SN);
    }
    public void setTextSUNDAY(String SN){
        textSUNDAY.setText(SN);
    }
}
