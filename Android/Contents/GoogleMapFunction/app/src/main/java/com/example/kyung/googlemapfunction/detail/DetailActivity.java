package com.example.kyung.googlemapfunction.detail;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
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
    private NestedScrollView nestedScrollView;
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
        setScrollPreventionByMap();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        nestedScrollView = findViewById(R.id.nestedScrollView);
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
        // 마커 세팅하기 (MapPoint를 만들어 세팅)
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(bikeConvention.getLAT(),bikeConvention.getLNG());
        setKaKaoMapMarker(mapPoint,bikeConvention.getADDRESS());
        MapPoint defaultMapPoint = MapPoint.mapPointWithGeoCoord(Const.DEFAULT_LAT,Const.DEFAULT_LNG);
        setKaKaoMapMarker(defaultMapPoint,"기본위치");
        // 지도 나오는 범위를 조절
        MapPoint[] mapPoints = {defaultMapPoint, mapPoint};
        setKaKaoMapBounds(mapPoints);
    }

    /**
     * daum map 에서 마커를 세팅
     * @param mapPoint
     * @param markerName
     */
    private void setKaKaoMapMarker(MapPoint mapPoint, String markerName){
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(markerName);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);
        // 마커로 포인트를 이동 및 줌 시킴 (boolean 값은 animation 을 활성화할지 결정)
        mapView.setMapCenterPoint(mapPoint,true);
        mapView.setZoomLevel(9,true);
        // 클릭효과 등록
        setMarkerClickListener(marker);
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

    /**
     * 마커의 클릭효과를 세팅
     * @param marker
     */
    private void setMarkerClickListener(MapPOIItem marker){
        mapView.setPOIItemEventListener(new MapView.POIItemEventListener() {
            @Override
            public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
                // POI Item 아이콘(마커)를 터치한 경우 호출
            }
            @Override
            public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
                // 삭제된 메소드
            }
            @Override
            public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
                // 클릭시 호출될 말풍선을 호출
                // CalloutBalloonButtonType : 말풍선의 버튼 타입을 의미
            }
            @Override
            public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
                // 마커의 drag 를 가능하게 하고 그 마커의 drag가 되었을 시에 호출
            }
        });
    }

    private void setScrollPreventionByMap(){
        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
    }
}

