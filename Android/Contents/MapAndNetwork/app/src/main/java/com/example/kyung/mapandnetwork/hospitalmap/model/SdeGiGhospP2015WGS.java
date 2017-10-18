package com.example.kyung.mapandnetwork.hospitalmap.model;

import com.example.kyung.mapandnetwork.hospitalmap.model.RESULT;
import com.example.kyung.mapandnetwork.hospitalmap.model.Row;

/**
 * Created by Kyung on 2017-10-18.
 */

public class SdeGiGhospP2015WGS {
    private com.example.kyung.mapandnetwork.hospitalmap.model.RESULT RESULT;
    private String list_total_count;
    private Row[] row;

    public RESULT getRESULT() {
        return RESULT;
    }

    public void setRESULT(RESULT RESULT) {
        this.RESULT = RESULT;
    }

    public String getList_total_count() {
        return list_total_count;
    }

    public void setList_total_count(String list_total_count) {
        this.list_total_count = list_total_count;
    }

    public Row[] getRow() {
        return row;
    }

    public void setRow(Row[] row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "ClassPojo [RESULT = " + RESULT + ", list_total_count = " + list_total_count + ", row = " + row + "]";
    }
}