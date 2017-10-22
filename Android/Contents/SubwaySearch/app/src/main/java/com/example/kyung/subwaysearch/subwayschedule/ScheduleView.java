package com.example.kyung.subwaysearch.subwayschedule;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyung.subwaysearch.CustomButton.LineButton;
import com.example.kyung.subwaysearch.R;
import com.example.kyung.subwaysearch.Remote;
import com.example.kyung.subwaysearch.model.SubwayInfo.JsonSubwayInfo;
import com.example.kyung.subwaysearch.model.SubwayInfo.Row;
import com.example.kyung.subwaysearch.model.SubwayNameService.JsonSubwayService;
import com.example.kyung.subwaysearch.util.MakeURL;
import com.example.kyung.subwaysearch.util.SearchSubway;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kyung on 2017-10-21.
 */

public class ScheduleView extends FrameLayout {

    ConstraintLayout constraintSearch;
    RelativeLayout linearLineBtnContainer;
    EditText stationMain;
    TextView stationRight;
    TextView stationLeft;
    TabLayout tabLayoutDay;
    ViewPager viewPagerSchedule;

    ScheduleTable scheduleTableDay;
    ScheduleTable scheduleTableSat;
    ScheduleTable scheduleTableSun;
    List<View> scheduleTableList = new ArrayList<>();

    HashMap<String, LineButton> buttonCollection = new HashMap<>();

    JsonSubwayInfo jsonSubwayInfo = null;
    JsonSubwayService jsonSubwayService = null;
    String jsonSubwayInfoURL = null;
    String jsonSubwayServiceURL = null;

    List<Row> rowsInfoUp = new ArrayList<>();
    List<Row> rowsInfoDown = new ArrayList<>();
    List<com.example.kyung.subwaysearch.model.SubwayNameService.Row> rowsService = new ArrayList<>();

    String stationName = null;
    String station_FR_CODE = null;
    String lineNum = null;
    int btnSize = 0;

    Gson gson;
    Activity activity;

    DayPagerAdapter dayPagerAdapter;

    public ScheduleView(Context context) {
        super(context);
        if(context instanceof Activity){
            activity = (Activity)context;
        }
        initView();
        init();
    }

    private void init(){
        rowsInfoDown.clear();
        rowsInfoUp.clear();
        rowsService.clear();
        scheduleTableList.clear();

        gson = new Gson();

        setDayTabLayout();
        setDayViewPager();
        setDayTabWithView();
        setEditText();
    }

    private void initView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.subwayschedule,null);
        constraintSearch = (ConstraintLayout) view.findViewById(R.id.constraintSearch);
        linearLineBtnContainer = (RelativeLayout) view.findViewById(R.id.linearLineBtnContainer);
        stationMain = (EditText) view.findViewById(R.id.stationMain);
        stationRight = (TextView) view.findViewById(R.id.stationRight);
        stationLeft = (TextView) view.findViewById(R.id.stationLeft);
        tabLayoutDay = (TabLayout) view.findViewById(R.id.tabLayoutDay);
        viewPagerSchedule = (ViewPager) view.findViewById(R.id.viewPagerSchedule);
        btnInit();

        scheduleTableDay = new ScheduleTable(getContext());
        scheduleTableSat = new ScheduleTable(getContext());
        scheduleTableSun = new ScheduleTable(getContext());
        addView(view);
    }

    private void btnInit(){
        btnSize = (int)(linearLineBtnContainer.getHeight() * 0.9);
        LineButton button1 = new LineButton(linearLineBtnContainer.getContext(),"1"); buttonCollection.put("1",button1);
        LineButton button2 = new LineButton(linearLineBtnContainer.getContext(),"2"); buttonCollection.put("2",button2);
        LineButton button3 = new LineButton(linearLineBtnContainer.getContext(),"3"); buttonCollection.put("3",button3);
        LineButton button4 = new LineButton(linearLineBtnContainer.getContext(),"4"); buttonCollection.put("4",button4);
        LineButton button5 = new LineButton(linearLineBtnContainer.getContext(),"5"); buttonCollection.put("5",button5);
        LineButton button6 = new LineButton(linearLineBtnContainer.getContext(),"6"); buttonCollection.put("6",button6);
        LineButton button7 = new LineButton(linearLineBtnContainer.getContext(),"7"); buttonCollection.put("7",button7);
        LineButton button8 = new LineButton(linearLineBtnContainer.getContext(),"8"); buttonCollection.put("8",button8);
        LineButton button9 = new LineButton(linearLineBtnContainer.getContext(),"9"); buttonCollection.put("9",button9);
        LineButton buttonI = new LineButton(linearLineBtnContainer.getContext(),"I"); buttonCollection.put("I",buttonI);
        LineButton buttonI2 = new LineButton(linearLineBtnContainer.getContext(),"I2"); buttonCollection.put("I2",buttonI2);
        LineButton buttonK = new LineButton(linearLineBtnContainer.getContext(),"K"); buttonCollection.put("K",buttonK);
        LineButton buttonKK = new LineButton(linearLineBtnContainer.getContext(),"KK"); buttonCollection.put("KK",buttonKK);
        LineButton buttonB = new LineButton(linearLineBtnContainer.getContext(),"B"); buttonCollection.put("B",buttonB);
        LineButton buttonA = new LineButton(linearLineBtnContainer.getContext(),"A"); buttonCollection.put("A",buttonA);
        LineButton buttonG = new LineButton(linearLineBtnContainer.getContext(),"G"); buttonCollection.put("G",buttonG);
        LineButton buttonS = new LineButton(linearLineBtnContainer.getContext(),"S"); buttonCollection.put("S",buttonS);
        LineButton buttonSS = new LineButton(linearLineBtnContainer.getContext(),"SS"); buttonCollection.put("SS",buttonSS);
    }
    // 호선 선택뷰 세팅
    private void setLineSelect(){
        // 가장 위 호선 세팅
        linearLineBtnContainer.removeAllViews();
        int x=20;
        for(int i=0 ; i<rowsService.size() ; i++){
            String line = rowsService.get(i).getLINE_NUM();
            buttonCollection.get(line).setX(x);
            Log.e("좌표","===================================="+buttonCollection.get(line).getX());
            linearLineBtnContainer.addView(buttonCollection.get(line),buttonCollection.get(line).getSize(),buttonCollection.get(line).getSize());
            buttonCollection.get(line).setPosition(i);
            Log.e("사이즈","======================="+buttonCollection.get(line).getWidth());
            x+=buttonCollection.get(line).getSize()+15;
        }

        // 스케쥴 및 나머지 업데이트
        startUpdate();
    }

    OnClickListener setLinebtnListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // 호선 이미지 업데이트
            setLineImage();
            // 스케쥴 및 나머지 업데이트
            startUpdate();
        }
    };
    // 호선 이미지 업데이트
    private void setLineImage(){
        /* 코드 작성 */
    }

    // 전역, 다음역 세팅, (리스너)
    private void setTextRight(String right){
        stationRight.setText(right +  ">");
        stationRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 스케쥴 및 나머지 업데이트
                startUpdate();
            }
        });
    }
    private void setTextLeft(String left){
        stationLeft.setText("<" + left);
        stationLeft.setOnClickListener(new OnClickListener() {
            @Override
            // 스케쥴 및 나머지 업데이트
            public void onClick(View v) {
                startUpdate();
            }
        });
    }

    // 시간표 세팅
    private void setDayViewPager(){
        scheduleTableList.clear();
        scheduleTableList.add(scheduleTableDay);
        scheduleTableList.add(scheduleTableSat);
        scheduleTableList.add(scheduleTableSun);
        dayPagerAdapter= new DayPagerAdapter(scheduleTableList);
        viewPagerSchedule.setAdapter(dayPagerAdapter);
    }
    private void setDayTabLayout(){
        tabLayoutDay.addTab(tabLayoutDay.newTab().setText("평일"));
        tabLayoutDay.addTab(tabLayoutDay.newTab().setText("토요일"));
        tabLayoutDay.addTab(tabLayoutDay.newTab().setText("주말/일요일"));
    }
    private void setDayTabWithView(){
        tabLayoutDay.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPagerSchedule));
        viewPagerSchedule.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutDay));
    }

    // 호선을 제외하고 모두 업데이트
    private void startUpdate(){
//        this.station_FR_CODE = station_FR_CODE;
        setTextLeft("임시");
        setTextRight("임시");
        setTimeSchedule();
    }

    // 스케줄(viewPager) 업데이트
    private void setTimeSchedule(){
        jsonSubwayInfo = null;
        new AsyncTask<String, Void, String[]>() {
            @Override
            protected String[] doInBackground(String... params) {
                String result[] = new String[6];
                jsonSubwayInfoURL = MakeURL.getInstance().makeSubwayInfoURL(params[0],"1","1");
                result[0] = Remote.getData(jsonSubwayInfoURL);
                jsonSubwayInfoURL = MakeURL.getInstance().makeSubwayInfoURL(params[0],"1","2");
                result[1] = Remote.getData(jsonSubwayInfoURL);
                jsonSubwayInfoURL = MakeURL.getInstance().makeSubwayInfoURL(params[0],"2","1");
                result[2] = Remote.getData(jsonSubwayInfoURL);
                jsonSubwayInfoURL = MakeURL.getInstance().makeSubwayInfoURL(params[0],"2","2");
                result[3] = Remote.getData(jsonSubwayInfoURL);
                jsonSubwayInfoURL = MakeURL.getInstance().makeSubwayInfoURL(params[0],"3","1");
                result[4] = Remote.getData(jsonSubwayInfoURL);
                jsonSubwayInfoURL = MakeURL.getInstance().makeSubwayInfoURL(params[0],"3","2");
                result[5] = Remote.getData(jsonSubwayInfoURL);
                return result;
            }
            @Override
            protected void onPostExecute(String[] jsonInfoString) {
                LoadTimeData(jsonInfoString[0],jsonInfoString[1]);
                scheduleTableDay.setDataByAdapter(rowsInfoUp,rowsInfoDown);
                LoadTimeData(jsonInfoString[2],jsonInfoString[3]);
                scheduleTableSat.setDataByAdapter(rowsInfoUp,rowsInfoDown);
                LoadTimeData(jsonInfoString[4],jsonInfoString[5]);
                scheduleTableSun.setDataByAdapter(rowsInfoUp,rowsInfoDown);
                dayPagerAdapter.notifyDataSetChanged();
            }
        }.execute(station_FR_CODE);
    }
    // 데이터를 불러오는 로직
    private void LoadTimeData(String jsonInfoUp, String jsonInfoDown){
        if("ServerConError".equals(jsonInfoUp)){
            Toast.makeText(getContext(), "서버 연결이 원할하지 않습니다.", Toast.LENGTH_SHORT).show();
        } else {
            jsonSubwayInfo = gson.fromJson(jsonInfoUp,JsonSubwayInfo.class);
            if(jsonSubwayInfo.getSearchSTNTimeTableByFRCodeService()==null){
                Toast.makeText(getContext(), "데이터 로드 오류", Toast.LENGTH_SHORT).show();
            } else{
                rowsInfoUp = SearchSubway.getInstance().changeRowSubwayInfo(jsonSubwayInfo);
            }
        }
        if("ServerConError".equals(jsonInfoDown)){
            Toast.makeText(getContext(), "서버 연결이 원할하지 않습니다.", Toast.LENGTH_SHORT).show();
        } else {
            jsonSubwayInfo = gson.fromJson(jsonInfoDown,JsonSubwayInfo.class);
            if(jsonSubwayInfo.getSearchSTNTimeTableByFRCodeService()==null){
                Toast.makeText(getContext(), "데이터 로드 오류", Toast.LENGTH_SHORT).show();
            } else{
                rowsInfoDown = SearchSubway.getInstance().changeRowSubwayInfo(jsonSubwayInfo);
            }
        }
    }

    // editText 세팅
    private void setEditText(){
        stationMain.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        stationMain.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId){
                    case EditorInfo.IME_ACTION_SEARCH:
                        stationName = v.getText().toString();
                        jsonSubwayService = null;
                        if(!"".equals(stationName)) {
                            jsonSubwayServiceURL = MakeURL.getInstance().makeSubwayNameServiceURL(stationName);
                            new AsyncTask<Void, Void, String>() {
                                @Override
                                protected String doInBackground(Void... params) {
                                    return Remote.getData(jsonSubwayServiceURL);
                                }

                                @Override
                                protected void onPostExecute(String subwayServiceString) {
                                    if ("ServerConError".equals(subwayServiceString)) {
                                        Toast.makeText(getContext(), "서버연결이 원할하지 않습니다.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        jsonSubwayService = gson.fromJson(subwayServiceString, JsonSubwayService.class);
                                        if (jsonSubwayService.getSearchInfoBySubwayNameService() == null) {
                                            Toast.makeText(getContext(), "역명이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            rowsService = SearchSubway.getInstance().changeRowSubwayService(jsonSubwayService);
                                            station_FR_CODE = rowsService.get(0).getFR_CODE();
                                            lineNum = rowsService.get(0).getLINE_NUM();
                                            setLineSelect();
                                        }
                                    }
                                }
                            }.execute();
                        }
                        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(stationMain.getWindowToken(),0);
                        break;
                    default:
                        // 기본 엔터키 동작
                        Toast.makeText(getContext(), "키 에러", Toast.LENGTH_SHORT).show();
                        return false;
                }
                return true;
            }
        });
        stationMain.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode==KeyEvent.KEYCODE_ENTER)){
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(stationMain.getWindowToken(),0);
                    return true;
                }
                return false;
            }
        });
    }
}
