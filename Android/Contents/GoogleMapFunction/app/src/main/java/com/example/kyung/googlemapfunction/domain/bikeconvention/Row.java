package com.example.kyung.googlemapfunction.domain.bikeconvention;

import java.io.Serializable;

/**
 * Created by Kyung on 2017-10-17.
 */

public class Row implements Serializable
{
    private String LAT;
    private String CLASS;
    private String ADDRESS;
    private String LNG;
    private String FILENAME;
    private String OBJECTID;

    public double getLAT ()
    {
        return Double.parseDouble(LAT);
    }

    public void setLAT (String LAT)
    {
        this.LAT = LAT;
    }

    public String getCLASS ()
    {
        return CLASS;
    }

    public void setCLASS (String CLASS)
    {
        this.CLASS = CLASS;
    }

    public String getADDRESS ()
    {
        return ADDRESS;
    }

    public void setADDRESS (String ADDRESS)
    {
        this.ADDRESS = ADDRESS;
    }

    public double getLNG ()
    {
        return Double.parseDouble(LNG);
    }

    public void setLNG (String LNG)
    {
        this.LNG = LNG;
    }

    public String getFILENAME ()
    {
        return FILENAME;
    }

    public void setFILENAME (String FILENAME)
    {
        this.FILENAME = FILENAME;
    }

    public String getOBJECTID ()
    {
        return OBJECTID;
    }

    public void setOBJECTID (String OBJECTID)
    {
        this.OBJECTID = OBJECTID;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [LAT = "+LAT+", CLASS = "+CLASS+", ADDRESS = "+ADDRESS+", LNG = "+LNG+", FILENAME = "+FILENAME+", OBJECTID = "+OBJECTID+"]";
    }
}