package com.example.kyung.googlemapfunction;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.kyung.googlemapfunction.contract.Contract;
import com.example.kyung.googlemapfunction.domain.BikeConventionDao;
import com.example.kyung.googlemapfunction.domain.bikeconvention.Json;
import com.example.kyung.googlemapfunction.domain.bikeconvention.Row;
import com.example.kyung.googlemapfunction.util.LoadSizeUtil;
import com.example.kyung.googlemapfunction.util.RemoteUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, Contract.IClickAction {


    private Button btnSearch;
    private EditText editArea;
    private ProgressBar mapProgress;
    private RecyclerView recyclerView;
    private ProgressBar listProgress;
    private SupportMapFragment mapFragment;
    private BikeListAdapter adapter;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        init();
        loadDataByInternet();
    }

    BikeConventionDao dao= null;
    private void init(){
        dao = new BikeConventionDao(this);
        initView();
        setLayoutManager();
        setBikeConventionAdapter();
        mapProgress.setVisibility(View.VISIBLE);
    }

    private void initView(){
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        btnSearch = findViewById(R.id.btnSearch);
        editArea = findViewById(R.id.editArea);
        mapProgress = findViewById(R.id.mapProgress);
        recyclerView = findViewById(R.id.recyclerView);
        listProgress = findViewById(R.id.listProgress);
    }
    private void setBikeConventionAdapter(){
        adapter = new BikeListAdapter(this);
        adapter.setRecyclerView(recyclerView);
    }
    private void setLayoutManager(){
        PreCacheLayoutManager manager = new PreCacheLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setExtraLayoutSpace(LoadSizeUtil.getScreenHeight(this));
        manager.setRecyclerView(recyclerView);
    }

    List<Row> bikeConventionList = new ArrayList<>();
    int totalCount = 0;
    private void loadDataByInternet(){
        // 총 개수가 1808개이므로 이를 반복
        new AsyncTask<Integer, Void ,String>(){
            @Override
            protected String doInBackground(Integer... count) {
                int firstCount = count[0];
                totalCount = count[0] + 500;
                String url = BuildConfig.SERVER_URL+ firstCount + "/" + totalCount + "/";
                totalCount++;
                return RemoteUtil.getData(url);
            }

            @Override
            protected void onPostExecute(String jsonString) {
                Gson gson = new Gson();
                Json json = gson.fromJson(jsonString,Json.class);
                bikeConventionList.addAll(json.getGeoInfoBikeConvenientFacilitiesWGS().getRow());
                dao.save(json.getGeoInfoBikeConvenientFacilitiesWGS().getRow());
                if(totalCount<1808) loadDataByInternet();
                else mapFragment.getMapAsync(MapsActivity.this);
            }
        }.execute(totalCount);
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapProgress.setVisibility(View.GONE);

        LatLng sydney = new LatLng(-34, 151);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void showList(List<String> objIdList) {
        List<Row> data = dao.readByObjId(objIdList);
        adapter.setDateAndRefresh(data);
    }

    @Override
    public void showDetail(String objId) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dao.deleteAll();
    }
}
