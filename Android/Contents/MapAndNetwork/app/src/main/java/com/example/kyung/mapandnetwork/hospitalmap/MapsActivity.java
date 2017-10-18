package com.example.kyung.mapandnetwork.hospitalmap;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.kyung.mapandnetwork.MainActivity;
import com.example.kyung.mapandnetwork.R;
import com.example.kyung.mapandnetwork.Remote;
import com.example.kyung.mapandnetwork.hospitalmap.model.JsonHos;
import com.example.kyung.mapandnetwork.hospitalmap.model.Row;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

public class MapsActivity extends Fragment implements OnMapReadyCallback {

    public MapsActivity() {
        // Required empty public constructor
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            activity = (Activity) context;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mapView !=null){
            mapView.onCreate(savedInstanceState);
        }
    }

    MapView mapView;
    private GoogleMap mMap;
    JsonHos jsonHosData;
    // 좌표 데이터를 저장하기 위한 저장소
    Row[] rowHos = null;
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mapview, container, false);
        mapView = (MapView) view.findViewById(R.id.mapView);
        load();

        return view;
    }

    public void load(){
        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... params) {
                return Remote.getData("http://openapi.seoul.go.kr:8088/(인증키)/json/SdeGiGhospP2015WGS/1/100/");
            }

            @Override
            protected void onPostExecute(String jsonHospital) {
                if(jsonHospital=="ConnectionError"){
                    Toast.makeText(activity, "접속이 원할하지 않습니다.", Toast.LENGTH_SHORT).show();
                } else{
                    Gson gson = new Gson();
                    jsonHosData = gson.fromJson(jsonHospital,JsonHos.class);
                    rowHos = jsonHosData.getSdeGiGhospP2015WGS().getRow();
                    // 맵이 사용할 준비가 되었는지를 비동기로 확인
                    // 맵이 사용할 준비가 되었으면 -> onMapReadyCallback.onMapReady를 호출
                    mapView.getMapAsync(MapsActivity.this);
                }
            }
        }.execute();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("onMapReady","=================연결완료");
        mMap = googleMap;
        LatLng point;
        // 데이터를 사용해서 마크를 각 좌표에 출력
        for(Row hos : rowHos){
            point = new LatLng(Double.parseDouble(hos.getLAT()),Double.parseDouble(hos.getLNG()));
            mMap.addMarker(new MarkerOptions().position(point).title(hos.getHOSP_NM()));
        }
        LatLng location = new LatLng(37.5669029,126.9889492);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,12)); // location 위치로 움직임
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }
}
