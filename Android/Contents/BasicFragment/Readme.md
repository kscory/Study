# Fragment
  - Fragment에 대해

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFragment/picture/fragments.png)

---

## Fragment의 생명주기
  ### Fragment의 생명주기 (그림)

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFragment/picture/fragment_lifecycle.png)

  ### 코드 예시
  - 로그로 생명주기 확인

  >  MainActivity

  ```java
  public class MainActivity extends AppCompatActivity implements ListFragment.Callback {

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          initFragment();
      }

      public void initFragment(){
          // 1. 프레그먼트 매니저
          FragmentManager manager = getSupportFragmentManager();
          // 2. 트랜잭션 처리자
          FragmentTransaction transaction = manager.beginTransaction(); // 프래크먼트를 처리하기 위한 트랜잭션을 시작
          // 3. 액티비티 레이아웃에 프레그먼트를 부착하고
          Log.d("Activity","======================================before add()");
          transaction.add(R.id.container, new ListFragment());
          Log.d("Activity","======================================afteradd()");
          // 4. 커밋해서 완료
          transaction.commit();
          Log.d("Activity","======================================commit()");
      }

      @Override
      protected void onStart() {
          super.onStart();
          Log.d("Activity","======================================onStart()");
      }

      @Override
      protected void onResume() {
          super.onResume();
          Log.d("Activity","======================================onResume()");
      }
  }
  ```

  >  Fragment

  ```java
  public class ListFragment extends Fragment {
      public ListFragment() {

      }

      @Override
      public void onAttach(Context context) {
          Log.d("Fragment","==========onAttach()");
          super.onAttach(context);
      }

      @Override
      public void onDetach() {
          Log.d("Fragment","==========onDetach()");
          super.onDetach();
      }

      // 액티비티에 부착되면서 동작시작
      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState) {
          Log.d("Fragment","==========onCreateView()");
          View view = inflater.inflate(R.layout.fragment_list, container, false);

          return view;
      }

      @Override
      public void onStart() {
          Log.d("Fragment","==========onStart()");
          super.onStart();
      }

      @Override
      public void onResume() {
          Log.d("Fragment","==========onResume()");
          super.onResume();
      }
  }  
  ```

  > 결과

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFragment/picture/fragments_ex.png)

---

## Fragment를 이용한 주소록 예시

  ### 1. Layout 구성
  - res/layout-land에 세로방향 layout xml 설정
    - layout-land에 저장하게 되면 세로방향으로 인식을 하게 된다.
  - 주소록 목록을 보여주는 ListFragment 생성
  - 상세 내역을 보여주는 DetailFragment를 구성
  - Layout 구성 그림

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFragment/picture/fragments_Layout.png)

  ### 2.

---

## 참고 링크
#### 1.[프래그먼트](https://developer.android.com/guide/components/fragments.html)
#### 2.[프레그먼트 블로그](http://blog.saltfactory.net/implement-layout-using-with-fragment/)
