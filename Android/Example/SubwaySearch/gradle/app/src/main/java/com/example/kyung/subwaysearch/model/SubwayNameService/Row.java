package com.example.kyung.subwaysearch.model.SubwayNameService;

/**
 * Created by Kyung on 2017-10-19.
 */

public class Row {

    private boolean clicked = false;

    private String STATION_NM; // 전철역 명
    private String STATION_CD; // 전철역 코드
    private String LINE_NUM; // 호선번호
    private String FR_CODE; // 외부코드

    public String getSTATION_NM() {
        return STATION_NM;
    }

    public void setSTATION_NM(String STATION_NM) {
        this.STATION_NM = STATION_NM;
    }

    public String getSTATION_CD() {
        return STATION_CD;
    }

    public void setSTATION_CD(String STATION_CD) {
        this.STATION_CD = STATION_CD;
    }

    public String getLINE_NUM() {
        return LINE_NUM;
    }

    public void setLINE_NUM(String LINE_NUM) {
        this.LINE_NUM = LINE_NUM;
    }

    public String getFR_CODE() {
        return FR_CODE;
    }

    public void setFR_CODE(String FR_CODE) {
        this.FR_CODE = FR_CODE;
    }

    public void click(){
        clicked = true;
    }
    public void unClick(){
        clicked = false;
    }
    public boolean getClicked(){
        return clicked;
    }

    @Override
    public String toString() {
        return "ClassPojo [STATION_NM = " + STATION_NM + ", STATION_CD = " + STATION_CD + ", LINE_NUM = " + LINE_NUM + ", FR_CODE = " + FR_CODE + "]";
    }
}

