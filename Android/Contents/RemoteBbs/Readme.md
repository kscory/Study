# RemoteBbs (게시판 기능)
  - 데이터 통신
  - 목록 뿌리기
  - [서버참고_MongoDB를 이용하여 간단한 게시판 구축](https://github.com/Lee-KyungSeok/Study/tree/master/Node.js/server_bbs)

---
## 출력 결과

![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/RemoteBbs/picture/bbsex.png)

---

## 게시판 구성
  ### 1. 데이터 클래스 정의 (model 패키지)
  - 서버에서 정의된 데이터를 Json 데이터 형식으로 클래스를 만든다.
  - sodhalibreary 이용
  - Result에 결과를 알려주는 isSuccess 메소드 정의

  > Data.java

  ```java
  public class Data {
      private String _id;
      private String content;
      private String title;
      private String user_id;
      private String date;

      public String getContent() { return content; }
      public void setContent(String content) { this.content = content; }
      public String getTitle() { return title; }
      public void setTitle(String title) { this.title = title; }
      public String get_id() { return _id; }
      public void set_id(String _id) { this._id = _id; }
      public String getUser_id() { return user_id; }
      public void setUser_id(String user_id) { this.user_id = user_id; }
      public String getDate() { return date; }
      public void setDate(String date) { this.date = date; }

      @Override
      public String toString() {
          return "ClassPojo [content = " + content + ", title = " + title + ", _id = " + _id + ", user_id = " + user_id + ", date = " + date + "]";
      }
  }
  ```

  > Result.java

  ```java
  public class Result {
      private Data[] data;
      private String code;
      private String msg;

      public String getMsg() { return msg; }
      public void setMsg(String msg) { this.msg = msg; }
      public Data[] getData() { return data; }
      public void setData(Data[] data) { this.data = data; }
      public String getCode() { return code; }
      public void setCode(String code) { this.code = code; }

      public boolean isSuccess() { return "200".equals(code); }

      @Override
      public String toString() {
          return "ClassPojo [data = " + data + ", code = " + code + ", msg = " + msg + "]";
      }
  }
  ```

  ### 2. 서버와 통신하는 클래스 정의 (Remote)
  - json형태로 데이터를 받아 처리
  - 서버와 통신할 때 Header에 데이터를 담아서 전달해야 한다.
    -` con.setRequestProperty("Content-Type","application/json; charset=utf8");` : Content-Type을 정한다.
    - `con.setRequestProperty("Authorization","token=dddd");` : token을 결정한다.

  > Remote.java

  ```java
  public class Remote {

      public static String sendPost(String address, String json){
          String result = "";
          try{
              URL url = new URL(address); // url = naver.com?id=aaa&pw=12345
              HttpURLConnection con = (HttpURLConnection) url.openConnection();
              con.setRequestMethod("POST");
              // header 작성 -----------
              con.setRequestProperty("Content-Type","application/json; charset=utf8");
              con.setRequestProperty("Authorization","token=dddd");
              // post 데이터를 전송 -----------
              con.setDoOutput(true);
              OutputStream os = con.getOutputStream();
              os.write(json.getBytes());
              os.flush();
              os.close();
              // -----------------------------
              if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
                  InputStreamReader isr = new InputStreamReader(con.getInputStream());
                  BufferedReader br = new BufferedReader(isr);
                  String temp = "";
                  while ((temp = br.readLine()) != null) {
                      result += temp;
                  }
                  br.close();
                  isr.close();
              }else{
                Log.e("ServerError", con.getResponseCode() + "");
              }
          }catch(Exception e){
              e.printStackTrace();
          }
          return result;
      }

      public static String getData(String string){ // naver.com?id=aaa&pw=1234
          final StringBuilder result = new StringBuilder();
          try {
              URL url = new URL(string);
              HttpURLConnection con = (HttpURLConnection)url.openConnection();
              con.setRequestMethod("GET");
              // header 작성 -----------
              //con.setRequestProperty("Content-Type","application/json; charset=utf8");
              con.setRequestProperty("Authorization","token=나중에서버랑할때쓸거임");
              // 통신이 성공인지 체크
              if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                  InputStreamReader isr = new InputStreamReader(con.getInputStream());
                  BufferedReader br = new BufferedReader(isr);
                  String temp = "";
                  while ((temp = br.readLine()) != null) {
                      result.append(temp);
                  }
                  br.close();
                  isr.close();
              } else {
                  Log.e("ServerError", con.getResponseCode()+"");
              }
              con.disconnect();
          }catch(Exception e){
              Log.e("Error", e.toString());
          }
          return result.toString();
      }
  }
  ```

  ### 3. 목록엑티비티
  - 서버로부터 데이터를 받아 목록을 뿌려준다.
  - LayoutManger를 정의한 후 정의되어 있는 메소드를 통해 목록을 20개씩 호출한다.
  - 처음에만 recyeleradapter를 정의하고 그 이후는 데이터만 세팅한다.
  - startActivity를 이용해 post가 일어난 후 다시 데이터를 세팅한다.
  - RecyclerView 에는 페이지의 마지막 번호가 몇번인지 알려주는 메소드가 존재한다. - `findLastVisibleItemPosition`(ListView에는 존재하지 않음)

  > MainActivity.java

  ```java
  public class MainActivity extends AppCompatActivity {

      private Button button;
      private RecyclerView recyclerView;
      private ProgressBar progressBar;
      private Intent postIntent;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);
          postIntent = new Intent(MainActivity.this, PostActivity.class);
          initView();
          load();
      }

      RecyclerAdapter adapter;
      LinearLayoutManager lmManager;
      private void setList(Result result) {
          adapter = new RecyclerAdapter(result.getData());
          recyclerView.setAdapter(adapter);
          lmManager = new LinearLayoutManager(this);
          recyclerView.setLayoutManager(lmManager);
      }
      private void addList(Result result) {
          adapter.addDataAndRefresh(result.getData());
      }

      private int page = 1;
      private void load() {
          new AsyncTask<Void, Void, Result>() {
              @Override
              protected void onPreExecute() {
                  super.onPreExecute();
                  progressBar.setVisibility(View.VISIBLE);
              }
              @Override
              protected Result doInBackground(Void... voids) {
                  try {
                      Thread.sleep(3000);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
                  String result = Remote.getData("http://192.168.0.6:8090/bbs?type=all&page="+page);
                  Log.d("LOAD","result=================="+result);
                  Gson gson = new Gson();
                  Result data = gson.fromJson(result, Result.class);
                  return data;
              }
              @Override
              protected void onPostExecute(Result result) {
                  progressBar.setVisibility(View.GONE);
                  if(result.isSuccess()){
                      if(page == 1){
                          setList(result);
                      } else if(page > 1){
                          addList(result);
                      }
                      page++;
                  }
              }
          }.execute();
      }

      private void initView() {
          button = (Button) findViewById(R.id.button);
          progressBar = (ProgressBar) findViewById(R.id.progressBar);
          recyclerView = (RecyclerView) findViewById(R.id.recycler);
          recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
              @Override
              public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                  super.onScrollStateChanged(recyclerView, newState);
              }
              @Override
              public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                  /* 이는 알아두어야 함
                  int visibleCount = ImManager.getChildCount(); // 화면에 보이는 아이템 개수
                  int firstPosition = ImManager.findFirstVisibleItemPosition(); // 화면에 최상단에 보이는 아이템
                  boolean bottom = false; // 바닥인지 체크
                  */
                  int totalCount = lmManager.getItemCount();
                  int lastPosition = lmManager.findLastVisibleItemPosition();
                  // 현재 바닥에 도달했으면
                  if(lastPosition == totalCount-1) load();
              }
          });
      }

      public static final int POST = 999;
      public void openPost(View view){
          startActivityForResult(postIntent,POST);
      }
      @Override
      protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          super.onActivityResult(requestCode, resultCode, data);
          switch (requestCode){
              case POST:
                  if(resultCode == RESULT_OK){
                      page=1;
                      load();
                  } else{
                      Log.e("error","error");
                  }
                  break;
          }
      }
  }
  ```

  ## 4. recyclerView 어뎁터
  - data를 refresh 시키기 위해 기존 데이터에 신규 데이터를 합치는 작업을 거친다.
  - `notifyDataSetChanged` 를 통해 데이터를 갱신한다.

  > RecyclerAdapter.java

  ```java
  public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {
      Data datas[];

      public RecyclerAdapter(Data datas[]){
          this.datas = datas;
      }

      @Override
      public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
          View view = LayoutInflater.from(parent.getContext())
                  .inflate(R.layout.item_list, parent, false);
          return new Holder(view);
      }

      @Override
      public void onBindViewHolder(Holder holder, int position) {
          Data data = datas[position];
          holder.textTitle.setText(data.getTitle());
          holder.textDate.setText(data.getDate());
      }

      @Override
      public int getItemCount() {
          return datas.length;
      }

      public void addDataAndRefresh(Data[] data) {
          // 기존 데이터에 신규 데이터를 합친다.
          Data temp[] = new Data[datas.length + data.length];
          System.arraycopy(datas, 0, temp, 0, datas.length);
          System.arraycopy(data,0, temp, datas.length, data.length);
          datas = temp;
          // 데이터를 갱신한다.
          notifyDataSetChanged();
      }

      public class Holder extends RecyclerView.ViewHolder{
          TextView textTitle;
          TextView textDate;
          public Holder(View view){
              super(view);
              textTitle = view.findViewById(R.id.textTitle);
              textDate = view.findViewById(R.id.textDate);
          }
      }
  }
  ```

  ### 5. 글 작성 후 post (PostActivity)
  - Remote 클래스의 sendpost를 이용하여 작성한 게시물을 서버로 전송한다.
  - android id 가져오기
    - `android.provider.Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);`
    - manifest에 `<uses-permission android:name="android.permission.READ_PHONE_STATE"/>` 추가
  - 날짜는 보통 서버에서 세팅한 후 전달해준다.

  > PostActivity.java

  ```java
  public class PostActivity extends AppCompatActivity {

      private Button btnPost;
      private EditText editTitle;
      private EditText editContent;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_post);
          initView();
          btnPost.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  post();
              }
          });
      }

      private void post(){
          // 원래는 예외처리 필요
          String title = editTitle.getText().toString();
          String content = editTitle.getText().toString();

          Data data = new Data();
          data.setTitle(title);
          data.setContent(content);

          // android id 가져오기 => 권한설정 필요(phone_state)
          String dID = android.provider.Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
          data.setUser_id(dID);
          // 날짜는 서버에서 세팅

          new AsyncTask<Data, Void, Result>() {
              @Override
              protected Result doInBackground(Data... params) {
                  Gson gson = new Gson();
                  String json = gson.toJson(params[0]);
                  String result_string = Remote.sendPost("http://192.168.0.6:8090/bbs",json);
                  Result result = gson.fromJson(result_string,Result.class);
                  return result;
              }

              @Override
              protected void onPostExecute(Result result) {
                  Log.d("POST","result = "+result);
                  if(result == null || !result.isSuccess()) {
                      Toast.makeText(PostActivity.this, "오류", Toast.LENGTH_SHORT).show();
                      setResult(RESULT_CANCELED);
                  }else {
                      setResult(RESULT_OK);
                  }
                  finish();
              }
          }.execute(data);
      }

      private void initView(){
          btnPost = (Button) findViewById(R.id.btnPost);
          editContent = (EditText) findViewById(R.id.editContent);
          editTitle = (EditText) findViewById(R.id.editTitle);
      }
  }
  ```
