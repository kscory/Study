package com.example.kyung.subwaysearch.stationsearch;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyung.subwaysearch.R;
import com.example.kyung.subwaysearch.Remote;
import com.example.kyung.subwaysearch.model.SubwayInfo.JsonSubwayInfo;
import com.example.kyung.subwaysearch.model.SubwayLine.JsonSubwayLine;
import com.example.kyung.subwaysearch.model.SubwayLine.Row;
import com.example.kyung.subwaysearch.model.SubwayNameService.JsonSubwayService;
import com.example.kyung.subwaysearch.util.MakeURL;
import com.example.kyung.subwaysearch.util.SearchSubway;
import com.google.gson.Gson;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-10-19.
 */

public class StationSearch extends FrameLayout implements RecyclerNumAdapter.SearchInfo{

    RecyclerView recyclerViewNum;
    RecyclerView recyclerViewUp;
    RecyclerView recyclerViewDown;
    EditText editText;
    TextView textSelect;
    Activity activity;
    View view;

    Gson gson;
    JsonSubwayInfo jsonSubwyInfoUp;
    JsonSubwayInfo jsonSubwyInfoDown;
    JsonSubwayLine jsonSubwayLine;
    JsonSubwayService jsonSubwayService;
    List<Row> rowsLine;
    List<com.example.kyung.subwaysearch.model.SubwayInfo.Row> rowInfo;
    List<com.example.kyung.subwaysearch.model.SubwayNameService.Row> rowName;

    String stationName;
    String subwayNameServiceURL;
    String subwayInfoURL;

    public StationSearch(Context context) {
        super(context);
        if(context instanceof Activity){
            activity = (Activity) context;
        }
        init();
        initView();
        initEditText();
    }

    private void init(){
        jsonSubwyInfoUp=null;
        jsonSubwyInfoDown=null;
        jsonSubwayLine=null;
        jsonSubwayService = null;
        rowsLine=new ArrayList<>();
        rowInfo = new ArrayList<>();
        rowName = new ArrayList<>();
        stationName = "";
        gson = new Gson();
    }

    private void initView(){
        view = LayoutInflater.from(getContext()).inflate(R.layout.view_stationsearch,null);
        recyclerViewUp = (RecyclerView) view.findViewById(R.id.recyclerViewUp);
        recyclerViewDown = (RecyclerView) view.findViewById(R.id.recyclerViewDown);
        editText = (EditText) view.findViewById(R.id.editText);
        textSelect = (TextView) view.findViewById(R.id.textSelect);
        addView(view);
    }

    // editText 동작
    private void initEditText(){
        // edittext의 엔터키를 검색표시로 변경
        editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        // 검색 동작
                        stationName = v.getText().toString();
                        jsonSubwayService = null;
                        subwayNameServiceURL = MakeURL.getInstance().makeSubwayNameServiceURL(stationName);
                        // url을 받아서 처리
                        new AsyncTask<Void, Void, String>(){
                            @Override
                            protected String doInBackground(Void... params) {
                                return Remote.getData(subwayNameServiceURL);
                            }

                            @Override
                            protected void onPostExecute(String s) {
                                if("ServerConError".equals(s)){
                                    Toast.makeText(getContext(), "서버연결이 원할하지 않습니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    jsonSubwayService = gson.fromJson(s, JsonSubwayService.class);
                                    if (jsonSubwayService.getSearchInfoBySubwayNameService() == null) {
                                        Toast.makeText(getContext(), "해당역이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        rowName = SearchSubway.getInstance().changeRowSubwayService(jsonSubwayService);
                                        setRecyclerViewNumListener();
                                    }
                                }
                            }
                        }.execute();
                        // 키보드 숨기기
                        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
                        break;
                    default:
                        // 기본 엔터키 동작
                        Toast.makeText(getContext(), "키 에러", Toast.LENGTH_SHORT).show();
                        return false;
                }
                return true;
            }
        });
        editText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode==KeyEvent.KEYCODE_ENTER)){
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
                    return true;
                }
                return false;
            }
        });
    }

    private void setRecyclerViewNumListener(){
        recyclerViewNum = null;
        recyclerViewNum = (RecyclerView) view.findViewById(R.id.recyclerViewNum);
        RecyclerNumAdapter adapter = new RecyclerNumAdapter(this,stationName,rowName);
        recyclerViewNum.setAdapter(adapter);
        recyclerViewNum.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setRecyclerViewUpListener(){
        recyclerViewUp = null;
        recyclerViewUp = (RecyclerView) view.findViewById(R.id.recyclerViewUp);
        RecyclerUpAdapter adapter = new RecyclerUpAdapter(this,rowInfo);
        recyclerViewUp.setAdapter(adapter);
        recyclerViewUp.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setRecyclerViewDownListener(){
        recyclerViewDown = null;
        recyclerViewDown = (RecyclerView) view.findViewById(R.id.recyclerViewDown);
        RecyclerDownAdapter adapter = new RecyclerDownAdapter(this,rowInfo);
        recyclerViewDown.setAdapter(adapter);
        recyclerViewDown.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    // 상,하행선 호출
    @Override
    public void SearchSubwayInfoUpByFR_CODE(String FR_CODE) {
        jsonSubwyInfoUp = null;
        jsonSubwyInfoDown = null;
        new AsyncTask<String, Void, String[]>(){
            @Override
            protected String[] doInBackground(String... params) {
                String result[] = new String[2];
                subwayInfoURL = MakeURL.getInstance().makeSubwayInfoURL(params[0],"1","1");
                result[0] = Remote.getData(subwayInfoURL);
                subwayInfoURL = MakeURL.getInstance().makeSubwayInfoURL(params[0],"1","2");
                result[1] = Remote.getData(subwayInfoURL);
                return result;
            }

            @Override
            protected void onPostExecute(String[] s) {
                if("ServerConError".equals(s[0])){
                    Toast.makeText(getContext(), "서버연결이 원할하지 않아 상행을 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    jsonSubwyInfoUp = gson.fromJson(s[0], JsonSubwayInfo.class);
                    if (jsonSubwyInfoUp.getSearchSTNTimeTableByFRCodeService() == null) {
                        Toast.makeText(getContext(), "데이터 로드 오류", Toast.LENGTH_SHORT).show();
                    } else {
                        rowInfo = SearchSubway.getInstance().changeRowSubwayInfo(jsonSubwyInfoUp);
                        setRecyclerViewUpListener();
                    }
                }
                if("ServerConError".equals(s)){
                    Toast.makeText(getContext(), "서버연결이 원할하지 않아 하행을 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    jsonSubwyInfoDown = gson.fromJson(s[1], JsonSubwayInfo.class);
                    if (jsonSubwyInfoDown.getSearchSTNTimeTableByFRCodeService() == null) {
                        Toast.makeText(getContext(), "데이터 로드 오류", Toast.LENGTH_SHORT).show();
                    } else {
                        rowInfo = SearchSubway.getInstance().changeRowSubwayInfo(jsonSubwyInfoDown);
                        setRecyclerViewDownListener();
                    }
                }

            }
        }.execute(FR_CODE);
    }
}
