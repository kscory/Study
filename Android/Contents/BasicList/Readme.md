# BasicList
- ListView와 Adapter 이용한 list의 구현

---

## 개요
- ListView, Adapter, Data Set, Android(System), Holder 관계 및
</br> ListView가 만들어지는 과정

#### 1. View가 null 이라면 View를 생성해준다.
- View가 만들어지지 않았다면 View는 null로 존재하므로 View를 생성해준다.
- 이때, View가 생성될 시 Holder도 함께 생성하고, 이를 이용하여 View를 재사용하기 위해 Holder에 View들을 담게된다.(Tag를 달아서, Android(System)은 Holder의 존재를 모르고 있다.)
- View를 생성한 후 dataset에서 data를 가져온다.

![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicList/picture/listview1.png)

#### 2. 화면에 보여지는 만큼만 View를 생성한다.
- View들은 Android(System)에 저장되어 있다는 것을 생각.

![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicList/picture/listview2.png)

#### 3. 스크롤을 내리게 되면 새로운 data를 보여주기 위해 getView를 실행한다.
- 이 때 View는 이미 생성되어 있는, 화면에서 사라진 View를 넘겨준다.

![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicList/picture/listview3.png)

#### 4. View를 받아서 Holder에 저장된 위젯의 데이터를 바꾼다.
- 만약 Holder를 사용하지 않는다면 위젯들을 새로 new 해주어야 하므로 효율성이 떨어진다.
![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicList/picture/listview4.png)

#### 5. listView 에 바뀐 View를 적용한다.

![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicList/picture/listview5.png)

---

## 코드에 적용
1. 데이터를 정의
2. __데이터와 리스트뷰를 연결하는 아답터를 생성__(이부분 설명)
3. 아답터와 리스트뷰를 연결

> MainActivity.java

```java
public class MainActivity extends AppCompatActivity {
    // 1. 데이터를 정의
    List<String> data = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // (1) (100개의 가상 값을 담는다.)
        for(int i=0 ; i<100 ; i++){
            data.add("item : "+i);
        }
        // 2. 데이터와 리스트뷰를 연결하는 아답터를 생성
        CustomAdapter adapter = new CustomAdapter(this, data);
        // 3. 아답터와 리스트뷰를 연결
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
```

### 1. __Layout__
- ListView Layout을 만들고
- 새로 list layout을 따로 만든다.

### 2. __아답터의 생성__

- 기본 아답터 클래스를 상속, 생성자

  ```java
  public class CustomAdapter extends BaseAdapter {
      // 데이터 저장소를 아답터 내부에 두는것이 컨트롤하기 편하다.
      List<String> data;
      Context context;
      // 생성자
      public  CustomAdapter(Context context, List<String> data){
          this.context = context;
          this.data = data;
      }
  }
  ```

- 상속받은 메소드 오버라이딩

  ```java
  @Override
  public int getCount() {
      return data.size();
  }
  // 현재 뿌려질 데이터를 리턴
  @Override
  public Object getItem(int position) { // <-호출되는 목록아이템의 위치가 position
      return data.get(position);
  }
  // 뷰의 아이디를 리턴
  @Override
  public long getItemId(int position) {
      return position;
  }
  // 목록에 나타나는 아이템 하나하나를 그려준다.
  // 화면에 1 픽셀이라도 나타나면 getView가 호출
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
      /* 뒤에 설명 */
      return convertView;
  }
  ```

- Holder 클래스
  - intent를 이용하여 textView의 text를 key를 달아 다음 레이아웃으로 넘겨준다.(`putExtra`)

  ```java
  class Holder{
      TextView textView;

      public void init(){
          textView.setOnClickListener(new View.OnClickListener() {
              // 화면에 보여지는 View는 기본적으로 자신이 속한 Component의 컨텍스트를 그대로 가지고 있다.
              @Override
              public void onClick(View view) {
                  Intent intent = new Intent(view.getContext(), DetailActivity.class);
                  intent.putExtra("valueKey", textView.getText());
                  view.getContext().startActivity(intent);
              }
          });
      }
  }
  ```

- getView 메소드
  - a. 레이아웃 인플레이터로 xml 파일을 View 객체로 변환
    - View itemView = LayoutInflater.from(컨텍스트).inflate(R.layout.만든 xml, null);
    - 커스텀뷰, 레이아웃을 만들때 사용 가능
  - b. View와 홀더를 생성하고 View에 Tag함
  - c. View가 존재할 경우 홀더를 불러옴

  ```java
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
      Holder holder = null;
      // 아이템 view를 재사용하기 위해서 null 체크
      if(convertView ==null){
          // 레이아웃 인플레이터로 xml 파일을 View 객체로 변환
          convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
          // 혹은 아래와 같이 사용 가능
          // LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);

          // 뷰안에 있는 텍스트뷰 위젯에 값을 입력
          // (가) 아이템이 최초 호출될 경우는 Holder에 위젯들을 담고,
          holder = new Holder();
          holder.textView =(TextView) convertView.findViewById(R.id.textView);
          holder.init();
          // (나) 홀더를 View에 붙여놓는다..
          // 원래 setTag는 네이밍(라벨링을 하기 위해서 사용했는데, 원래 목적과 다르게 사용되고 있다.
          convertView.setTag(holder);
      } else{
          // View에 붙어 있는 홀더를 가져온다.
          holder = (Holder) convertView.getTag();
      }
      // 데이터를 입력
      holder.textView.setText(data.get(position));

      return convertView;
  }
```

- 전체 코드
  ```java
  public class CustomAdapter extends BaseAdapter {
      List<String> data;
      Context context;

      public  CustomAdapter(Context context, List<String> data){
          this.context = context;
          this.data = data;
      }
      @Override
      public int getCount() {
          return data.size();
      }
      @Override
      public Object getItem(int position) {
          return data.get(position);
      }
      @Override
      public long getItemId(int position) {
          return position;
      }
      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
          Holder holder = null;
          if(convertView ==null){
              convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);

              holder = new Holder();
              holder.textView =(TextView) convertView.findViewById(R.id.textView);
              holder.init();
              convertView.setTag(holder);

          } else{
              holder = (Holder) convertView.getTag();
          }

          holder.textView.setText(data.get(position));

          return convertView;
      }

      class Holder{
          TextView textView;
          public void init(){
              textView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Intent intent = new Intent(view.getContext(), DetailActivity.class);
                      intent.putExtra("valueKey", textView.getText());
                      view.getContext().startActivity(intent);
                  }
              });
          }
      }
  }
  ```

### 3. __넘겨받은 인텐트를 받음__
\#1 번들을 통해 받기 / \#2 인텐트에서 바로 꺼내기

> DetailActivity.java

- \# 1. 번들을 통해 받기

  ```java
  public class DetailActivity extends AppCompatActivity {
      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_detail);
          // 인텐트를 통해 넘어온 값 꺼내기
          // startActivity를 통해 넘어온 intent를 꺼낸다.
          Intent intent = getIntent();

          // 인텐트에서 값의 묶음인 번들을 꺼내고
          Bundle bundle = intent.getExtras();
          // 번들에서 최종 값을 꺼낸다.
          String result = bundle.getString("valueKey");

          TextView textView = (TextView) findViewById(R.id.textView);
          textView.setText(result);
      }
  }
  ```
- \# 2. 인텐트에서 바로 값을 꺼내기

  ```java
  public class DetailActivity extends AppCompatActivity {
      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_detail);
          // 인텐트를 통해 넘어온 값 꺼내기
          // startActivity를 통해 넘어온 intent를 꺼낸다.
          Intent intent = getIntent();

          // # 2. 인텐트에서 바로 값을 꺼내기
          String result = intent.getStringExtra("valueKey");

          TextView textView = (TextView) findViewById(R.id.textView);
          textView.setText(result);


      }
  }
  ```

  ---

## 참고자료 (LayoutInflater)
- LayoutInflater.from(context).inflate(R.layout.item_list,null)
  - 최외곽의 레이아웃의 속성이 레이아웃에 영향을 미치지 못한다.(ex> matchparent인 경우 화면을 다 차치해야하는데 그렇지 않고 내용물만이 영향을 미친다.)

- LayoutInflater.from(context).inflate(R.layout.item_list,parent, false)
  - 최외곽의 레이아웃 속성이 영향을 미치는데 true, false에 따라 목록에 영향을 미치게 된다.
