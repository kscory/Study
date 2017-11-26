package com.example.kyung.googlemapfunction.cluster;

import android.content.Context;
import android.util.Log;

import com.example.kyung.googlemapfunction.Const;
import com.example.kyung.googlemapfunction.contract.Contract;
import com.example.kyung.googlemapfunction.domain.bikeconvention.Row;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
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

    /**
     * 1. 클러스터 매니저 초기화
     * @param context
     * @param googleMap
     */
    public CustomClusterManager(Context context, GoogleMap googleMap){
        // 1. 클러스터 매니저 초기화
        clusterManager = new ClusterManager<MarkerItem>(context, googleMap);
        if(context instanceof Contract.IClickAction)
            clickAction = (Contract.IClickAction)context;
        else throw new RuntimeException(context.toString()  + " must implement IClickAction");
        this.googleMap = googleMap;
        init();
    }

    private void init(){
        setClickListener();
        setMapListener();
    }

    private void setMapListener(){
        googleMap.setOnCameraIdleListener(clusterManager);
        googleMap.setOnMarkerClickListener(clusterManager);
    }

    /**
     * 2. 클러스터에 대한 특정 효과 추가
     *    - 마커에 대한 크릭효과 추가
     */
    private void setClickListener(){
        // 클러스터 그룹 클릭시 효과
        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MarkerItem>() {
            @Override
            public boolean onClusterClick(Cluster<MarkerItem> cluster) {
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
                clickAction.showDetail(markerItem.getObjId());
                return false;
            }
        });
    }

    /**
     * 3. 클러스터 매니저에 마커를 등록
     *    - 이를 호출하면 맵에 있는 클러스터를 수정하게 된다.
     * @param bikeConventions
     */
    public void setCluster(List<Row> bikeConventions){
        clusterManager.clearItems();
        for(Row conInfo : bikeConventions){
            // 2. 클러스터 매니저에 마커를 등록
            MarkerItem markerItem = new MarkerItem(conInfo.getLAT(), conInfo.getLNG(), conInfo.getOBJECTID());
            clusterManager.addItem(markerItem);
        }
        if(bikeConventions.size() > 1000 || bikeConventions.size()<=0) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Const.DEFAULT_LAT, Const.DEFAULT_LNG), 10));
        } else {
            LatLng latLng = new LatLng(bikeConventions.get(bikeConventions.size()-1).getLAT(), bikeConventions.get(bikeConventions.size()-1).getLNG());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
        }
    }
}
