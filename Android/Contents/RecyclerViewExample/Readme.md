# Recycler View
- 사용법 (순서)
  - 화면 만들기
  - 데이터를 정의
  - 아답터를 재정의
  - 재정의한 아답터를 생성하면서 데이터를 담기.
  - 아답터와 RecyclerView 컨테이너를 연결
  - RecyclerView에 레이아웃매니저를 설정
- 참고 : [ListView](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/BasicList)

---
## 0. 화면 만들기
- Recyvler View를 이용하기 위한 Layout

  ### 1. RecyclerView를 library에 추가
  - `tools:listitem="@layout/item_list"`를 이용해 listitem 표시

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/RecyclerViewExample/picture/recycle1.png)

  ### 2. CardView를 library에 추가
  - CardView는 List가 출력되는데 리스트가 오른쪽에서 순서대로 밀려 들어오는 것과 같은데서 사용하기 좋다.
  - CardView 안에 text를 넣어 list를 꾸며줄 수 있다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/RecyclerViewExample/picture/recycle1.png)

---

## Recycler View Code
- RecyclerView 위젯은 ListView의 더욱 향상되고 유연해진 버전으로 한정된 수의 뷰를 유지함으로써 효율적으로 스크롤할 수 있는 큰 데이터 집합을 표시하기 위한 컨테이너
- 사용 순서


  ### 1. 데이터를 정의
  - 들어갈 데이터를 정의한다. (ex> Memo, PicNote 등 정의한 데이터도 가능)

  > 예시
  ```java
  List<String> data = new ArrayList<>();
  for(int i=0 ; i<100 ; i++){
      data.add("item : "+i);
  }
  ```

  ### 2. 아답터를 재정의
  - RecyclerView도 Adapter를 통해 데이터에 접근하게 된다.
  - `홀더 정의` -> `데이터 저장소 정의` -> `목록의 길이 설정` -> `홀더 생성` -> `홀더사용 순서로 진행`
  - 생성자를 지정할수도 있지만 setData를 통해 데이터를 가져올 수도 있다.

  ```java
  public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {
      // 1. 데이터 저장소
      List<String> data;
      Context context;
      // 생성자
      public CustomAdapter(Context context, List<String> data){
          this.data = data;
          this.context = context;
      }
      // 2. 목록의 전체 길이 설정
      @Override
      public int getItemCount() {
          return data.size();
      }
      // 3. 목록에서 아이템이 최초 요청되면 View Holder를 생성(홀더 생성)
      @Override
      public CustomAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
          View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
          Holder holder = new Holder(view);
          return holder;
      }
      // 4. 생성된 View Holder를 RecyclerView 에 넘김 (홀더 사용)
      @Override
      public void onBindViewHolder(CustomAdapter.Holder holder, int position) {
          holder.setText(data.get(position));
      }
      // 0. 홀더 만들기
      public class Holder extends RecyclerView.ViewHolder{

          private TextView textExample;

          public Holder(final View itemView) {
              super(itemView);
              textExample = (TextView) itemView.findViewById(R.id.textExample);

              itemView.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Intent intent = new Intent(v.getContext(), DetailActivity.class);
                      intent.putExtra("keys", textExample.getText());
                      v.getContext().startActivity(intent);
                  }
              });
          }
          public void setText(String text){
              textExample.setText(text);
          }
      }
  }
  ```

  ### 3. 재정의한 아답터를 생성하면서 데이터를 담기.
  - setData 사용이 될수도 있다.

  ```java
  // # 1. 생성자 사용
  CustomAdapter adapter = new CustomAdapter(this,data);
  // # 2. setData
  CustomAdapter adapter = new CustomAdapter();
  adapter.setData(data);
  ```

  ### 4. Divider 설정 (부가사항)
  - 아래의 코드를 추가시켜 단순한 divier를 구현할 수 있다.
  - Divier의 경우 adapter를 세팅하기 전에 먼저 선언해주어야 한다.
  - Divider는 설정하지 않아도 된다.

  ```java
  RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
  recyclerView.addItemDecoration(itemDecoration);
  ```

  ### 5. 아답터와 RecyclerView 컨테이너를 연결
  - adapter와 연결

  ```java
  recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
  recyclerView.setAdapter(adapter);
  ```

  ### 6. RecyclerView에 레이아웃매니저를 설정
  - 레이아웃 매니저 종류는 3가지 존재
    - `LinearLayoutManager` : 리사이클러 뷰에서 가장 많이 쓰이는 레이아웃으로 수평, 수직 스크롤을 제공하는 리스트를 만들 수 있다.
    - `StaggeredGridLayoutManager` : 레이아웃을 통해 뷰마다 크기가 다른 레이아웃을 만들 수 있다. 마치 Pinterest 같은 레이아웃 구성가능.
    - `GridLayoutManager` : 갤러리(GridView) 같은 격자형 리스트를 만들 수 있다.

  ```java
  // # 1. LinearLayoutManager
  recyclerView.setLayoutManager(new LinearLayoutManager(this));

  // # 2. StaggeredGridLayoutManager
  RecyclerView.LayoutManager sgm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
  recyclerView.setLayoutManager(sgm);

  // # 3. GridLayoutManager
  RecyclerView.LayoutManager gm= new GridLayoutManager(this, 3);
  recyclerView.setLayoutManager(gm);
  ```

---
## 추가사항
  ### 1. 데이터의 변경을 알려주는 메소드
  - `notifyDataSetChanged` : 데이터가 전체 바뀌었을 때 호출. 즉, 처음 부터 끝까지 전부 바뀌었을 경우
  - `notifyItemChanged` : 특정 Position의 위치만 바뀌었을 경우. ex>position 4 번 위치만 데이터가 바뀌었을 경우 사용 하면 된다.
  - `notifyItemRangeChanged` : 특정 영역을 데이터가 바뀌었을 경우. ex>position 3~10번까지의 데이터만 바뀌었을 경우 사용 하면 된다.
  - `notifyItemInserted` : 특정 Position에 데이터 하나를 추가 하였을 경우. ex>position 3번과 4번 사이에 넣고자 할경우 4를 넣으면 된다.
  - `notifyItemRangeInserted` : 특정 영역에 데이터를 추가할 경우. ex>position 3~10번 자리에 7개의 새로운 데이터를 넣을 경우
  - `notifyItemRemoved` : 특정 Position에 데이터를 하나 제거할 경우.
  - `notifyItemRangeRemoved` : 특정 영역의 데이터를 제거할 경우
  - `notifyItemMoved` : 특정 위치를 교환할 경우

  ### 2. RecyclerView의 Viewtype 지정
  - 아래와 같이 adapter에서 `viewtype` 을 지정하여 hodler의 layout을 다르게 지정할 수 있다.

  ```java
  @Override
  public int getItemViewType(int position) {
      if(data.get(position).equals("item : 0"))
          return 0;
      else
          return 1;
  }

  @Override
  public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view;
      switch (viewType){
          case 1:
              view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_msg_other, parent, false);
              break;
          default:
              view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_msg_me, parent, false);
              break;
      }
      return new Holder(view);
  }
  ```

  ### 3. CustomDivider
  - `getItemOffsets` : 아이템의 영역(공백을 추가 시킴)을 늘릴 수 있다.
  - `onDraw` : 아이템이 그려지기 전에 먼저 그린다.
  - `onDrawOver` : 아이템 위에 덮어서 그린다.

  > CustomDivider.java

  ```java
  public class CustomDivider extends RecyclerView.ItemDecoration {
      public Drawable mDivider;
      private final int verticalSpaceHeight;

      public CustomDivider(Context context, int verticalSpaceHeight){
          mDivider = context.getResources().getDrawable(R.drawable.line_divider);
          this.verticalSpaceHeight = verticalSpaceHeight;
      }

      @Override
      public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
          // 마지막 아이템이 아닌 경우, 공백 추가
          if(parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() -1 ){
  //            outRect.set(left, top, right, bottom);
              outRect.bottom = verticalSpaceHeight; // 아래쪽 공백 추가
              outRect.top = 10; // 위쪽 공백 추가
          }
      }

      @Override
      public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
          super.onDraw(c, parent, state);
      }

      @Override
      public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
          // parent.getPaddingLeft() : recyclerView의 padding값을 불러온다.
          // parent.getWidth : recyclerView의 width값을 불러온다.
          int left = parent.getPaddingLeft();
          int right = parent.getWidth() - parent.getPaddingRight();

          int childCount = parent.getChildCount();
          for(int i=0 ; i<childCount ; i++){
              View child = parent.getChildAt(i);

              RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
              // child.getBottom : child까지의 높이, params.bootmMargin : RecyclerView의 item의 최상위 parent에서 결정한 margin값
              int top = child.getBottom() + params.bottomMargin + verticalSpaceHeight;
              // mDivider.getIntrinsicHeight : line.divider.xml에서 정의한 높이값
              int bottom = top + mDivider.getIntrinsicHeight();

              // 밑줄의 사각형 bound(굵기 및 길이)를 결정 (left~right : 길이, top~bottom : 높이)
              mDivider.setBounds(left, top,right,bottom);
              // 밑줄을 그린다.
              mDivider.draw(c);
          }
      }
  }
  ```

  > item_divider.xml

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <shape xmlns:android="http://schemas.android.com/apk/res/android"
      android:shape="rectangle">
      <size
          android:width="1dp"
          android:height="1dp"/>
      <solid
          android:color="@color/colorPrimary"/>

  </shape>
  ```

  ### 4. CustomItemAnimator
  - recyclerView의 item이 추가되거나 사라졌을때 등 데이터의 변경이 있을때 애니메이션 효과를 준다.
  - DefaultItemAnimator를 상속받으면 기본적인 애니메이션 효과를 줄 수 있다.
  - `notifyItemInserted` 혹은 `notifyItemRemoved` 등이 호출될 때 애니메이션이 실행된다.

  >CustomItemAnimator.java

  ```java
  public class CustomItemAnimator extends DefaultItemAnimator {

      @Override
      public boolean animateRemove(final RecyclerView.ViewHolder holder) {
          View view = holder.itemView;
          ObjectAnimator ani = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 300).setDuration(1000);
          ani.setInterpolator(new DecelerateInterpolator());
          // 리스너를 달아 끝나는 경우 holder를 삭제시킨다.
          ani.addListener(new Animator.AnimatorListener() {
              @Override
              public void onAnimationStart(Animator animation) {}

              @Override
              public void onAnimationEnd(Animator animation) {
                  dispatchRemoveFinished(holder);
              }

              @Override
              public void onAnimationCancel(Animator animation) {}
              @Override
              public void onAnimationRepeat(Animator animation) {}
          });
          ani.start();
          // returndl true일 경우 실행 완료 후 runPendingAnimations 메소드를 실행
          return true;
      }

      @Override
      public boolean animateAdd(RecyclerView.ViewHolder holder) {
          return super.animateAdd(holder);
      }

      @Override
      public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
          return super.animateMove(holder, fromX, fromY, toX, toY);
      }

      @Override
      public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
          return super.animateChange(oldHolder, newHolder, fromX, fromY, toX, toY);
      }

      @Override
      public void runPendingAnimations() {
          super.runPendingAnimations();
          Log.e("실행","======================================================");
      }
  }
  ```

  > 적용방법

  ```java
  // Mainactivty 에서
  recyclerView.setItemAnimator(new CustomItemAnimator());

  // adapter에서
  public void removeDate(List<String> data){
      this.data = data;
      notifyItemRemoved(data.size());
  }
  ```

  ### 5. RecyclerView의 PreCache / Prefetch
  - LayoutManager를 상속받아 `PreCache`를 만든다.
  - `getExtraLayoutSpace` 메소드 에서 `extraLayoutSpace` 만큼 추가적으로 holder 를 생성하여 이미지와 같이 크기가 큰 파일을 빠르게 로드하는 것처럼 보이게 한다.
  - RecyclerView에서 LayoutManager를 세팅할때 이를 세팅한다.
  - `Prefetch`의 경우 LayoutManager를 세팅할 때 설정해준다.

  > PreCachingLayoutManager.java

  ```java
  public class PreCachingLayoutManager extends LinearLayoutManager {
      private static final int DEFAULT_EXTRA_LAYOUT_SPACE = 600;
      private int extraLayoutSpace = -1;

      public PreCachingLayoutManager(Context context) {
          super(context);
      }

      public void setExtraLayoutSpace(int extraLayoutSpace) {
          this.extraLayoutSpace = extraLayoutSpace;
      }

      @Override
      protected int getExtraLayoutSpace(RecyclerView.State state) {
          if(extraLayoutSpace >0) {
              return extraLayoutSpace;
          }
          return DEFAULT_EXTRA_LAYOUT_SPACE;
      }
  }
  ```

  > 적용

  ```java
  PreCachingLayoutManager manager = new PreCachingLayoutManager(this);
  manager.setOrientation(LinearLayoutManager.VERTICAL);
  // 화면 사이즈만큼 가져오는 유틸을 통해 그만큼 holder를 더 생성
  manager.setExtraLayoutSpace(DeviceUtil.getScreenHeight(this));
  recyclerView.setLayoutManager(manager);
  ```

  > Prefetch 적용

  ```java
  LinearLayoutManager manager = new LinearLayoutManager(this);
  manager.setItemPrefetchEnabled(true);
  manager.setInitialPrefetchItemCount(10);
  recyclerView.setLayoutManager(manager);
  ```

---

## 참고 자료
#### 1. [목록 및 카드 생성](https://developer.android.com/training/material/lists-cards.html)

#### 2. [RecyclerView](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html)
