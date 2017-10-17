package com.example.kyung.jsondatapractice.transferstation.model;

/**
 * Created by Kyung on 2017-10-17.
 */

public class Row {
    private String STATN_NM;
    private String SUNDAY;
    private String WKDAY;
    private String SN;
    private String SATDAY;

    public String getSTATN_NM() {
        return STATN_NM;
    }

    public void setSTATN_NM(String STATN_NM) {
        this.STATN_NM = STATN_NM;
    }

    public String getSUNDAY() {
        return SUNDAY;
    }

    public void setSUNDAY(String SUNDAY) {
        this.SUNDAY = SUNDAY;
    }

    public String getWKDAY() {
        return WKDAY;
    }

    public void setWKDAY(String WKDAY) {
        this.WKDAY = WKDAY;
    }

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public String getSATDAY() {
        return SATDAY;
    }

    public void setSATDAY(String SATDAY) {
        this.SATDAY = SATDAY;
    }

    @Override
    public String toString() {
        return "ClassPojo [STATN_NM = " + STATN_NM + ", SUNDAY = " + SUNDAY + ", WKDAY = " + WKDAY + ", SN = " + SN + ", SATDAY = " + SATDAY + "]";
    }
}