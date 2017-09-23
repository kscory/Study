# Android Intro
알고 있어야 할 개념


## 기본 개념
들어가기 전 기본 개념

### 0. __안드로이드 Framework__
- 아래 그림에서 자바API Framework 까지 관리하며 아래는 C / C++로 보통 되어 있다.
- 따라서 bitmap과 같은 경우 강제로 refresh 시켜주지 않을 경우 계속 값을 가지게 된다.
![](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/Basic%20Concept/picture/android-stack_2x.png)

### 1. __안드로이드 화면 구조__
App(어플) > Activity(화면한개 단위) > Fragment(화면조각) > Layout(뷰그룹:컨테이너) > Widget(뷰)
> ※ Fragment의 경우 생명주기 관리가 복잡하여 요즘에는 View를 Fragment처럼 하는 회사가 많다.

---
### 2. __Context 란__
[참고자료_안드로이드 Context 이야기](http://blog.naver.com/PostView.nhn?blogId=huewu&logNo=110085457720)

- Context : 시스템 자원을 사용하기 위한 도구
  - 개발자가 필요요할 함수를 미리 정의해둔 클래스
  - 안드로이드의 메이저 컴포넌트를 위한 기반 클래스

- 대표적으로 Activity가 Context를 상속

- getContext vs  getapplicationContext
  - getContext : 컴포넌트 단위
  - getapplicationContext : 들어가있는 도구 등 차이
  - 되도록이면 그냥 컨텍스트 or getBaseContext사용 (물론 아닌 경우도 존재)

- 시스템과의 차이
  - Context는 new로 메모리에 로드(대표적으로 this) 되어야만 사용 가능 (클래스)
  - System.in 과 같이 사용 가능

- View?
  - View는 Activity랑 Service에 속해 있어 getContext 사용 가능
  - View는 개별적으로 context를 가지고 있을 수 없다

---
### 3. __Listener 란__
- Listener는 크게 하나가 존재하며, 서버로 생각할 수 있다.
- 우리는 Listener의 사용할 명령을 내리는 것.
- App 증가 시 Listener에 set 되는 것이 많아진다.


---
### 4. __MainActivity, onCreate, setContentView__
- 메인 Activity의 경우 Activity 기반클래스를 상속받아서 구성

  ```java
  public class MainActivity extends AppCompatActivity{
  }
  ```

- onCreate는 화면을 최초에 생성할 때 호출되는 함수

  ```java
  @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
  }
  ```

- setContentView : 레이아웃 xml 파일을 메모리에 로드.

  ```java
  setContentView(R.layout.activity_main);
  ```

---
### 5. __위젯 사용법__
  1. 레이아웃에 정의된 위젯의 아이디로 해당 객체를 변수에 선언</br>
  2. 선언된 변수에 실제 위젯을 할당
  3. 위에서 저장한 변수를 사용( 이벤트(클릭, 터치) 캐치가 필요할 경우는 리스너를 달아준다,)
  4. 실행할 코드를 작성

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
