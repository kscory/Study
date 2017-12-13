# [MPAndroid Chart](https://github.com/PhilJay/MPAndroidChart)
  - MPAndroid Chart 라이브러리 기본적인 사용법

---

## 사용법
  ### 1. 시작하기
  - project Gradle 과 app gralde에 추가

  > gradle

  ```java
  // project gradle
  allprojects {
  	repositories {
  		maven { url "https://jitpack.io" }
  	}
  }

  // app gradle
  dependencies {
  	implementation 'com.github.PhilJay:MPAndroidChart:v3.0.3'
  }
  ```

  ### 2. xml에 chartView 추가
  - 라이브러리에서 제공하는 여러 차트 존재

  ```xml
  <com.github.mikephil.charting.charts.LineChart
      android:id="@+id/lineChart"
      android:layout_width="match_parent"
      android:layout_height="match_parent" />
  ```

  ### 3. 차트에 들어갈 데이터 정의
  - 데이터 정의
  - 데이터 셋 및 값의 속성, 범례 정의

  ```java
  // 데이터 정의
  List<Entry> entries1 = new ArrayList<>();
  List<Entry> entries2 = new ArrayList<>();
  for(int i=1 ; i<11 ; i++){
      entries2.add(new Entry(i,i*3));
      if(i%2==0) entries1.add(new Entry(i, i * 4));
      else entries1.add(new Entry(i, i));
  }
  // 데이터 셋을 정의
  LineDataSet dataSet = new LineDataSet(entries1,"LINE TEST 1"); // 데어트, 범례
  dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
  List<Integer> colors = new ArrayList<>(); // 색상지정
  /* 칼라 데이터 로직 */
  dataSet.setColors(colors);
  dataSet.setValueTextColor(Color.BLUE);

  LineDataSet dataSet2 = new LineDataSet(entries2,"LINE TEST 2");
  dataSet2.setColor(Color.RED);
  dataSet2.setValueTextColor(Color.GRAY);
  ```

  ### 4. X축 및 Y축을 styling
  - value 포멧터 정의
  - 전체적인 styling 및 포멧 설정
  - [더 많은 styling](https://github.com/PhilJay/MPAndroidChart/wiki/The-Axis)

  ```java
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
  ```

  ### 5. chart를 styling 및 animation
  - [chat의 styling](https://github.com/PhilJay/MPAndroidChart/wiki/General-Chart-Settings-&-Styling)
  - [chat의 animation](https://github.com/PhilJay/MPAndroidChart/wiki/Animations)

  ```java
  lineChart.setBackgroundColor(Color.WHITE); // 배경색 지정
  Description description = new Description();
  description.setText("");
  lineChart.setDescription(description);// 설명 정의(오른쪽 아래)
  lineChart.setBorderWidth(100);
//        lineChart.setMaxVisibleValueCount(8);
  lineChart.invalidate();

  lineChart.animateX(3000, Easing.EasingOption.Linear); // 속도, 애니메이션
  ```

  ### 6. 그래프에 들어갈여러 데이터 셋을 lineData로 확정한 후 chat에 등록 및 그리기

  ```java
  List<ILineDataSet> dataSets = new ArrayList<>();
  dataSets.add(dataSet); dataSets.add(dataSet2);
  LineData lineData = new LineData(dataSets);
  lineChart.setData(lineData);
  lineChart.invalidate(); // refresh
  ```

  ### 7. 데이터가 동적으로 변경되는 경우

  ```java
  /* 데이터 변경 로직 작성 */
  dataSet.notifyDataSetChanged(); // 데이터가 바뀌었다고 notify
  lineChart.notifyDataSetChanged(); // 차트에 데이터가 바뀌었다고 notify
  lineChart.invalidate(); // 다시 그리라고 명령
  ```

  ### 8. 그외 추가적인 그래프 인터페이스 설정
  - touch, drage, scale, Zoom

  ```java
  // touch
  lineChart.setTouchEnabled(false);
  // drag
  lineChart.setDragEnabled(false);
  // scale
  lineChart.setScaleEnabled(false);
  lineChart.setScaleXEnabled(false);
  lineChart.setScaleYEnabled(false);
  // pinchZoom
  lineChart.setPinchZoom(false);
  // double tap
  lineChart.setDoubleTapToZoomEnabled(false);
  ```
