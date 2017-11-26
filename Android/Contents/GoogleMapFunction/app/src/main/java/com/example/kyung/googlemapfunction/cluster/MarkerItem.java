package com.example.kyung.googlemapfunction.cluster;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Cluster를 위한 마커 생성
 */

public class MarkerItem implements ClusterItem {
    private LatLng position;
    private String objId;

    public MarkerItem(double lat, double lng, String objId){
        position = new LatLng(lat,lng);
        this.objId = objId;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }
    public String getObjId() {
        return objId;
    }
}
