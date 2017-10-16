# Network & Thread
  - Network 이용방법
  - AsyncTask를 이용하는 방법
  - Sub Thread의 다른 사용 방법 (참고개념)

---

## Network 사용 방법
  ### 1. 사용 순서
  - ① 권한설정 > 런타임권한(x)
  - ② Thread > 네트워크를 통한 데이터 이용은 Sub Thread
  - ③ HttpURLConnection > 내장 Api (안드로이드에서 제공하는 기본 Api)
    - Retrofit (내부에 Thread 포함)
    - Rx (내부에 Thread 포함, Thread 관리기능 포함, 예외처리 특화)

  > 권한 설정 (manifest.xml)

  ```xml
  <uses-permission android:name="android.permission.INTERNET"/>
  ```

  ### 2. HttpURLConnection 사용하기
  - ① URL 객체를 선언 (웹주소를 가지고 생성)
  - ② URL 객체에서 서버 연결을 해준다 > HttpURLConnection 을 생성 = Stream
  - ③ 커넥션의 방식을 설정 (기본값 = GET)
  - ④ 연결되어 있는 Stream 을 통해서 데이터를 가져온다.
  - ⑤ 연결(Stream)을 닫는다.

  > 사용 예시

  ```java
  // 1. URL 객체를 선언 (웹주소를 가지고 생성)
  URL url = new URL("http://learnbranch.urigit.com/");
  // 2. URL 객체에서 서버 연결
  HttpURLConnection con = (HttpURLConnection) url.openConnection();
  // 3.  커넥션의 방식을 설정
  con.setRequestMethod("GET");
  // 통신이 성공인지 체크
  if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
      // 4. 연결되어 있는 Stream 을 통해서 데이터를 가져온다.
      //여기서부터는 파일에서 데이터를 가져오는 것과 동일
      InputStreamReader isr = new InputStreamReader(con.getInputStream());
      BufferedReader br = new BufferedReader(isr);
      String temp = "";
      while ((temp = br.readLine()) != null) {
          result.append(temp).append("\n");
      }
      br.close();
      isr.close();
  } else {
      Log.e("ServerError",con.getResponseCode()+"");
  }
  // 5. 연결(Stream)을 닫는다.
  con.disconnect();
  ```

  ### 사용 예시
  - 서버에서 HTTP 상태 코드를 날려주는 것을 Log로 체크한다. ([HTTP 상태 코드에 관해](https://ko.wikipedia.org/wiki/HTTP_%EC%83%81%ED%83%9C_%EC%BD%94%EB%93%9C))
  - Sub Thread에서 mainThread로 위젯만 바로 접근 불가하여 핸들러를 이용하며 데이터는 접근 가능하며 이를 이용해 textView에 값을 세팅한다.


  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicNetwork/picture/network.png)

  ```java
  public class NetworkBasic extends FrameLayout {

      FrameLayout stage;
      TextView textView;
      Context context;

      public static final int SERVER_CODE = 999;

      // 데이터를 메인 쓰레드에 정의
      String data="";

      public NetworkBasic(Context context) {
          super(context);
          this.context = context;

          initView();
          NetworkThread networkThread = new NetworkThread(handler);
          networkThread.start();
      }

      private void initView(){
          View view = LayoutInflater.from(getContext()).inflate(R.layout.network,null);
          stage = (FrameLayout) view.findViewById(R.id.stage);
          textView = (TextView) view.findViewById(R.id.textView);
          addView(view);
      }

      public void setData(){
          textView.setText(data);
      }

      Handler handler = new Handler(){
          @Override
          public void handleMessage(Message msg) {
              switch (msg.what){
                  case SERVER_CODE:
                      setData();
                      break;
              }
          }
      };

      class NetworkThread extends Thread{
          Handler handler = new Handler();
          public NetworkThread(Handler handler){
              this.handler = handler;
          }
          public void run(){
              final StringBuilder result = new StringBuilder();
              try {
                  // 1. URL 객체를 선언 (웹주소를 가지고 생성)
                  URL url = new URL("http://learnbranch.urigit.com/");
                  // 2. URL 객체에서 서버 연결
                  HttpURLConnection con = (HttpURLConnection) url.openConnection();
                  // 3.  커넥션의 방식을 설정
                  con.setRequestMethod("GET");
                  // 통신이 성공인지 체크
                  if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                      // 4. 연결되어 있는 Stream 을 통해서 데이터를 가져온다.
                      //여기서부터는 파일에서 데이터를 가져오는 것과 동일
                      InputStreamReader isr = new InputStreamReader(con.getInputStream());
                      BufferedReader br = new BufferedReader(isr);
                      String temp = "";
                      while ((temp = br.readLine()) != null) {
                          result.append(temp).append("\n");
                      }
                      br.close();
                      isr.close();
                  } else {
                      Log.e("ServerError",con.getResponseCode()+"");
                  }
                  // 5. 연결(Stream)을 닫는다.
                  con.disconnect();
              } catch (Exception e){
                  Log.e("Error",e.toString());
              }

              // 액티비티에 직접 값 넣기
              data = result.toString();
              // 핸들러로 메세지를 전달해서 함수 실행
              handler.sendEmptyMessage(NetworkBasic.SERVER_CODE);
          }
      }
  }  
  ```

---

## AsyncTask 사용 방법
  ### 1. AsyncTask란
  - AsyncTask = 세개의 기본함수를 지원하는 Thread 이며 주로 처음과 끝을 곧바로 사용하기 위해서 이용한다.
  - AsyncTask 메소드
    -  `onPreExecute` : doInBackground() 함수가 실행되기 전에 실행되는 함수
    - `doInBackground` : 백그라운드(sub thread)에서 코드를 실행하는 함수 < 얘만 sub thread
    - `onPostExecute` : doInBackground() 함수가 실행된 후에 실행되는 함수 (onPostExecute는 doInBackground로 부터 데이터를 받을 수 있다.)
  - AsyncTask의 제네릭 (AsyncTask<Gerneric1, Gerneric2, Gerneric3>())
    - Gerneric1 : doInBackground 함수의 파라미터로 사용
    - Gerneric2 : onProgressUpdate 함수의 파라미터로 사용 (주로 진행상태의 percent 값(int)으로 사용 => ProgressBar를 돌리는 등의 이용)
    - Gerneric3 : doInBackground 의 리턴값이면서 onPostExecute의 파라미터
  - `doInBackground(String... params)` 은 인자를 여러개 받을 수 있으며, `execute(String... params)` 로 여러개를 대입시킨다.

  ### 2. 사용 예시
  - AsyncTask를 이용한 클래스
  - Url(Server)를 연결하는 클래스

  > AsyncTask를 이용한 클래스(AsyncTaskExample.java)

  ```java
  public class AsyncTaskExample extends FrameLayout {

      TextView textView;
      FrameLayout stage;
      Context context;

      public AsyncTaskExample(Context context) {
          super(context);
          this.context = context;
          initView();
          getServer("http://google.com");
      }

      private void initView(){
          View view = LayoutInflater.from(getContext()).inflate(R.layout.asynctask,null);
          stage = (FrameLayout) view.findViewById(R.id.stage);
          textView = (TextView) view.findViewById(R.id.textView);
          addView(view);
      }

      private void getServer(String url){

          new AsyncTask<String, Void, String>(){
              @Override
              protected String doInBackground(String... params) {
                  // 사용할 때
                  String param1 = params[0];
                  String result = Remote.getData(param1);
                  return result;
              }
              @Override
              protected void onPostExecute(String result) {
                  result = result.substring(result.indexOf("<title>")+"<title>".length()
                          ,result.indexOf("</title>"));
                  textView.setText(result);
                  super.onPostExecute(result);
              }
          }.execute(url);
      }
  }
  ```

  > Url(Server)를 연결하는 클래스(Remote.java)

  ```java
  public class Remote {
      public static String getData(String string){
          StringBuilder result = new StringBuilder();
          try {
              URL url = new URL(string);
              HttpURLConnection con = (HttpURLConnection) url.openConnection();
              con.setRequestMethod("GET");
              if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                  InputStreamReader isr = new InputStreamReader(con.getInputStream());
                  BufferedReader br = new BufferedReader(isr);
                  String temp = "";
                  while ((temp = br.readLine()) != null) {
                      result.append(temp).append("\n");
                  }
                  br.close();
                  isr.close();
              } else {
                  Log.e("Server Error", con.getResponseCode() + "");
              }
              con.disconnect();
          } catch (Exception e){
              Log.e("Error",e.toString());
          }
          return result.toString();
      }
  }
  ```

---

## 참고 개념

  ### Thread의 여러가지 호출방법
  - １. `RunOnUiThread()` 이용 (Activity를 받아서 이용한다=>mainThread를 이용해야 하기 때문)
  - ２. Handler에서 `msg.obj`를 이용 (obj에 값을 넣어서 전달)
  - ３. 데이터를 먼저 선언한 후 이용 (위에서 사용)

  > 1. RunOnUiThread 이용

  ```java
  public class NetworkBasicOne extends FrameLayout {

      FrameLayout stage;
      TextView textView;
      Context context;
      Activity activity;

      public NetworkBasicOne(Context context) {
          super(context);
          this.context = context;
          if(context instanceof Activity){
              activity = (Activity) context;
          }
          initView();
          new NetworkThread().start();
      }

      private void initView(){
          View view = LayoutInflater.from(getContext()).inflate(R.layout.network,null);
          stage = (FrameLayout) view.findViewById(R.id.stage);
          textView = (TextView) view.findViewById(R.id.textView);
          addView(view);
      }

      class NetworkThread extends Thread{
          public void run(){
              final StringBuilder result = new StringBuilder();
              try {
                  URL url = new URL("http://fastcampus.co.kr");
                  HttpURLConnection con = (HttpURLConnection) url.openConnection();
                  con.setRequestMethod("GET");
                  if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                      InputStreamReader isr = new InputStreamReader(con.getInputStream());
                      BufferedReader br = new BufferedReader(isr);
                      String temp = "";
                      while ((temp = br.readLine()) != null) {
                          result.append(temp).append("\n");
                      }
                      br.close();
                      isr.close();
                  } else {
                      Log.e("ServerError",con.getResponseCode()+"");
                  }
                  con.disconnect();
              } catch (Exception e){
                  Log.e("Error",e.toString());
              }

              // runOnUiThread 사용
              activity.runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                      textView.setText(result.toString());
                  }
              });
          }
      }
  }
  ```

  > 2. Handler에서 msg.obj를 이용

  ```java
  public class NetworkBasicTwo extends FrameLayout {

      FrameLayout stage;
      TextView textView;
      Context context;
      public static final int SERVER_CODE_TWO = 999;

      public NetworkBasicTwo(Context context) {
          super(context);
          this.context = context;
          initView();
          NetworkThreadTwo networkThread = new NetworkThreadTwo(handler);
          networkThread.start();
      }

      private void initView(){
          View view = LayoutInflater.from(getContext()).inflate(R.layout.network,null);
          stage = (FrameLayout) view.findViewById(R.id.stage);
          textView = (TextView) view.findViewById(R.id.textView);
          addView(view);
      }
      // 핸들러 정의 및 obj에서 값을 꺼냄
      Handler handler = new Handler(){
          @Override
          public void handleMessage(Message msg) {
              switch (msg.what){
                  case SERVER_CODE_TWO:
                      setData(msg.obj.toString());
                      break;
              }
          }
      };

      private void setData(String data){
          textView.setText(data);
      }
  }

  class NetworkThreadTwo extends Thread{
      Handler handler;
      public NetworkThreadTwo(Handler handler){
          this.handler = handler;
      }
      public void run(){
          final StringBuilder result = new StringBuilder();
          try {
              URL url = new URL("http://data.seoul.go.kr/");
              HttpURLConnection connection = (HttpURLConnection) url.openConnection();
              connection.setRequestMethod("GET");
              if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                  InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                  BufferedReader br = new BufferedReader(isr);
                  String temp = "";
                  while ((temp = br.readLine()) != null) {
                      result.append(temp).append("\n");
                  }
                  br.close();
                  isr.close();
              } else {
                  Log.e("ServerError", connection.getResponseCode() + "");
              }
              connection.disconnect();
          } catch (Exception e){
              Log.e("Error",e.toString());
          }
          // msg.obj 사용
          Message msg = new Message();
          msg.what = NetworkBasicTwo.SERVER_CODE_TWO;
          msg.obj = result;
          handler.sendMessage(msg);
      }
  }
  ```

  > 3. 데이터를 먼저 선언한 후 이용 (위에서 사용)

  ```java
  public class NetworkBasic extends FrameLayout {

      FrameLayout stage;
      TextView textView;
      Context context;

      public static final int SERVER_CODE = 999;

      // 데이터를 메인 쓰레드에 정의
      String data="";

      public NetworkBasic(Context context) {
          super(context);
          this.context = context;

          initView();
          NetworkThread networkThread = new NetworkThread(handler);
          networkThread.start();
      }

      private void initView(){
          View view = LayoutInflater.from(getContext()).inflate(R.layout.network,null);
          stage = (FrameLayout) view.findViewById(R.id.stage);
          textView = (TextView) view.findViewById(R.id.textView);
          addView(view);
      }

      public void setData(){
          textView.setText(data);
      }

      Handler handler = new Handler(){
          @Override
          public void handleMessage(Message msg) {
              switch (msg.what){
                  case SERVER_CODE:
                      setData();
                      break;
              }
          }
      };
      class NetworkThread extends Thread{
          Handler handler = new Handler();
          public NetworkThread(Handler handler){
              this.handler = handler;
          }
          public void run(){
              final StringBuilder result = new StringBuilder();
              try {
                  URL url = new URL("http://learnbranch.urigit.com/");
                  HttpURLConnection con = (HttpURLConnection) url.openConnection();
                  con.setRequestMethod("GET");
                  if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                      InputStreamReader isr = new InputStreamReader(con.getInputStream());
                      BufferedReader br = new BufferedReader(isr);
                      String temp = "";
                      while ((temp = br.readLine()) != null) {
                          result.append(temp).append("\n");
                      }
                      br.close();
                      isr.close();
                  } else {
                      Log.e("ServerError",con.getResponseCode()+"");
                  }
                  con.disconnect();
              } catch (Exception e){
                  Log.e("Error",e.toString());
              }
              // 데이터에 직접 값 넣기
              data = result.toString();
              // 핸들러로 메세지를 전달해서 함수 실행
              handler.sendEmptyMessage(NetworkBasic.SERVER_CODE);
          }
      }
  }
  ```
