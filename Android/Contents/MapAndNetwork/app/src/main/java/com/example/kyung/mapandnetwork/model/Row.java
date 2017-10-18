package com.example.kyung.mapandnetwork.model;

/**
 * Created by Kyung on 2017-10-18.
 */

public class Row {
    private String HOSP_NM;
    private String LAT;
    private String HOSP_ID;
    private String LNG;
    private String GISID;
    private String HOSP_CD;
    private String OBJECTID;

    public String getHOSP_NM() {
        return HOSP_NM;
    }

    public void setHOSP_NM(String HOSP_NM) {
        this.HOSP_NM = HOSP_NM;
    }

    public String getLAT() {
        return LAT;
    }

    public void setLAT(String LAT) {
        this.LAT = LAT;
    }

    public String getHOSP_ID() {
        return HOSP_ID;
    }

    public void setHOSP_ID(String HOSP_ID) {
        this.HOSP_ID = HOSP_ID;
    }

    public String getLNG() {
        return LNG;
    }

    public void setLNG(String LNG) {
        this.LNG = LNG;
    }

    public String getGISID() {
        return GISID;
    }

    public void setGISID(String GISID) {
        this.GISID = GISID;
    }

    public String getHOSP_CD() {
        return HOSP_CD;
    }

    public void setHOSP_CD(String HOSP_CD) {
        this.HOSP_CD = HOSP_CD;
    }

    public String getOBJECTID() {
        return OBJECTID;
    }

    public void setOBJECTID(String OBJECTID) {
        this.OBJECTID = OBJECTID;
    }

    @Override
    public String toString() {
        return "ClassPojo [HOSP_NM = " + HOSP_NM + ", LAT = " + LAT + ", HOSP_ID = " + HOSP_ID + ", LNG = " + LNG + ", GISID = " + GISID + ", HOSP_CD = " + HOSP_CD + ", OBJECTID = " + OBJECTID + "]";
    }
}

