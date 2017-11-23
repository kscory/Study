package com.example.kyung.googlemapfunction.cluster;

import android.content.Context;

import com.example.kyung.googlemapfunction.contract.Contract;
import com.example.kyung.googlemapfunction.domain.bikeconvention.Row;
import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 1. 클러스터 매니저 초기화
 * 2. 클러스터 매니저에 마커를 등록
 */

public class CustomClusterManager {

    ClusterManager<MarkerItem> clusterManager;
    GoogleMap googleMap;
    Contract.IClickAction clickAction;

    public CustomClusterManager(Context context, GoogleMap googleMap){
        // 1. 클러스터 매니저 초기화
        clusterManager = new ClusterManager<MarkerItem>(context, googleMap);

        if(context instanceof Contract.IClickAction)
            clickAction = (Contract.IClickAction)context;
        else
            throw new RuntimeException(context.toString()  + " must implement IClickAction");

        this.googleMap = googleMap;
        init();
    }

    private void init(){
        setClickListener();
    }

    private void setClickListener(){
        // 클러스터 그룹 클릭시 효과
        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MarkerItem>() {
            @Override
            public boolean onClusterClick(Cluster<MarkerItem> cluster) {
                // 클릭하면 obj 리스트를 넘겨서 보관대 그룹의 정보를 recyclerView로 보여주도록 설계할 것
                List<String> objs = new ArrayList<>();
                for(MarkerItem markerItem : cluster.getItems()){
                    objs.add(markerItem.getObjId());
                }
                clickAction.showList(objs);
                return false;
            }
        });

        // 단일 마커 클릭시 효과
        clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MarkerItem>() {
            @Override
            public boolean onClusterItemClick(MarkerItem markerItem) {
                // 특정 보관대의 정보를 보여주도록 설계할 것
                clickAction.showDetail(markerItem.getObjId());
                return false;
            }
        });
    }

    public void setCluster(List<Row> bikeConventions){
        clusterManager.clearItems();
        for(Row conInfo : bikeConventions){
            // 2. 클러스터 매니저에 마커를 등록
            MarkerItem markerItem = new MarkerItem(conInfo.getLAT(), conInfo.getLNG(), conInfo.getOBJECTID());
            clusterManager.addItem(markerItem);
        }
    }
}