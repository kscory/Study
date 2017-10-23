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
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kyung.subwaysearch.CustomButton.CustomTab;
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
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kyung on 2017-10-21.
 */

public class ScheduleView extends FrameLayout {

    ConstraintLayout constraintSearch;
    RelativeLayout relativeLineBtnContainer;
    EditText stationMain;
    TextView stationRight;
    TextView stationLeft;
    TabLayout tabLayoutDay;
    ViewPager viewPagerSchedule;
    CustomTab tab1;
    CustomTab tab2;
    CustomTab tab3;

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
    int tabPosition=0;

    Gson gson;
    Activity activity;

    DayPagerAdapter dayPagerAdapter;

    public ScheduleView(Context context) {
        super(context);
        if(context instanceof Activity){
            activity = (Activity)context;
        }
        initView();
        initLineBtnListener();
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
        relativeLineBtnContainer = (RelativeLayout) view.findViewById(R.id.relativeLineBtnContainer);
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
        btnSize = (int)(relativeLineBtnContainer.getHeight() * 0.9);
        LineButton button1 = new LineButton(relativeLineBtnContainer.getContext(),"1"); buttonCollection.put("1",button1);
        LineButton button2 = new LineButton(relativeLineBtnContainer.getContext(),"2"); buttonCollection.put("2",button2);
        LineButton button3 = new LineButton(relativeLineBtnContainer.getContext(),"3"); buttonCollection.put("3",button3);
        LineButton button4 = new LineButton(relativeLineBtnContainer.getContext(),"4"); buttonCollection.put("4",button4);
        LineButton button5 = new LineButton(relativeLineBtnContainer.getContext(),"5"); buttonCollection.put("5",button5);
        LineButton button6 = new LineButton(relativeLineBtnContainer.getContext(),"6"); buttonCollection.put("6",button6);
        LineButton button7 = new LineButton(relativeLineBtnContainer.getContext(),"7"); buttonCollection.put("7",button7);
        LineButton button8 = new LineButton(relativeLineBtnContainer.getContext(),"8"); buttonCollection.put("8",button8);
        LineButton button9 = new LineButton(relativeLineBtnContainer.getContext(),"9"); buttonCollection.put("9",button9);
        LineButton buttonI = new LineButton(relativeLineBtnContainer.getContext(),"I"); buttonCollection.put("I",buttonI);
        LineButton buttonI2 = new LineButton(relativeLineBtnContainer.getContext(),"I2"); buttonCollection.put("I2",buttonI2);
        LineButton buttonK = new LineButton(relativeLineBtnContainer.getContext(),"K"); buttonCollection.put("K",buttonK);
        LineButton buttonKK = new LineButton(relativeLineBtnContainer.getContext(),"KK"); buttonCollection.put("KK",buttonKK);
        LineButton buttonB = new LineButton(relativeLineBtnContainer.getContext(),"B"); buttonCollection.put("B",buttonB);
        LineButton buttonA = new LineButton(relativeLineBtnContainer.getContext(),"A"); buttonCollection.put("A",buttonA);
        LineButton buttonG = new LineButton(relativeLineBtnContainer.getContext(),"G"); buttonCollection.put("G",buttonG);
        LineButton buttonS = new LineButton(relativeLineBtnContainer.getContext(),"S"); buttonCollection.put("S",buttonS);
        LineButton buttonSS = new LineButton(relativeLineBtnContainer.getContext(),"SS"); buttonCollection.put("SS",buttonSS);
        relativeLineBtnContainer.addView(button1,button1.getSize(),button1.getSize()); // 테스트용
    }
    private void initLineBtnListener(){
        for(String lineName : buttonCollection.keySet()){
            buttonCollection.get(lineName).setOnClickListener(setLineBtnListener);
        }
    }
    // 호선 선택뷰 세팅
    private void setLineSelect(){
        // 가장 위 호선 세팅
        relativeLineBtnContainer.removeAllViews();
        int x=20;
        for(int i=0 ; i<rowsService.size() ; i++){
            String line = rowsService.get(i).getLINE_NUM();
            buttonCollection.get(line).setX(x);
            relativeLineBtnContainer.addView(buttonCollection.get(line),buttonCollection.get(line).getSize(),buttonCollection.get(line).getSize());
            buttonCollection.get(line).setPosition(i);
            x+=buttonCollection.get(line).getSize()+15;
        }
        if(lineNum!=null){
            buttonCollection.get(lineNum).unClickButton();
        }
        station_FR_CODE = rowsService.get(0).getFR_CODE();
        lineNum = rowsService.get(0).getLINE_NUM();
        buttonCollection.get(lineNum).clickButton();
        constraintSearch.setBackgroundColor(buttonCollection.get(lineNum).getColor());
        // 스케쥴 및 나머지 업데이트
        startUpdate();
    }

    OnClickListener setLineBtnListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(rowsService.size()>0) {
                LineButton btn = (LineButton) v;
                // FR_CODE 변경
                station_FR_CODE = rowsService.get(btn.getPosition()).getFR_CODE();
                // 이전 호선 이미지 원본으로
                buttonCollection.get(lineNum).unClickButton();
                // lineNum 변경
                lineNum = rowsService.get(btn.getPosition()).getLINE_NUM();
                // 호선 이미지 업데이트
                buttonCollection.get(lineNum).clickButton();
                constraintSearch.setBackgroundColor(btn.getColor());
                // 스케쥴 및 나머지 업데이트
                startUpdate();
            }
        }
    };

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
        tab1 = new CustomTab(getContext(),"평일");
        tab2 = new CustomTab(getContext(),"토요일");
        tab3 = new CustomTab(getContext(),"공휴일/일요일");
        tab1.clickTab();
        tabLayoutDay.addTab(tabLayoutDay.newTab().setCustomView(tab1));
        tabLayoutDay.addTab(tabLayoutDay.newTab().setCustomView(tab2));
        tabLayoutDay.addTab(tabLayoutDay.newTab().setCustomView(tab3));
        tabLayoutDay.setBackgroundColor(Color.LTGRAY);
        tabLayoutDay.setSelectedTabIndicatorColor(Color.LTGRAY);
        tabPosition = tabLayoutDay.getSelectedTabPosition();
    }

    private void setDayTabWithView(){
        viewPagerSchedule.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutDay));
//        tabLayoutDay.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPagerSchedule));
        tabLayoutDay.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerSchedule.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()){
                    case 0: tab1.clickTab(); break;
                    case 1: tab2.clickTab(); break;
                    case 2: tab3.clickTab(); break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0: tab1.unClickTab(); break;
                    case 1: tab2.unClickTab(); break;
                    case 2: tab3.unClickTab(); break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
