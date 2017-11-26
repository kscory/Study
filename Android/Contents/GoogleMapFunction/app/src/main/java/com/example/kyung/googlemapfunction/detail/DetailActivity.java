package com.example.kyung.googlemapfunction.detail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kyung.googlemapfunction.Const;
import com.example.kyung.googlemapfunction.R;
import com.example.kyung.googlemapfunction.domain.bikeconvention.Row;
import com.example.kyung.googlemapfunction.util.ImageLoadUtil;

public class DetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textType;
    private TextView textAddress;
    private ImageView imageToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Row bikeConvention = (Row) getIntent().getSerializableExtra(Const.KEY_BIKECONVENTION);

        initView();
        setValues(bikeConvention);

    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        textType = findViewById(R.id.textType);
        textAddress = findViewById(R.id.textAddress);
        imageToolbar = findViewById(R.id.imageToolbar);
    }

    private void setValues(Row bikeConvention) {
        ImageLoadUtil.loadImageByPicasso(this, bikeConvention.getFILENAME(), imageToolbar);
        textType.setText(bikeConvention.getCLASS());
        textAddress.setText(bikeConvention.getADDRESS());
    }
}
