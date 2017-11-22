package com.example.kyung.googlemapfunction;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.kyung.googlemapfunction.domain.Json;
import com.example.kyung.googlemapfunction.domain.Row;
import com.example.kyung.googlemapfunction.util.RemoteUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        load();
    }

    List<Row> bikeConventionList = new ArrayList<>();
    int totalCount = 0;
    private void load(){
        // 총 개수가 1808개이므로 이를 반복
        new AsyncTask<Integer, Void ,String>(){
            @Override
            protected String doInBackground(Integer... count) {
                int firstCount = count[0];
                totalCount = count[0] + 500;
                String url = BuildConfig.SERVER_URL+ firstCount + "/" + totalCount + "/";
                return RemoteUtil.getData(url);
            }

            @Override
            protected void onPostExecute(String jsonString) {

                Gson gson = new Gson();
                Json json = gson.fromJson(jsonString,Json.class);
                bikeConventionList.addAll(json.getGeoInfoBikeConvenientFacilitiesWGS().getRow());
                if(totalCount<1808) load();
            }
        }.execute(0);
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
