# TabLayout(Fragment)
  - Fragment를 이용하여 TabLayout 구성
  - Fragment의 경우 보여지는 양 옆 페이지까지 호출한다.
  - 아래와 같은 TabLayout

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/TapLayout/picture/tabLayout.gif)

---

## TabLayout 사용 방법
  ### 1. Layout
  - ViewPager의 경우 위젯에 없기 때문에 text 에서 직접 작성해야 한다.
  - TabLayout은 item을 직접 설정할 수 있지만 코드로 보통 작성한다.

  > activity_main.xml

  ```xml
  <android.support.design.widget.TabLayout
      android:layout_width="0dp"
      android:layout_height="wrap_content"
      app:layout_constraintLeft_toLeftOf="parent"
      app:layout_constraintRight_toRightOf="parent"
      tools:layout_editor_absoluteY="8dp"
      android:id="@+id/tabLayout">

  </android.support.design.widget.TabLayout>

  <android.support.v4.view.ViewPager
      android:id="@+id/viewPager"
      android:layout_width="0dp"
      android:layout_height="0dp"
      app:layout_constraintBottom_toBottomOf="parent"
      app:layout_constraintLeft_toLeftOf="parent"
      app:layout_constraintRight_toRightOf="parent"
      app:layout_constraintHorizontal_bias="0.0"
      app:layout_constraintTop_toBottomOf="@+id/tabLayout"
      app:layout_constraintVertical_bias="0.0">
  </android.support.v4.view.ViewPager>  
  ```

  ### 2. 사용할 만큼의 Fragmnet 생성
  - 현재는 4개의 Fragment를 생성
  - Fragment에서 코드 작성

  ### 3. TabLayout Adapter
  - `Fragment가 로드될때만` 사용하는 방법과 `모든 Fragment를 데이터로 받아서` 사용하는 방법 두가지 존재
  - 보통은 Fragment가 로드될 때 앞뒤로 세가지만 로드하는 방식 사용
  - 멀리까지 한번에 가야하는 경우는 후자 사용
  - 한번 움직일때 안드로이드가 `getitem` 메소드를 세번 호출

  > CustomAdapter.java (Fragment 로드 될때만 사용)

  ```java
  public class CustomAdapter extends FragmentStatePagerAdapter {

      private static final int COUNT =4;

      public CustomAdapter(FragmentManager fm) {
          super(fm);
      }

      @Override
      public int getCount() {
          return COUNT;
      }

      @Override
      public Fragment getItem(int position) {
          switch (position){
              case 1:
                  return new TwoFragment();
              case 2:
                  return new ThreeFragment();
              case 3:
                  return new FourFragment();
              default:
                  return new OneFragment();
          }
      }

      // (주의) 아래와 같이 Fragment를 전역변수로 사용시 메모리에 남게되는 문제 발생
      //    Fragment fragment;
      //    @Override
      //    public Fragment getItem(int position) {
      //        Fragment fragment = null;
      //        switch (position){
      //            case 1:
      //                fragment = new TwoFragment();
      //            case 2:
      //                fragment = new ThreeFragment();
      //            case 3:
      //                fragment = new FourFragment();
      //            default:
      //                fragment = new OneFragment();
      //        }
      //        return fragment;
      //    }
  }
  ```

  > CustomAdapter.java (Fragment를 데이터로 받아서 이용)

  ```java
  List<Fragment> data;

  public CustomAdapter(FragmentManager fm, List<Fragment> data) {
      super(fm);
      this.data = data;
  }

  @Override
  public Fragment getItem(int position) {
      return data.get(position);
  }

  @Override
  public int getCount() {
      return data.size();
  }
  ```


  ### 4. MainActivity에서 Fragment 및 ViewPager 연결
  - 탭을 원하는 만큼 추가
  - ViewPager 를 연결(아답터에)
  - 리스너 연결
  - 위와 같이 두가지 방법이 존재

  > MainActivity.java

  ```java
  public class MainActivity extends AppCompatActivity {

      ViewPager viewPager;
      TabLayout tabLayout;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          setTabLayout();
          setViewPager();
          setListener();

      }

      private void setListener(){
          // 탭 레이아웃을 뷰 페이저에 연결
          tabLayout.addOnTabSelectedListener( new TabLayout.ViewPagerOnTabSelectedListener(viewPager) );

          // ViewPager의 변경사항을 탭 레이아웃에 전달
          viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
      }

      // 뷰페이지 연결
      private void setViewPager(){
          viewPager = (ViewPager) findViewById(R.id.viewPager);
          CustomAdapter adapter = new CustomAdapter(getSupportFragmentManager());
          viewPager.setAdapter(adapter);

          // data를 넘겨서 사용하는 방법
  //        List<Fragment> data = new ArrayList<>();
  //        data.add(new OneFragment());
  //        data.add(new TwoFragment());
  //        data.add(new ThreeFragment());
  //        data.add(new FourFragment());
  //        CustomAdapter adapter = new CustomAdapter(getSupportFragmentManager(),data);
  //        viewPager.setAdapter(adapter);
      }

      // 탭을 순서대로 추가
      private  void setTabLayout(){
          tabLayout = (TabLayout) findViewById(R.id.tabLayout);
          tabLayout.addTab( tabLayout.newTab().setText("One") );
          tabLayout.addTab( tabLayout.newTab().setText("Two") );
          tabLayout.addTab( tabLayout.newTab().setText("Three") );
          tabLayout.addTab( tabLayout.newTab().setText("Four") );
      }
  }
  ```

---

## 참고 개념

  ### 1. preview를 한글로 바꾸기
  - 아래와 같이 res/value-ko/strings 에 내용을 한글로 저장

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/TapLayout/picture/language.png)

  ### 2. 한글깨짐 현상 해결
  - 경로를 디폴트로 하였다면 `C:\Program Files\Android\Android Studio\plugins\android\lib\layoutlib\data\fonts`로 이동
  - `fonts.xml`파일을 열고 한글 폰트 정의부분을 찾아 `NanumGothic.ttf`로 수정

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/TapLayout/picture/font.png)

  ### 3. 프로젝트를 복사해서 재사용하는 방법
  - [참고] (http://blog.daum.net/web_design/323)
