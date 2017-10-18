# JsonData 및 네트워크 활용
  - Json 형식에 대한 설명
  - Glide 라이브러리 사용방법
  - Gson 라이브러리 사용방법
  - sodhanalibrary 사용방법
  - 오픈Api를 이용해 Json 데이터를 불러와 뿌려주는 예제 2가지
  - <참고>
    - 서버 연결 오류시 정의할 값
    - Restful 방식과 주소 전달 체계

---

## Json 형식에 대해
  ### 2. Json 형태
  - json string 은 아래와 같이 세 가지의 조합으로 이루어진다.
    - ① 배열형을 표현하는 대괄호
    - ② 객체(오브젝트)를 표현하는 중괄호
    - ③ key값
  - 중괄호를 포함하는 경우 클래스의 타입은 제일 앞을 대문자로 바꾼 것이 된다.
  - 이름이 없으면 개발자가 임의로 정해줄 수 있으며, 대괄호는 배열을 참조한다.
  - 대괄호가 있으면 배열을 참조하게 되며, 오브젝트까지는 변수를 리턴한다.
  - 사용 예시

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/JsonDataPractice/picture/json.png)

  ### 2. 통신 개요
  - 아래 그림과 같이 접근한다. (타입은 정해줄 수 있다.)

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/JsonDataPractice/picture/json2.png)

---

## Glide & Gson 라이브러리 & sodhanalibrary
  ### 1. Glide 사용법 ([글라이드 라이브러리](https://github.com/bumptech/glide))
  - Gradle 설정 (버전을 4.0 or 3.+로.. 업데이트 가능)

  ```
  compile 'com.github.bumptech.glide:glide:3.+'
  ```

  - 사용방법 (이미지를 imageView에 url을 가져와서 세팅)

  ```java
  Glide.with(context)                                 // 글라이드 초기화
          .load(url)   // 주소에서 이미지 가져오기
          .into(imageView);                    // 실제 대상에 꽂는다.
  ```

  ### 2. Gson 사용법 ([Gson 라이브러리](https://github.com/google/gson))
  - Gson 설정

  ```
  compile 'com.google.code.gson:gson:2.8.2'
  ```

  - 사용방법 (json string 포멧을 가져와서 정의한 클래스에 따라 값을 대입)
  ```java
  Gson gson = new Gson();
  User user = gson.fromJson(item,User.class);
  ```

  ### 3. sodhanalibrary 사용법 ([링크](http://pojo.sodhanalibrary.com/Convert))
  - json 포멧을 클래스로 변환시켜주는 라이브러리
  - 생성할 클래스가 다른 변수명과 겹치지 않도록 조심해서 대입
  - 생성된 변수는 변경할 수 없으며 클래스는 변경 가능

 ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/JsonDataPractice/picture/sodhanalibrary.png)

---

## 예제 공통부분
  ### 1. Layout 및 결과
  - TabLayout 및 viewPager 이용

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/JsonDataPractice/picture/json3.png)

  ### 2. MainActivity & PagerAdapter
  - 생략... → [참고_ViewPager](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/TabLayoutView)

  ### 3. 네트워크 연결(Remote.class)
  - 다른부분은 동일 ([참고_네트워크](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/BasicNetwork))
  - 추가부분 : 서버 연결 에러 방지를 위해 runFlag를 주어 서버에서 값을 받아오지 못하면 반복문을 count만큼 계속 돌려주며 계속 연결이 안될 시 예외처리를 한다.

  > Remote.class

  ```java
  public class Remote {

      public static String getData(String string){
          boolean runFlag = true;
          int accessCount = 0;
          final StringBuilder result = new StringBuilder();
          // 서버에 접속이 안될경우 반복해주며 5번 접속해도 안될 시 Error 값을 리턴
          while(runFlag) {
              try {
                  URL url = new URL(string);
                  HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                  connection.setRequestMethod("GET");
                  if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                      InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                      BufferedReader br = new BufferedReader(isr);
                      String temp = "";
                      while((temp=br.readLine()) != null){
                          result.append(temp).append("\n");
                      }
                      br.close();
                      isr.close();
                  } else{
                      Log.e("ServerError",connection.getResponseCode() +"");
                  }
                  connection.disconnect();
                  runFlag = false;
              } catch (Exception e) {
                  runFlag=true;
                  accessCount++;
                  Log.e("Error", e.toString());
                  if(accessCount>5){
                      runFlag=false;
                      return "AccessError";
                  }
              }
          }
          return result.toString();
      }
  }
  ```

---

## 예제1 (GitHubUsers)
  ### 1. 데이터 구조 클래스 (User.class)
  - json 형태의 데이터를 변환

  ```java
  public class User {
      int id;
      String login;
      String avatar_url;
      String gravatar_id;
      String url;
      /* ..생략.. */

      public int getId() { return id; }
      public String getLogin() { return login; }
      public String getAvatar_url() { return avatar_url; }
      public void setId(int id) { this.id = id; }
      public void setLogin(String login) { this.login = login; }
      public void setAvatar_url(String avatar_url) { this.avatar_url = avatar_url; }
      /* ..생략.. */
  ```

  ### 2. GitHubUserAdapter
  - User 클래스의 리스트를 받아서 데이터를 뿌려줌
  - 이미지는 Glide 라이브러리를 사용해서 뿌려줌
  - ListView 이므로 BaseAdapter 상속

  ```java
  public class GitHubUserAdapter extends BaseAdapter {

      Context context;
      List<User> data;

      public GitHubUserAdapter(Context context,List<User> data){
          this.context = context;
          this.data = data;
      }
      // 전체 사이즈
      @Override
      public int getCount() {
          return data.size();
      }
      // 현재 뿌려질 데이터를 리턴
      @Override
      public Object getItem(int position) {
          return data.get(position);
      }
      // 뷰의 아이디를 리턴
      @Override
      public long getItemId(int position) {
          return position;
      }
      // 목록에 나타나는 아이템 하나하나를 그린다.
      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
          Holder holder = null;
          // 아이템View 재사용
          if(convertView == null){
              convertView = LayoutInflater.from(context).inflate(R.layout.githubuser_list,parent,false);
              // 홀더에 담기
              holder = new Holder();
              holder.textId = (TextView)convertView.findViewById(R.id.textId);
              holder.textLogin = (TextView)convertView.findViewById(R.id.textLogin);
              holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
              // 태그 달기
              convertView.setTag(holder);
          } else{
              // 태그 추출
              holder = (Holder) convertView.getTag();
          }
          // 값 입력
          holder.textId.setText(data.get(position).getId()+"");
          holder.textLogin.setText(data.get(position).getLogin());
          // 이미지 불러오기 (그래들에 추가)
          Glide.with(context)                                 // 글라이드 초기화
                  .load(data.get(position).getAvatar_url())   // 주소에서 이미지 가져오기 (폰에있는 uri 넘기면 그것을 넘길수도 있음)
                  .into(holder.imageView);                    // 실제 대상에 꽂는다.

          return convertView;
      }
  }
  class Holder{
      TextView textId;
      TextView textLogin;
      ImageView imageView;
  }
  ```

  ### 3. GitHubUserView
  - `https://api.github.com/users` url을 이용
  - AsyncTask를 통해 Thread를 돌린다.
  - json 포멧을 직접 파싱 / Gson 라이브러리를 이용
  - adapter의 경우 데이터를 넘겨주어야 하는데 Thread에서 작동하므로 반드시 작업이 끝난후 대입할 수 있도록 설정 (parse 아래에 initListView를 호출)
  - Remote에서 서버 연결이 안되었다고 날라온 경우 Toast를 띄운다.

  ```java
  public class GitHubUserView extends FrameLayout {

      ListView listView;
      List<User> users;

      public GitHubUserView(Context context) {
          super(context);

          load();
          initView();
      }
      // View를 초기화
      private void initView(){
          View view = LayoutInflater.from(getContext()).inflate(R.layout.githubuser,null);
          listView = (ListView) view.findViewById(R.id.listView);
          addView(view);
      }
      // 리스트뷰 아답터 연결
      private void initListView(){
          GitHubUserAdapter adapter = new GitHubUserAdapter(getContext(),users);
          listView.setAdapter(adapter);
      }
      // 데이터를 로드
      private void load(){
          // data 변수에 불러온 데이터를 입력
          new AsyncTask<Void, Void, String>(){

              @Override
              protected String doInBackground(Void... params) {
                  return Remote.getData("https://api.github.com/users");
              }

              @Override
              protected void onPostExecute(String jsonString) {
                  if("AccessError".equals(jsonString)){
                      Toast.makeText(getContext(), "githubuser Api 통신 오류", Toast.LENGTH_SHORT).show();
                  } else {
                      // jsonString을 parsing
                      users = parse(jsonString);
                      // data = 결과 (쓰레드로 작동하기 때문에 작업이 끝난후 이어지게 하려면 이곳에 넣는다.)
                      initListView();
                  }
              }
          }.execute();
      }

      // Gson 라이브러리 사용
      private List<User> parse(String string){
          List<User> result = new ArrayList<>();
          // 양 끝 문자 없애기 [, ]
          string = string.substring(2, string.length()-3);
          // 문자열 분리하기
          String array[] = string.split("\\},\\{");
          for(String item : array) {
              item = "{"+item+"}";
              Gson gson = new Gson();
              User user = gson.fromJson(item,User.class);
              result.add(user);
          }
          return result;
      }
  //    직접 파싱하는 메소드
  //    private List<User> parse(String string){
  //        List<User> result = new ArrayList<>();
  //        // 앞의 문자 두개 없애기 [ {
  //        string = string.substring(string.indexOf("{")+1);
  //        // 뒤의 문자 두개 없애기 } ]
  //        string = string.substring(0,string.lastIndexOf("}"));
  //        // 문자열 분리하기
  //        String array[] = string.split("\\},\\{");
  //        for(String item : array){
  //            User user = new User();
  //            // item 문자열을 분리하여 user의 멤버변수에 담기
  //            String member[] = item.split(",");
  //            for(String unit : member){
  //                String first = unit.substring(0,unit.indexOf(":"));
  //                String second = unit.substring(unit.indexOf(":")+1);
  //
  //                if(first.equals("\"id\"")){
  //                    user.setId(Integer.parseInt(second));
  //                } else if(first.equals("\"login\"")){
  //                    second = second.substring(second.indexOf("\"")+1,second.lastIndexOf("\""));
  //                    user.setLogin(second);
  //                } else if(first.equals("\"avatar_url\"")){
  //                    second = second.substring(second.indexOf("\"")+1,second.lastIndexOf("\""));
  //                    user.setAvatar_url(second);
  //                }
  //            }
  //            result.add(user);
  //        }
  //        return result;
  //    }
  }  
  ```

---

## 예제2 (TransferTation 유동인구_서울열린데이터)
  ### 0. 호출하는 형태 그림
  - 최상위 클래스를 호출하여 그 안에 있는 메소드를 이용한다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/JsonDataPractice/picture/json4.png)

  ### 1. model(sodhanalibrary 이용) 및 서울시 api 이용
  - 서울 열린데이터에서 openapi를 클릭하여 api 주소를 복사
  - http://openapi.seoul.go.kr:8088/(인증키)/json/StationDayTrnsitNmpr/1/5/ </br> → 프로토콜 / 	인증키 / 문서타입 / 서비스명 / 시작데이터인덱스 / 끝데이터 인덱스
  - 인증키를 바꾸고 문서타입을 json으로 바꿔야 한다.
  - 명령어 구조는 데이터베이스마다, 웹서버마다 다르다.
  - 라이브러리를 이용하여 클래스를 잘 붙이도록 한다. (다른 클래스와 겹치지 않게 한다.)

  ```java
  public class JsonTransferClass{/*..생략..*/}
  public class StationDayTrnsitNmpr {/*..생략..*/}
  public class RESULT {/*..생략..*/}
  public class Row {/*..생략..*/}
  ```

  ### 2. TransferAdapter
  - Adapter 연결시 Json클래스 형태로 받아와서 세부 데이터를 조작
  - ListView를 사용하여 연결

  ```java
  public class TransferAdapter extends BaseAdapter {

      List<Row> rows;
      JsonTransferClass json;
      Context context;

      public TransferAdapter(Context context, JsonTransferClass json){
          this.context = context;
          this.json = json;
          Row[] tempRow = json.getStationDayTrnsitNmpr().getRow();
          rows = Arrays.asList(tempRow);
      }
      @Override
      public int getCount() { return rows.size(); }
      @Override
      public Object getItem(int position) { return rows.get(position); }
      @Override
      public long getItemId(int position) { return position; }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
          Holder holder = null;
          if(convertView == null){
              convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transferstation_list,parent,false);
              holder = new Holder();
              holder.setInitView(convertView);
              convertView.setTag(holder);
          } else{
              holder = (Holder) convertView.getTag();
          }

          Row row = rows.get(position);
          holder.setTextSN(row.getSN());
          holder.setTextSTATN_NM(row.getSTATN_NM());
          holder.setTextWKDAY(row.getWKDAY());
          holder.setTextSATDAY(row.getSATDAY());
          holder.setTextSUNDAY(row.getSUNDAY());
          return convertView;
      }
  }
  class Holder{
      TextView textSN;
      TextView textSTATN_NM;
      TextView textWKDAY;
      TextView textSATDAY;
      TextView textSUNDAY;

      public void setInitView(View view){
          textSN = (TextView) view.findViewById(R.id.textSN);
          textSTATN_NM = (TextView) view.findViewById(R.id.textSTATN_NM);
          textWKDAY = (TextView) view.findViewById(R.id.textWKDAY);
          textSATDAY = (TextView) view.findViewById(R.id.textSATDAY);
          textSUNDAY = (TextView) view.findViewById(R.id.textSUNDAY);
      }
      public void setTextSN(String SN){ textSN.setText(SN); }
      public void setTextSTATN_NM(String SN){ textSTATN_NM.setText(SN); }
      public void setTextWKDAY(String SN){ textWKDAY.setText(SN); }
      public void setTextSATDAY(String SN){ textSATDAY.setText(SN); }
      public void setTextSUNDAY(String SN){ textSUNDAY.setText(SN); }
  }
  ```

  ### 3. TransferStationView
  - Remote를 이용하여 데이터를 받아온다.
  - 외부 api 주소인 `http://openapi.seoul.go.kr:8088/(인증키)/json/StationDayTrnsitNmpr/1/100/` 사용
  - Gson 라이브러리 이용

  ```java
  public class TransferStationView extends FrameLayout {

      ListView listView;
      TextView textCount;
      TextView textCode;
      TextView textMessage;
      JsonTransferClass jsonData;

      public TransferStationView(Context context) {
          super(context);
          initView();
          load();
      }

      private void initView(){
          View view = LayoutInflater.from(getContext()).inflate(R.layout.transferstation,null);
          textCount = (TextView) view.findViewById(R.id.textCount);
          textCode = (TextView) view.findViewById(R.id.textCode);
          textMessage = (TextView) view.findViewById(R.id.textMessage);
          listView = (ListView) view.findViewById(R.id.listView);
          addView(view);
      }

      private void load(){
          new AsyncTask<Void, Void, String>(){
              @Override
              protected String doInBackground(Void... params) {
                  return Remote.getData("http://openapi.seoul.go.kr:8088/(인증키)/json/StationDayTrnsitNmpr/1/100/");
              }

              @Override
              protected void onPostExecute(String jsonString) {
                  if("AccessError".equals(jsonString)){
                      Toast.makeText(getContext(), "환승 유동 인구 Api 통신 오류", Toast.LENGTH_SHORT).show();
                  } else {
                      parse(jsonString);
                      setListAdapter();
                      setTextInView();
                  }
              }

          }.execute();
      }
      private void parse(String string){
          Gson gson = new Gson();
          jsonData = gson.fromJson(string,JsonTransferClass.class);

      }
      private void setListAdapter(){
          TransferAdapter adapter = new TransferAdapter(getContext(),jsonData);
          listView.setAdapter(adapter);
      }
      private void setTextInView(){
          textCount.setText(jsonData.getStationDayTrnsitNmpr().getList_total_count());
          textCode.setText(jsonData.getStationDayTrnsitNmpr().getRESULT().getCODE());
          textMessage.setText(jsonData.getStationDayTrnsitNmpr().getRESULT().getMESSAGE());
      }
  }
  ```

---

## 참고 개념

  ### 1. 서버 연결시 Flag값 주기
  - Remote 클래스에서 반복해서 연결하기 위해 반복문을 쓰며 Flag값을 준다.
  - 일정 카운트값까지 반복하도록 한다.
  ```java
  while(runFlag) {
    /* 접속 시도 */
    if(count==?) runFlag=false;
  }
  ```

  ### 2. Restful 방식
  - 이전에는 액션에 따라 모든 파일(php)를 만들었는데 현재는 위에서 쓴 `GET` 과 같이 Method로 알려주게 된다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/JsonDataPractice/picture/restful.png)

  ### 3. Restful 주소전달체계
  - GET의 경우는 주소에 모두 호출한다. 그 외 여러가지 존재
  - 나머지는 Body안에 데이터가 딸려서 들어간다.
  - [참고자료](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/JsonDataPractice/pdf/RESTful.pdf)

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/JsonDataPractice/picture/restful2.png)
