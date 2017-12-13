package com.example.kyung.mpandroidchartprac;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebIconDatabase;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LineChart lineChart;

    List<Entry> entries1 = new ArrayList<>();
    List<Entry> entries2 = new ArrayList<>();
    List<Integer> colors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        showChartTest();
    }
    private void initView(){
        lineChart = findViewById(R.id.lineChart);
    }

    private void showChartTest(){
        entries1.clear();
        entries2.clear();

        // 0. 차트를 xml에 정의
        // 1. 차트에 들어갈 데이터 정의
        for(int i=1 ; i<11 ; i++){
            entries2.add(new Entry(i,i*3));
            if(i%2==0) {
                entries1.add(new Entry(i, i * 4));
                colors.add(Color.DKGRAY);
            }
            else {
                entries1.add(new Entry(i, i));
                colors.add(Color.MAGENTA);
            }
        }

        // 2. LineDataSet을 정의 (data, 범례이름) + 정의한 값의 속성 정의
        LineDataSet dataSet = new LineDataSet(entries1,"LINE TEST 1");
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.BLUE);

        LineDataSet dataSet2 = new LineDataSet(entries2,"LINE TEST 2");
        dataSet2.setColor(Color.RED);
        dataSet2.setValueTextColor(Color.GRAY);

        // 3. value를 styling - ex> x축
        // 포멧터 정의
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            // x축에 있는 value들을 가져오는 콜백함수
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String x = "Q"+ (int)value;
                return x;
            }
        };
        XAxis xAxis = lineChart.getXAxis(); // x축 스타일링시작
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // x축 위치 지정
        xAxis.setTextSize(10f); // 크기 지정
        xAxis.setTextColor(Color.RED); // 색 지정
        xAxis.setDrawLabels(true); // 라벨(x축 좌표)를 그릴지 결정
        xAxis.setDrawAxisLine(false); // x축 라인을 그림 (라벨이 없을때 잘 됨)
        xAxis.setDrawGridLines(false); // 내부 선 그을지 결정
        xAxis.setLabelCount(2); // 라벨의 개수를 결정 => 나누어 떨어지는 개수로 지정
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);

        // 4. 그래프에 들어갈 여러 데이터 셋을 lineData 로 확정한후 chart 에 등록
        //      - chart를 셋하고 refresh 한다.
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet); dataSets.add(dataSet2);
        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);
        setAnimation();
        lineChart.invalidate(); // refresh

        // 데이터가 변경되는 경우
//        dataSet.notifyDataSetChanged(); // 데이터가 바뀌었다고 notify
//        lineChart.notifyDataSetChanged(); // 차트에 데이터가 바뀌었다고 notify
//        lineChart.invalidate(); // 다시 그리라고 명령

        setCharStyling();
    }

    private void setCharStyling(){
        lineChart.setBackgroundColor(Color.WHITE); // 배경색 지정
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);// 설명 정의(오른쪽 아래)
        lineChart.setBorderWidth(100);
//        lineChart.setMaxVisibleValueCount(8);
        lineChart.invalidate();
    }

    private void setAnimation(){
        lineChart.animateX(3000, Easing.EasingOption.Linear);
    }

    private void setInteraction(){
        // touch
        lineChart.setTouchEnabled(false);
        // drag
        lineChart.setDragEnabled(false);
        // scale
        lineChart.setScaleEnabled(false); lineChart.setScaleXEnabled(false); lineChart.setScaleYEnabled(false);
        // pinchZoom
        lineChart.setPinchZoom(false);
        // double tap
        lineChart.setDoubleTapToZoomEnabled(false);
    }
}
