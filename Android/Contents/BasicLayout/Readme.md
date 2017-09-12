# BasicLayout
화면에 사용되는 Layout들과 화면이동

## BasicLayout 종류
Layout종류와 간단한 특징

### 1. __Constraint Layout__
뷰 계층의 깊이와 복잡성을 해결하기 위해 ContraintLayout이 만들어졌으며, 앱의 UI렌더링 속도를 높일 수 있을 뿐만아니라 다양한 기기의 해상도에 최적화된 UI를 쉽게 개발 가능
</br> - 단순한 계층 구조로 만들어낼 수 있다는 장점 존재

```java
//XML 파일
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.kyung.basiccalculator.MainActivity">
</android.support.constraint.ConstraintLayout>
```

### 2. __Linear Layout__
요소들을 수평 혹은 수직으로 일렬로 배열
</br> - Linearlayout은 보통 두가지 이상의 레이아웃이 사용됨
> 추가적으로 orientation 속성이 추가

```java
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    // 추가내용
    android:orientation="horizontal"
    // 추가내용
    tools:context="com.example.kyung.basiclayout.LinearActivity">
</LinearLayout>
```

### 3. __Frame Layout__
요소들을 중첩해서 놓기 위해 주로 사용 (게임이 대표적)

### 4. __Grid Layout__
columnCount를 이용해 그리드를 나눔
</br> - 잘 사용하지 않음 : 빈 여백을 처리할 방법이 부족</br>
</br> - 병합처리할때만 가끔 사용

### 5. __Relative Layout__
Contraint와 거의 비슷하지만 희소행렬을 사용하지 않음

## 화면이동 방법

### 1. __화면이동 방법__
이전과 동일하게 진행 여기서 Intent 사용
> "Intent intent = new Intent(컨텍스트("시스템자원"),타겟);"

```java
//java 파일
public class MainActivity extends AppCompatActivity {

    //1. 레이아웃에 정의된 위젯의 아이디로 해당 객체를 변수에 선언
    Button btnFrame, btnLinear, btnGrid, btnRelative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. 선언된 변수에 실제 위젯을 할당
        btnFrame = (Button) findViewById(R.id.btnFrame);
        btnLinear = (Button) findViewById(R.id.btnLinear);
        btnGrid = (Button) findViewById(R.id.btnGrid);
        btnRelative = (Button) findViewById(R.id.btnRelative);
        btnCalculator = (Button) findViewById(R.id.btnCalculator);

        // 4. 아래에 선언한 실행객체를 리스너에 던져준다.
        btnFrame.setOnClickListener(onClickListener);
        btnLinear.setOnClickListener(onClickListener);
        btnGrid.setOnClickListener(onClickListener);
        btnRelative.setOnClickListener(onClickListener);
        btnCalculator.setOnClickListener(onClickListener);
    }

    // 3. 중복되는 리스너를 전역변수로 선언할 수 있다.
    // View에서 넘겨주므로 id를 넘겨받을 수 있다.
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){

            // 액티비티(메이저 컴포넌트) 실행
            // 3.1. 인텐트 (시스템에 전달되는 메시지객체) -> Intent intent = new Intent(컨텍스트("시스템자원"),타겟);
            Intent intent = null;
            switch (v.getId()){
                case R.id.btnFrame:
                    intent = new Intent(MainActivity.this, FrameActivity.class);
                    //this라 하면 알아서 컨텍스트를 불러옴
                    break;
                case R.id.btnLinear:
                    intent = new Intent(MainActivity.this, LinearActivity.class);
                    break;
                case R.id.btnGrid:
                    intent = new Intent(MainActivity.this, GridActivity.class);
                    break;
                case R.id.btnRelative:
                    intent = new Intent(MainActivity.this, RelativeActivity.class);
                    break;
            }
            // 3.2. 엑티비티 실행요청
            startActivity(intent);
```
