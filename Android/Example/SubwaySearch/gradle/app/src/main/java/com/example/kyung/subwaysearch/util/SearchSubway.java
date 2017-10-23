package com.example.kyung.subwaysearch.util;

import android.net.Uri;
import android.os.AsyncTask;
import android.widget.SeekBar;

import com.example.kyung.subwaysearch.Remote;
import com.example.kyung.subwaysearch.model.SubwayInfo.JsonSubwayInfo;
import com.example.kyung.subwaysearch.model.SubwayLine.JsonSubwayLine;
import com.example.kyung.subwaysearch.model.SubwayNameService.JsonSubwayService;
import com.example.kyung.subwaysearch.model.SubwayNameService.Row;
import com.google.gson.Gson;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kyung on 2017-10-19.
 */

public class SearchSubway {
    // Singleton
    private static SearchSubway instance = null;
    private SearchSubway(){}
    public static SearchSubway getInstance(){
        if(instance ==null){
            instance = new SearchSubway();
        }
        return instance;
    }

    JsonSubwayService jsonSubwayService;
    JsonSubwayLine jsonSubwayLine;
    JsonSubwayInfo jsonSubwayInfo;

    public JsonSubwayLine SearchSubwayLineInfoByName(String text){
        jsonSubwayLine = null;

        return jsonSubwayLine;
    }

    public JsonSubwayInfo SearchSubwayInfoByFR_CODE(String FR_CODE){
        jsonSubwayInfo = null;

        return jsonSubwayInfo;
    }

    public JsonSubwayService SeacrchSubwayServiceByName(String stationName){
        jsonSubwayService = null;
        String url = MakeURL.getInstance().makeSubwayNameServiceURL(stationName);
        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... params) {
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        }.execute();

        Gson gson = new Gson();
        jsonSubwayService = gson.fromJson(Remote.getData(url),JsonSubwayService.class);
        return jsonSubwayService;
    }

    public List<Row> changeRowSubwayService(JsonSubwayService jsonSubwayService){
        List<Row> result = new ArrayList<>();
        Row[] rows = jsonSubwayService.getSearchInfoBySubwayNameService().getRow();
        result = Arrays.asList(rows);
        return result;
    }

    public List<com.example.kyung.subwaysearch.model.SubwayInfo.Row> changeRowSubwayInfo(JsonSubwayInfo jsonSubwayInfo){
        List<com.example.kyung.subwaysearch.model.SubwayInfo.Row> result = new ArrayList<>();
        com.example.kyung.subwaysearch.model.SubwayInfo.Row[] rows = jsonSubwayInfo.getSearchSTNTimeTableByFRCodeService().getRow();
        result = Arrays.asList(rows);
        return result;
    }
}
