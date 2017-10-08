# TabLayout(View)
  - CustomView를 이용하여 TabLayout 구성
  - 작동되는 모션은 [TabLayout(Fragment)](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/TapLayout)과 동일
  -

---

## TabLayout(View) 사용 방법
  ### 1. Layout (Fragment와 동일)
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

  ### 2. 사용할 만큼의 CustomView 생성
  - 현재는 4개의 CustomView(여기서는 FrameLayout을 상속)를 생성
  - CustomView에서 직접 로직 작성

  > One.java

  ```java
  public class One extends FrameLayout{

      public One(Context context){
          super(context);
          initView();
      }

      public One(Context context, @Nullable AttributeSet attrs){
          super(context, attrs);
          initView();
      }

      // 여기서 내가만든 레이아웃을 infalte하고 나 자신에게 add
      private void initView(){
          // 1.  Layout파일로 뷰를 만들고
          View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_one,null);
          // 로직작성
          prcess();
          // 나 자신에게 add
          addView(view);
      }
      // 로직 작성
      private void prcess(){ /*로직*/ }
  }  
  ```


  ### 3. TabLayout Adapter
  - PagerAdapter를 상속받아 이용
  - `getCount` : 전체 View의 개수를 반환
  - `instantiateItem` : 보이는 화면과 앞뒤 화면을 불러온다.
  - `isViewFromObject` : `instantiateItem`에서 리턴된 object가 View인지를 체크한다.
  - `destroyItem` : 쓰지 않는 View를 제거

  > CustomAdapter.java

  ```java
  public class CustomAdapter extends PagerAdapter {
      private static final int COUNT =4;
      List<View> views;

      // view를 add 해준다.
      public CustomAdapter(Context context){
          views = new ArrayList<>();
          views.add(new One(context));
          views.add(new Two(context));
          views.add(new Three(context));
          views.add(new Four(context));
      }
      // 전체 갯수
      @Override
      public int getCount() {
          return COUNT;
      }

      // getView 와 같이 안드로이드가 때에 따라서 계속 실행시킴
      @Override
      public Object instantiateItem(ViewGroup container, int position) {
          // 1. 레이아웃 파일을 inflate해서 view로 만든다.
          // 여기서는 null을 반드시 사용
          View view = views.get(position);
          // 2. 뷰에 데이터를 세팅(로직을 작성)
          // 3. 뷰 그룹에 만들어진 view를 add 한다.
          container.addView(view);
          return view;
      }

      // instantiateItem 에서 리턴된 object가 View가 맞는지 확인
      @Override
      public boolean isViewFromObject(View view, Object object) {
          return view == object;
      }

      // 현재 사용하지 않는 View는 제거
      // container : 뷰페이져
      // object    : 뷰아이템 (뷰페이져 안에 있는)
      @Override
      public void destroyItem(ViewGroup container, int position, Object object) {
          container.removeView( (View)object ); // object를 View로 캐스팅 필요
          // 이는 자동으로 해주지만 bitmap 같은 경우는 직접 제거 해주어야 한다.
          // 즉, 특정 스트림을 열었을 경우 제거 필요( 물론 View는 자동으로 제거 가능)
          //super.destroyItem(container, position, object);
      }
  }
  ```


  ### 4. MainActivity에서 Fragment 및 ViewPager 연결
  - 탭을 원하는 만큼 추가
  - ViewPager 를 연결(아답터에)
  - 리스너 연결

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

      private void setViewPager(){
          viewPager = (ViewPager) findViewById(R.id.viewPager);
          CustomAdapter adapter = new CustomAdapter(this);
          viewPager.setAdapter(adapter);
      }

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

  ### 1. 요즘에는 아래와 같이 adapter에 view를 세팅하는 메소드를 생성하여 사용한다.
  - 아래의 코드를 추가

  > CustomAdapter.java

  ```java
  // 항상 고정적으로 들어간다( 마치 controller처럼 됨)
  public void setView(ViewPager viewPager){
      viewPager.setAdapter(this);
  }
  ```

  > MainActivity.java

  ```java
  CustomAdapter adapter = new CustomAdapter(this);
  // 아답터 연결
  adapter.setView(viewPager);
  ```

  ### 2. Fragment vs View
  - Fragment 사용해서 ViewPager(FragmentStatePager) 사용시 Activity LifeCycle과 Fragment LifeCycle 관리가 어려움
  - 따라서 실제 View를 컨트롤하는 ViewPager 사용 (최근에는 이렇게 사용)
  - 하지만 이는 View가 메모리에 모두 올라와야 하는 문제 존재
  - 최근에는 메모리관리를 위한 프레임워크가 있기도 하다.
