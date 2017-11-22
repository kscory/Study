package com.example.kyung.googlemapfunction.cluster;

import android.content.Context;

import com.example.kyung.googlemapfunction.domain.Row;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import java.util.List;

/**
 * 1. 클러스터 매니저 초기화
 * 2. 클러스터 매니저에 마커를 등록
 */

public class CustomClusterManager {

    ClusterManager<MarkerItem> clusterManager;
    GoogleMap googleMap;

    public CustomClusterManager(Context context, GoogleMap googleMap){
        // 1. 클러스터 매니저 초기화
        clusterManager = new ClusterManager<MarkerItem>(context, googleMap);
        this.googleMap = googleMap;
    }

    private void init(){
        setClickListener();

    }

    private void setClickListener(){
        // 클러스터 그룹 클릭시 효과
        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MarkerItem>() {
            @Override
            public boolean onClusterClick(Cluster<MarkerItem> cluster) {
                // 클릭하면 그 보관대 그룹의 정보를 recyclerView로 보여줌
                return false;
            }
        });

        // 단일 마커 클릭시 효과
        clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MarkerItem>() {
            @Override
            public boolean onClusterItemClick(MarkerItem markerItem) {
                // 클릭하면 그 보관대 정보를 보여줌
                return false;
            }
        });
    }

    public void setCluster(List<Row> bikeConventions){
        clusterManager.clearItems();
        for(Row conInfo : bikeConventions){
            // 2. 클러스터 매니저에 마커를 등록
            MarkerItem markerItem = new MarkerItem(conInfo.getLAT(),conInfo.getLNG());

        }
    }


}
