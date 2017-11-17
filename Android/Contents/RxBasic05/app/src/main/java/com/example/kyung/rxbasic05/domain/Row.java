package com.example.kyung.rxbasic05.domain;

/**
 * Created by Kyung on 2017-11-16.
 */

public class Row {
    private String SAWS_SOLAR;
    private String SAWS_OBS_TM;
    private String SAWS_SHINE;
    private String SAWS_WS_AVG;
    private String NAME;
    private String SAWS_HD;
    private String STN_NM;
    private String SAWS_RN_SUM;
    private String STN_ID;
    private String CODE;
    private String SAWS_TA_AVG;

    public String getSAWS_SOLAR() {
        return SAWS_SOLAR;
    }

    public void setSAWS_SOLAR(String SAWS_SOLAR) {
        this.SAWS_SOLAR = SAWS_SOLAR;
    }

    public String getSAWS_OBS_TM() {
        return SAWS_OBS_TM;
    }

    public void setSAWS_OBS_TM(String SAWS_OBS_TM) {
        this.SAWS_OBS_TM = SAWS_OBS_TM;
    }

    public String getSAWS_SHINE() {
        return SAWS_SHINE;
    }

    public void setSAWS_SHINE(String SAWS_SHINE) {
        this.SAWS_SHINE = SAWS_SHINE;
    }

    public String getSAWS_WS_AVG() {
        return SAWS_WS_AVG;
    }

    public void setSAWS_WS_AVG(String SAWS_WS_AVG) {
        this.SAWS_WS_AVG = SAWS_WS_AVG;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getSAWS_HD() {
        return SAWS_HD;
    }

    public void setSAWS_HD(String SAWS_HD) {
        this.SAWS_HD = SAWS_HD;
    }

    public String getSTN_NM() {
        return STN_NM;
    }

    public void setSTN_NM(String STN_NM) {
        this.STN_NM = STN_NM;
    }

    public String getSAWS_RN_SUM() {
        return SAWS_RN_SUM;
    }

    public void setSAWS_RN_SUM(String SAWS_RN_SUM) {
        this.SAWS_RN_SUM = SAWS_RN_SUM;
    }

    public String getSTN_ID() {
        return STN_ID;
    }

    public void setSTN_ID(String STN_ID) {
        this.STN_ID = STN_ID;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getSAWS_TA_AVG() {
        return SAWS_TA_AVG;
    }

    public void setSAWS_TA_AVG(String SAWS_TA_AVG) {
        this.SAWS_TA_AVG = SAWS_TA_AVG;
    }

    @Override
    public String toString() {
        return "ClassPojo [SAWS_SOLAR = " + SAWS_SOLAR + ", SAWS_OBS_TM = " + SAWS_OBS_TM + ", SAWS_SHINE = " + SAWS_SHINE + ", SAWS_WS_AVG = " + SAWS_WS_AVG + ", NAME = " + NAME + ", SAWS_HD = " + SAWS_HD + ", STN_NM = " + STN_NM + ", SAWS_RN_SUM = " + SAWS_RN_SUM + ", STN_ID = " + STN_ID + ", CODE = " + CODE + ", SAWS_TA_AVG = " + SAWS_TA_AVG + "]";
    }
}
