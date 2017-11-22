package com.example.kyung.googlemapfunction.cluster;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Cluster를 위한 마커 생성
 */

public class MarkerItem implements ClusterItem {
    private LatLng position;

    public MarkerItem(double lat, double lng){
        position = new LatLng(lat,lng);
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

}
