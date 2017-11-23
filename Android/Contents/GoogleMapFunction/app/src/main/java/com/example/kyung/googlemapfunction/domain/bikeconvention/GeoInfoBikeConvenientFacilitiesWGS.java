package com.example.kyung.googlemapfunction.domain.bikeconvention;

import java.util.List;

/**
 * Created by Kyung on 2017-10-17.
 */

public class GeoInfoBikeConvenientFacilitiesWGS
{
    private RESULT RESULT;
    private String list_total_count;
    private List<Row> row;

    public RESULT getRESULT ()
    {
        return RESULT;
    }

    public void setRESULT (RESULT RESULT)
    {
        this.RESULT = RESULT;
    }

    public String getList_total_count ()
    {
        return list_total_count;
    }

    public void setList_total_count (String list_total_count)
    {
        this.list_total_count = list_total_count;
    }

    public List<Row> getRow ()
    {
        return row;
    }

    public void setRow (List<Row> row)
    {
        this.row = row;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [RESULT = "+RESULT+", list_total_count = "+list_total_count+", row = "+row+"]";
    }
}