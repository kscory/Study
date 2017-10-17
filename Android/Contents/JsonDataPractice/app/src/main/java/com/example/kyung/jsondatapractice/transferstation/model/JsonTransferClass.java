package com.example.kyung.jsondatapractice.transferstation.model;

/**
 * Created by Kyung on 2017-10-17.
 */

public class JsonTransferClass {
    private StationDayTrnsitNmpr StationDayTrnsitNmpr;

    public StationDayTrnsitNmpr getStationDayTrnsitNmpr() {
        return StationDayTrnsitNmpr;
    }

    public void setStationDayTrnsitNmpr(StationDayTrnsitNmpr StationDayTrnsitNmpr) {
        this.StationDayTrnsitNmpr = StationDayTrnsitNmpr;
    }

    @Override
    public String toString() {
        return "ClassPojo [StationDayTrnsitNmpr = " + StationDayTrnsitNmpr + "]";
    }
}