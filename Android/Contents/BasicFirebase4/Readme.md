# Firebase Basic 4
  - noti 기능
  - Firebase Cloud Messaging
  - Retrofit 이용 ([Retrofit](http://square.github.io/retrofit/))
  - [서버 코드(deploy되는 코드 및 중계코드) 및 설명은 여기로 _ Node.js](https://github.com/Lee-KyungSeok/Study/tree/master/Node.js/fcm)

---

## noti 기능
  ### 1. 시작하기
  - [Firebase Basic3](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/BasicFirebase3) 에서 한 것과 비슷하게 Tool > firebase > Cloud Messaging > Set up Firebase Cloud Messaging > 순서대로 진행
  - 그 전에 mainfest 관련이 있는데 이렇게 해도 되지만 [quickstart-android](https://github.com/firebase/quickstart-android/blob/master/messaging/app/src/main/java/com/google/firebase/quickstart/fcm/MyFirebaseMessagingService.java) 에 접속하여 아래의 클래스를 복붙한다.
    - `MyFirebaseInstanceIDService` : 토큰을 리프레시 해주는 클래스
    - `MyFirebaseMessagingService` : 앱이 화면에 떠있을 경우와 아닐 경우의 노티를 주는 클래스

  > MyFirebaseInstanceIDService.java

  ```java
  public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

      private static final String TAG = "IDService";

      // 토큰을 리프레시 해준다.
      @Override
      public void onTokenRefresh() {

          String refreshedToken = FirebaseInstanceId.getInstance().getToken();
          Log.d(TAG, "Refreshed token: " + refreshedToken);

          sendRegistrationToServer(refreshedToken);
      }
      private void sendRegistrationToServer(String token) {
          // TODO: 내 데이터베이스의 사용자 token 값을 여기서 갱신
          // ex> 데이터베이스에서 user id 밑에 token 값이 있을 텐데 이를 갱신시키면 된다.
          // String user_node = "user/사용자_id";
          // user_node.setValue(token);
      }
  }
  ```

  > MyFirebaseMessagingService.java

  ```java
  public class MyFirebaseMessagingService extends FirebaseMessagingService {

      private static final String TAG = "MyFirebaseMsgService";

      // 내 앱이 화면에 현재 떠있으면 noti가 전송되었을 때 이 함수가 호출된다.
      @Override
      public void onMessageReceived(RemoteMessage remoteMessage) {
          Log.d(TAG, "From: " + remoteMessage.getFrom());

          if (remoteMessage.getData().size() > 0) {
              Log.d(TAG, "Message data payload: " + remoteMessage.getData());
              // 여기서 노티피케이션 메시지를 받아서 처리
              sendNotification(remoteMessage.getData().get("type"));
          }
          if (remoteMessage.getNotification() != null) {
              Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
          }
          String type = "";

          Map map = remoteMessage.getData();
          if(map !=null ){
              type = map.get("type") != null ? (String)map.get("type") :"";
          }

          MediaPlayer player;
          switch (type){
              case "one":
                  player = MediaPlayer.create(getBaseContext(),R.raw.doorbell);
                  break;
              default:
                  player = MediaPlayer.create(getBaseContext(),R.raw.welcome);
          }
          player.setLooping(false);
          player.start();
      }

      private void sendNotification(String messageBody) {

          MediaPlayer player = MediaPlayer.create(getBaseContext(),R.raw.doorbell);
          Intent intent = new Intent(this, MainActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                  PendingIntent.FLAG_ONE_SHOT);

          Uri url;
          switch (messageBody){
              case "one":
                  url = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.doorbell);
                  break;
              default:
                  url = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.doorbell);
                  break;
          }
          String channelId = "DEFAULT ChANNEL";
          Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
          File file = new File(url.toString());
          NotificationCompat.Builder notificationBuilder =
                  new NotificationCompat.Builder(this, channelId)
                          .setSmallIcon(R.drawable.ic_launcher_background)
                          .setContentTitle("FCM Message")
                          .setContentText(messageBody)
                          .setAutoCancel(true)
                          .setSound(url)
                          .setContentIntent(pendingIntent);

          NotificationManager notificationManager =
                  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

          notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
      }
  ```

  - `onTokenRefresh()` 메소드는 토큰을 생성하고 사용시키기 위해서 사용하라고 포멧을 준 것이다. (이를 sign-up 메소드에 넣어 핸드폰마다 토큰을 만들어 낼 수 있다.)

  ```java
  public void onTokenRefresh() {
      // 아래의 코드만 sign-up 에 넣어 token 발생시킴
      String refreshedToken = FirebaseInstanceId.getInstance().getToken();
  }
  ```

  ### 2. 브라우저(firebase 프로젝트에서 전송)
  - 아래의 형식대로 하면 핸드폰에 noti가 간다.
  - 토큰은 각 핸드폰마다 고유의 값을 가지므로 이를 확인하여 입력한 후 진행
  - 전부에게 보내는 것은 토큰 없이 serverkey 만 있어도 할 수 있다.
  - 서버가 에뮬레이터는 인식하지 못할 수 있으므로 주의할 것.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase4/picture/token1.png)

  ### 3. 코드 작성
  - sign-up에 아래 코드를 추가하여 토큰과 userid를 데이터베이스에 등록한다.

  ```java
  String refreshedToken = FirebaseInstanceId.getInstance().getToken();
  User data = new User(user.getUid(),refreshedToken,user.getEmail());
  userRef.child(user.getUid()).setValue(data);
  ```

---

## Firebase Cloud Messaging
  ### 1. 메세징 통신(noti)의 구조
  - 메세지 통신 시 해킹의 위험 때문에 Firebase 서버에 직접 접근하지 않고 아래 그림과 같이 중간에 자체적 Server를 만들어서 접근하는 것을 권장한다.
  - Message 토큰과 Serverkey를 함께 보내주어 이를 처리한다.
  - Serverkey만 있다면 앱을 다운받은 모든 사용자에게 noti를 날릴 수 있다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase4/picture/messaging.png)

  ### 2. 시작하기_Firebase Function 이용시
  - Firebase Function을 이용하지 않는다면 Node.js의 서버를 실행시켜 연결
  - 단 Firebase Function을 이용하게 되면 email이나 다른 값으로 Token을 검색할 수 있는 장점이 존재(아니면 Token으로만 검색 가능)

  - ① powershell(terminal) 에 (경로 지정 후) Firebase 명령줄 도구 설치 (이는 firebase “TOOL” 이기 때문에 다시 받을 필요는 없으며 -g 입력시 전체 적용)
  ```
  npm install -g firebase-tools
  ```

  - ② 설치 후 아래 코드를 입력하여 Firebase 로그인 (`firbase -V`의 버전확인 명령어 입력 후 설치완료되었는지 확인 후 진행)
  ```
  firbase login
  ```

  - ③ firebase init 명령어 입력 후 방향키로 function 선택하여 파일 설치
  - 안되는 경우 아래와 같은 명령어 입력하여 파일을 설치한다.
  ```
  firebase init
  혹은 (안될 경우)
  firbase init function
  ```

  - ④ Functions에 있는 index에 (node.js로) 코드를 작성한다. ([코드작성예시](https://github.com/Lee-KyungSeok/Study/tree/master/Node.js/fcm))

  - ⑤ Firebase에 코드를 deploy 한다.
  ```
  firbase deploy
  ```

  - ⑥ Firebase 홈페이지에 들어가 코드가 deploy 되었는지 확인

  - 진행 과정

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase4/picture/start.png)

  ### 3. 실행 예시
  - 브라우저 혹은 postman에서 요청을 전송

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase4/picture/example.png)

---

## [Retrofit](http://square.github.io/retrofit/)을 이용하여 통신
  ### 1. 시작하기
  - Retrofit은 `Thread + httpUrlConnection` 의 형태로 네트워킹을 위한 라이브러리다.
  - Gradle에 아래와 같이 추가

  ```java
  implementation 'com.squareup.retrofit2:retrofit:2.3.0'
  ```

  ### 2. 코드 구성
  - Retrofit을 사용하기 위해서는 인터페이스를 만들어 주어야 한다.
  - 사용시에는 인터페이스와 결합하여 사용

  > IRetro.java

  ```java
  public interface IRetro {
      // @GET("sendNotification?postData=")
      // 리턴타입 함수명(인자)
      @POST("sendNotification")
      Call<ResponseBody> sendNotification(@Body RequestBody postdata);
      // 따로 처리를 하지 않아도 http의 body가 담겨서 넘어간다.(@Body)
  }
  ```

  > StorageActivity의 send 메소드

  ```java
  public void send(View view){
      // 선택한 계정의 token과 작성한 메시지를 가져옴
      String token = textToken.getText().toString();
      String msg = editMsg.getText().toString();

      // 각각이 null인 경우 실행하지 않음
      if(token == null || "".equals(token)){
          Toast.makeText(this,"받는사람을 선택하세요",Toast.LENGTH_SHORT).show();
          return;
      }
      if(msg == null || "".equals(msg)){
          Toast.makeText(this, "메시지를 입력하세요", Toast.LENGTH_SHORT).show();
          return;
      }

      String json = "{\"to\":\""+token+"\", \"msg\":\""+msg+"\"}";
      // Retrofit 선언
      Retrofit retrofit = new Retrofit.Builder()
              .baseUrl("http://192.168.0.63:9000/") // firebase deploy 이용시 url 변경 필요
              .build();
      // 인터페이스와 결합
      IRetro service = retrofit.create(IRetro.class);
      RequestBody body = RequestBody.create(MediaType.parse("plain/text"),json); // firebase deploy 이용시 타입 변경 필요
      // 서비스로 서버 연결준비
      Call<ResponseBody> remote = service.sendNotification(body);
      // 실제 연결후 데이터처리
      remote.enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
              if(response.isSuccessful()){
                  ResponseBody data = response.body();
                  try {
                      Toast.makeText(StorageActivity.this, data.string(), Toast.LENGTH_SHORT).show();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              } else {
                  // 아닐경우 처리해도 됨
              }
          }

          @Override
          public void onFailure(Call<ResponseBody> call, Throwable t) {
              Log.e("Retro",t.getMessage());
          }
      });
  }
  ```

---
