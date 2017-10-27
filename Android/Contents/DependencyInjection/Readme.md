# Dependency Injection
  - DependencyInjection 이란
  - DependencyInjection 라이브러리
    - [ButterKnife](http://jakewharton.github.io/butterknife/)
    - [AndroidAnnotation](http://androidannotations.org/)

---

## Dependency Injection 이란 (+IOC , AOP)
  ### 1. Dependency Injection(DI)
  - 의존성 주입(Dependency Injection, DI)은 프로그래밍에서 구성요소간의 의존 관계가 소스코드 내부가 아닌 외부의 설정파일 등을 통해 정의되게 하는 디자인 패턴 중의 하나
    - 의존성 : new를 하는 행위 자체
    - 주입 : 내가 가진 변수를 집어 넣을 때
    - 즉 다른 곳에서 만들어져서 나에게 넣어주는것이 의존성 주입(DI)
  - 의존성 주입(DI)의 장점
    - 의존 관계 설정이 컴파일시가 아닌 실행시에 이루어져 모듈들간의 결합도 를 낮출 수 있다.
    - 코드 재사용을 높여서 작성된 모듈을 여러 곳에서 소스코드의 수정 없이 사용할 수 있다.
    - 모의 객체 등을 이용한 단위 테스트의 편의성을 높여준다.
  - [참고_의존성 주입](https://ko.wikipedia.org/wiki/%EC%9D%98%EC%A1%B4%EC%84%B1_%EC%A3%BC%EC%9E%85)

  ### 2. Inverstion Of Control (IOC)
  - 의존성이 생기는 것들을 한곳에서 관리하는 것
  - 프로그래머가 작성한 프로그램이 재사용 라이브러리의 흐름 제어를 받게 되는 소프트웨어 디자인 패턴
    > 전통적인 프로그래밍에서 흐름은 프로그래머가 작성한 프로그램이 외부 라이브러리의 코드를 호출해 이용하지만 제어 반전이 적용된 구조에서는 외부 라이브러리의 코드가 프로그래머가 작성한 코드를 호출
  - [참고_제어  반전](https://ko.wikipedia.org/wiki/%EC%A0%9C%EC%96%B4_%EB%B0%98%EC%A0%84)

  - DI, IOC 그림

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/DependencyInjection/picture/DI,IOP.png)

  ### 3. Aspect Oriented Programming(AOP)
  - 관점 지향 프로그래밍(aspect-oriented programming, AOP)은 횡단 관심사(cross-cutting concern)의 분리를 허용함으로써 모듈성을 증가시키는 것이 목적인 프로그래밍 패러다임
  - [참고_관점지향프로그래밍](https://ko.wikipedia.org/wiki/%EA%B4%80%EC%A0%90_%EC%A7%80%ED%96%A5_%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D)
  - 그림으로 이해

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/DependencyInjection/picture/AOP.png)

---

## ButterKnife
  ### 1. ButterKnife란? & 라이브러리 추가 방법
  - 필드나 메소드를 안드로이드 뷰에 어노테이션(@)을 이용하여 DI를 도입한 안드로이드 라이브러리
  - Gradle에 아래 코드 추가
  ```
  compile 'com.jakewharton:butterknife:8.8.1'
  annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'  
  ```

  ### 2. ButterKnife 사용방법
  - 크게 4가지 기능
    - ` @BindView` annotation을 이용해서 `findViewById`를 제거
    - resource annotation을 이용해서 resource 검색작업을 축소
    - 여러 개의 뷰와 배열을 그룹화
    - 리스너를 위한 익명의 내부 클래스들을 `@OnClick` annotation을 이용해서 제거
  - 주의사항
    - 버터나이프는 반드시 엑티비티에서 선언 (아닐경우 사용방법은 아래에.)

  >  @BindView 주석(annotation)을 이용해서 findViewById를 제거

  ```java
  class ExampleActivity extends Activity {
    // 버터나이프 사용
    // id에는 위젯의 id가 들어감(여기서는 title,subtitle)
    @BindView(R.id.title) TextView title;
    @BindView(R.id.subtitle) TextView subtitle;

    // 동일하게 사용부분
    @Override public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.simple_activity);
      // 이를 선언해주어야 한다.
      ButterKnife.bind(this);
      // TODO Use fields...
    }
  }
  ```

  > resource annotation을 이용해서 resource 검색작업을 축소

  ```java
  class ExampleActivity extends Activity {
    @BindString(R.string.title) String title;
    @BindDrawable(R.drawable.graphic) Drawable graphic;
    /* 동일 */
  }
  ```

  > 여러 개의 뷰와 배열을 그룹화

  ```java
  class ExampleActivity extends Activity {
    @BindView({R.id.btn_0,R.id.btn_1....})
    /* 동일 */
  }

  ```

  > f리스너를 위한 익명의 내부 클래스들을 @OnClick annotation을 이용해서 제거

  ```java
  // id에는 위젯의 id가 들어감(여기서는 submit)
  @OnClick(R.id.submit)
  public void submit(View view) {
    // TODO submit data....
  }
  ```

  > NON-ACTIVITY BINDING

  ```java
  // fragment
  public class FancyFragment extends Fragment {
    @BindView(R.id.button1) Button button1;
    @BindView(R.id.button2) Button button2;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fancy_fragment, container, false);
      // activity에서 선언 후 view를 넘겨준다.
      ButterKnife.bind(this, view);
      // TODO Use fields...
      return view;
    }  

  // viewholder  
  public class MyAdapter extends BaseAdapter {
    @Override
    public View getView(int position, View view, ViewGroup parent) {
      ViewHolder holder;
      if (view != null) {
        holder = (ViewHolder) view.getTag();
      } else {
        view = inflater.inflate(R.layout.whatever, parent, false);
        holder = new ViewHolder(view);
        view.setTag(holder);
      }

      holder.name.setText("John Doe");
      // etc...

      return view;
    }

    static class ViewHolder {
      @BindView(R.id.title) TextView name;
      @BindView(R.id.job_title) TextView jobTitle;

      public ViewHolder(View view) {
        ButterKnife.bind(this, view);
      }
    }
  }
  ```

---

## AndroidAnnotation
  ### 1. AndroidAnnotation 이란? & 라이브러리 추가 방법
  - open source framework 으로 여러가지 idiomatic 표현들을 annotation 으로 지원해주는 라이브러리 (View, Extras, System Service, Resource, Thread 등을 지원)
  - Gradle에 아래 코드 추가
  ```
  def AAVersion = "4.3.1"

  dependencies {
      annotationProcessor "org.androidannotations:androidannotations:$AAVersion"
      compile "org.androidannotations:androidannotations-api:$AAVersion"
  }   
  ```

  ### 2. AndroidAnnotation 사용방법
  - onCreate를 대체 / window title 날릴 수 있음 등
  - thread 사용 가능
  - 주의사항
    - AndroidAnnotation은 mainifest에서 "_"를 추가시켜 주여야 한다.

  ```java
  @EActivity(R.layout.activity_main) // oncreate 생성
  @WindowFeature(Window.FEATURE_NO_TITLE) // 타이틀을 날린다. =>그 외에도 스타일에서 했던 것을 정해줄 수 있다.
  @Fullscreen
  public class MainActivity extends AppCompatActivity {

      @ViewById
      TextView text;

      // Resource
      @AnimationRes
      Animation fadeIn;

      // Listener
      @Click({R.id.updateBookmarksButton1, R.id.updateBookmarksButton2})
      void updateBookmarksClicked() {
          searchAsync(search.getText().toString(), application.getUserId());
      }

      @AfterViews
      public void init(){
          text.setText("Hello AA");
      }

      // 쓰레드 기능이 막강
      @Background
      public void run(){
          // 여기는 sub thread에서 실행 (메소드가 run이 아니어도 됨)
          for(int i=0 ; i<10 ; i++){
              main(i);
          }
      }
      @UiThread
      public void main(int i){
          // 여기 코드는 main thread에서 실행
          text.setText(i+"");
      }
  }
  ```
