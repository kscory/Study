package com.example.kyung.googlemapfunction.detail;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kyung.googlemapfunction.Const;
import com.example.kyung.googlemapfunction.R;
import com.example.kyung.googlemapfunction.domain.bikeconvention.Row;
import com.example.kyung.googlemapfunction.util.ImageLoadUtil;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

public class DetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textType;
    private TextView textAddress;
    private ImageView imageToolbar;
    private ViewGroup mapViewContainer;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Row bikeConvention = (Row) getIntent().getSerializableExtra(Const.KEY_BIKECONVENTION);
        initView();
        setValues(bikeConvention);
        setKaKaoMap(bikeConvention);

    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        textType = findViewById(R.id.textType);
        textAddress = findViewById(R.id.textAddress);
        imageToolbar = findViewById(R.id.imageToolbar);
        mapViewContainer = findViewById(R.id.kaKaoMap);
        mapView = new MapView(this);
        mapViewContainer.addView(mapView);
    }

    private void setValues(Row bikeConvention) {
        ImageLoadUtil.loadImageByPicasso(this, bikeConvention.getFILENAME(), imageToolbar);
        textType.setText(bikeConvention.getCLASS());
        textAddress.setText(bikeConvention.getADDRESS());
    }

    private void setKaKaoMap(Row bikeConvention){
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(bikeConvention.getLAT(),bikeConvention.getLNG()); // mapPoint 만들기
        setKaKaoMapMarker(mapPoint,bikeConvention.getADDRESS());
        // default Marker
        MapPoint defaultMapPoint = MapPoint.mapPointWithGeoCoord(Const.DEFAULT_LAT,Const.DEFAULT_LNG);
        setKaKaoMapMarker(defaultMapPoint,"기본위치");

        // 지도 나오는 범위를 조절
        MapPoint[] mapPoints = {defaultMapPoint, mapPoint};
        setKaKaoMapBounds(mapPoints);
    }

    /**
     * daum map 에서 마커를 세팅
     * @param mapPoint
     * @param MmarkerName
     */
    private void setKaKaoMapMarker(MapPoint mapPoint, String MmarkerName){
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(MmarkerName);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(marker);

        mapView.setMapCenterPoint(mapPoint,true); // 중심점 변경, true값은 animation
        mapView.setZoomLevel(9,true);
    }

    /**
     * 지도의 줌 바운드를 결정
     * @param mapPoints
     */
    private void setKaKaoMapBounds(MapPoint[] mapPoints){
        MapPointBounds mapPointBound = new MapPointBounds(mapPoints);
        int padding = 50;
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBound,padding));
    }
}

