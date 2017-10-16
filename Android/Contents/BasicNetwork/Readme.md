# Network & Thread
  - dd

---

## Network 사용 방법
  ### 1. f
  - dd

  > MainActivity.java

  ```java

  ```

---

## AsyncTask 사용 방법
  ### 1. f
  - dd

  > MainActivity.java



---

## 참고 개념

  ### 1. 1번에서 Thread의 여러가지 이용
  - 1. `RunOnUiThread()` 이용 (Activity를 받아서 이용한다=>mainThread를 이용해야 하기 때문)
  - 2. Handler에서 obj를 이용
  - 3. 데이터를 먼저 선언한 후 이용

  > RunOnUiThread 이용

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
                  // 통신이 성공인지 체크
                  if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
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
                  con.disconnect();
              } catch (Exception e){
                  Log.e("Error",e.toString());
              }

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
