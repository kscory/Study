package com.example.kyung.googlemapfunction.domain.bikeconvention;

/**
 * Created by Kyung on 2017-10-17.
 */

public class Json
{
    private GeoInfoBikeConvenientFacilitiesWGS GeoInfoBikeConvenientFacilitiesWGS;

    public GeoInfoBikeConvenientFacilitiesWGS getGeoInfoBikeConvenientFacilitiesWGS ()
    {
        return GeoInfoBikeConvenientFacilitiesWGS;
    }

    public void setGeoInfoBikeConvenientFacilitiesWGS (GeoInfoBikeConvenientFacilitiesWGS GeoInfoBikeConvenientFacilitiesWGS)
    {
        this.GeoInfoBikeConvenientFacilitiesWGS = GeoInfoBikeConvenientFacilitiesWGS;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [GeoInfoBikeConvenientFacilitiesWGS = "+GeoInfoBikeConvenientFacilitiesWGS+"]";
    }
}