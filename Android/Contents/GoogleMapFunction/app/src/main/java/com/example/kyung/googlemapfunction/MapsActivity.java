package com.example.kyung.googlemapfunction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.kyung.googlemapfunction.cluster.CustomClusterManager;
import com.example.kyung.googlemapfunction.contract.Contract;
import com.example.kyung.googlemapfunction.detail.DetailActivity;
import com.example.kyung.googlemapfunction.domain.BikeConventionDao;
import com.example.kyung.googlemapfunction.domain.bikeconvention.Json;
import com.example.kyung.googlemapfunction.domain.bikeconvention.Row;
import com.example.kyung.googlemapfunction.util.DeviceUtil;
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
    private SwipeRefreshLayout swipeRefresh;
    private SupportMapFragment mapFragment;
    private BikeListAdapter adapter;

    private GoogleMap mMap;
    List<Row> bikeConventionList = new ArrayList<>();
    CustomClusterManager clusterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        init();

        if(bikeConventionList.size()>0){
            mapFragment.getMapAsync(MapsActivity.this);
        } else {
            loadDataByInternet();
        }
    }

    BikeConventionDao dao= null;
    private void init(){
        dao = new BikeConventionDao(this);
        bikeConventionList = dao.readAll();

        initView();
        setSwipeLayout();
        setLayoutManager();
        setBikeConventionAdapter();
        setSearchButton();

        mapProgress.setVisibility(View.VISIBLE);
    }

    private void initView(){
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        btnSearch = findViewById(R.id.btnSearch);
        editArea = findViewById(R.id.editArea);
        mapProgress = findViewById(R.id.mapProgress);
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefresh = findViewById(R.id.swipeRefresh);
    }
    private void setBikeConventionAdapter(){
        adapter = new BikeListAdapter(this);
        adapter.setRecyclerView(recyclerView);
    }
    private void setLayoutManager(){
        PreCacheLayoutManager manager = new PreCacheLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setExtraLayoutSpace(DeviceUtil.getScreenHeight(this));
        manager.setRecyclerView(recyclerView);
    }
    private void setSwipeLayout(){
        // 색깔 결정
        swipeRefresh.setColorSchemeColors(Color.GREEN);
        // 리소스면 setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent) 를 통해 색깔 결정

        // swipe 효과 결정
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 새로고침 시 검색된 모든 데이터 불러와서 뿌려주기
                adapter.setDateAndRefresh(bikeConventionList);
                // animation을 멈추려면 false 로 설
                swipeRefresh.setRefreshing(false);
            }
        });
    }
    private void setSearchButton(){
        btnSearch.setOnClickListener(v ->{
            if(!"".equals(editArea.getText().toString()) && editArea.getText().toString() != null) {
                bikeConventionList.clear();
                bikeConventionList.addAll(dao.searchInAddress(editArea.getText().toString()));
                Log.e("adfadfadf","================"+bikeConventionList.size());
                clusterManager.setCluster(bikeConventionList);
                adapter.setDateAndRefresh(bikeConventionList);
            } else {
                bikeConventionList.addAll(dao.readAll());
                clusterManager.setCluster(bikeConventionList);
                adapter.setDateAndRefresh(bikeConventionList);
            }
        });
    }

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
                Log.e("Save!!!!","size = "+dao.getTotalCount());
                if(totalCount<1808) loadDataByInternet();
                else mapFragment.getMapAsync(MapsActivity.this);
            }
        }.execute(totalCount);
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        clusterManager = new CustomClusterManager(this, mMap);
        clusterManager.setCluster(bikeConventionList);
        mapProgress.setVisibility(View.GONE);
    }

    @Override
    public void showList(List<String> objIdList) {
        List<Row> data = dao.readByObjList(objIdList);
        adapter.setDateAndRefresh(data);
    }

    @Override
    public void showDetail(String objId) {
        Row row = dao.readByObjId(objId);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Const.KEY_BIKECONVENTION,row);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
