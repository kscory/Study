# Android Intro
구성


## 기본 개념
들어가기 전 기본 개념
### 1. __안드로이드 화면 구조__
App(어플) > Activity(화면한개 단위) > Fragment(화면조각) > Layout(뷰그룹:컨테이너) > Widget(뷰)
> ※ Fragment의 경우 생명주기 관리가 복잡하여 요즘에는 View를 Fragment처럼 하는 회사가 많다.

### 2. __MainActivity__
> 메인 Activity의 경우 Activity 기반클래스를 상속받아서 구성

```java
public class MainActivity extends AppCompatActivity{
}
```

### 3. __onCreate__
> onCreate는 화면을 최초에 생성할 때 호출되는 함수

```java
@Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
}
```

### 4. __setContentView__
> 레이아웃 xml 파일을 메모리에 로드.
```java
setContentView(R.layout.activity_main);
```

### 5. __위젯 사용__
> 1. 레이아웃에 정의된 위젯의 아이디로 해당 객체를 변수에 선언</br>
> 2. 선언된 변수에 실제 위젯을 할당
> 3. 위에서 저장한 변수를 사용( 이벤트(클릭, 터치) 캐치가 필요할 경우는 리스너를 달아준다,)
> 4. 실행할 코드를 작성

```java
public class MainActivity extends AppCompatActivity {
    // 1. 레이아웃에 정의된 위젯의 아이디로 해당 객체를 변수에 선언
    Button btn;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) { 되는 함수
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. 선언된 변수에 실제 위젯을 할당
        btn = (Button) findViewById(R.id.btn);
        txt = (TextView) findViewById(R.id.txt);

        // 3. 위에서 저장한 변수를 사용
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //4. 실행할 코드를 작성
                txt.setText("Hello Android!");
            }
        });
    }
}
```
