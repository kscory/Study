package com.example.kyung.subwaysearch.util;

/**
 * Created by Kyung on 2017-10-19.
 */

public class MakeURL {
    //Singleton
    private static MakeURL instance = null;
    private MakeURL(){}
    public static MakeURL getInstance(){
        if(instance == null){
            instance = new MakeURL();
        }
        return instance;
    }
    // 호선별 지하철 정보를 담은 URL을 가져옴
    public String makeSubwayLineURL(String LineNumber) {
        // 1~9: 1~9호선, I: 인천1호선, I2: 인천2호선, K: 경의중앙선, KK: 경강선 B: 분당선, A: 공항철도, G: 경춘선, S:신분당선, SU:수인선
        return "http://openapi.seoul.go.kr:8088/인증키/json/SearchSTNBySubwayLineService/1/101/" + LineNumber + "/";
    }
    // 특정 지하철 정보를 담은 URL을 가져옴
    public String makeSubwayInfoURL(String FR_CODE, String Week, String INOUT){
        // FR_CODE = 외부코드
        // Week = 평일 : 1, 토요일 : 2, 휴일/일요일 : 3
        // INOUT = 상행 : 1, 하행 : 2
        return "http://openapi.seoul.go.kr:8088/인증키/json/SearchSTNTimeTableByFRCodeService/1/500/"+FR_CODE+"/"+Week+"/"+INOUT+"/";
    }
    public String makeArrivalSubwayNowURL(String FR_CODE, String Week, String INOUT){
        // FR_CODE = 외부코드
        // Week = 평일 : 1, 토요일 : 2, 휴일/일요일 : 3
        // INOUT = 상행 : 1, 하행 : 2
        return "http://openapi.seoul.go.kr:8088/인증키/json/SearchArrivalTimeOfLine2SubwayByFRCodeService/1/129/"+FR_CODE+"/"+Week+"/"+INOUT+"/";
    }
    public String makeSubwayNameServiceURL(String stationName){
        // stationName = 역명
        return "http://openapi.seoul.go.kr:8088/인증키/json/SearchInfoBySubwayNameService/1/5/"+stationName+"/";
    }
}
