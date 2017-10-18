package com.example.kyung.mapandnetwork;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.kyung.mapandnetwork.model.JsonHos;
import com.example.kyung.mapandnetwork.model.Row;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    JsonHos jsonHosData;
    Row[] rowHos = null;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        load();
    }

    public void load(){
        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... params) {
                return Remote.getData("http://openapi.seoul.go.kr:8088/키 입력/json/SdeGiGhospP2015WGS/1/1000/");
            }

            @Override
            protected void onPostExecute(String jsonHospital) {
                if(jsonHospital=="ConnectionError"){
                    Toast.makeText(MapsActivity.this, "접속이 원할하지 않습니다.", Toast.LENGTH_SHORT).show();
                } else{
                    Gson gson = new Gson();
                    jsonHosData = gson.fromJson(jsonHospital,JsonHos.class);
                    rowHos = jsonHosData.getSdeGiGhospP2015WGS().getRow();
                    Log.d("MapsActivity","====="+rowHos);
                    // 맵이 사용할 준비가 되었는지를 비동기로 확인
                    // 맵이 사용할 준비가 되었으면 -> onMapReadyCallback.onMapReady를 호출
                    mapFragment.getMapAsync(MapsActivity.this);
                }
            }
        }.execute();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng point;
        for(Row hos : rowHos){
            point = new LatLng(Double.parseDouble(hos.getLAT()),Double.parseDouble(hos.getLNG()));
            mMap.addMarker(new MarkerOptions().position(point).title(hos.getHOSP_NM()));
        }
        LatLng location = new LatLng(37.5669029,126.9889492);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,12));
    }
}
