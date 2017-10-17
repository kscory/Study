package com.example.kyung.jsondatapractice.transferstation;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyung.jsondatapractice.MainActivity;
import com.example.kyung.jsondatapractice.R;
import com.example.kyung.jsondatapractice.Remote;
import com.example.kyung.jsondatapractice.transferstation.model.JsonTransferClass;
import com.google.gson.Gson;

/**
 * Created by Kyung on 2017-10-17.
 */

public class TransferStationView extends FrameLayout {

    ListView listView;
    TextView textCount;
    TextView textCode;
    TextView textMessage;

    JsonTransferClass jsonData;

    public TransferStationView(Context context) {
        super(context);

        initView();
        load();
    }

    private void initView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.transferstation,null);
        textCount = (TextView) view.findViewById(R.id.textCount);
        textCode = (TextView) view.findViewById(R.id.textCode);
        textMessage = (TextView) view.findViewById(R.id.textMessage);
        listView = (ListView) view.findViewById(R.id.listView);
        addView(view);
    }

    private void load(){

        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... params) {
                return Remote.getData("http://openapi.seoul.go.kr:8088/70425153456c65653539624c6d7a66/json/StationDayTrnsitNmpr/1/100/");
            }

            @Override
            protected void onPostExecute(String jsonString) {
                if("AccessError".equals(jsonString)){
                    Toast.makeText(getContext(), "환승 유동 인구 Api 통신 오류", Toast.LENGTH_SHORT).show();
                } else {
                    parse(jsonString);
                    setListAdapter();
                    setTextInView();
                }
            }

        }.execute();
    }

    private void parse(String string){
        Gson gson = new Gson();
        jsonData = gson.fromJson(string,JsonTransferClass.class);

    }

    private void setListAdapter(){
        TransferAdapter adapter = new TransferAdapter(getContext(),jsonData);
        listView.setAdapter(adapter);
    }

    private void setTextInView(){
        textCount.setText(jsonData.getStationDayTrnsitNmpr().getList_total_count());
        textCode.setText(jsonData.getStationDayTrnsitNmpr().getRESULT().getCODE());
        textMessage.setText(jsonData.getStationDayTrnsitNmpr().getRESULT().getMESSAGE());
    }

}
