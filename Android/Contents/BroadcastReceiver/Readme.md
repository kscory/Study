# BroadcastReceiver & Notification
  - BroadcastReceiver 사용법
  - Notification 사용법

---

## BroadcastReceiver
  ### 1. BroadcastReceiver 란
  - 안드로이드 단말기에서 발생하는 다양한 이벤트/정보를 받고 반응하는 컴포넌트
  - 핸드폰의 키고 꺼짐, 배터리, 문자/전화, 네트워크, GPS 등을 감지
    - ex1> SMS 수신 : `android.provider.Telephony.SMS_RECEIVED`
    - ex2> GPS 변화 : `android.location.PROVIDERS_CHANGED`
    - ex3> 네트워크 변화 : `android.net.conn.BACKGROUND_DATA_SETTING_CHANGED`
  - runtime 권한이 필요한 sms 등은 Permission을 승인받아야 이용 가능

  ### 2. 정적으로 BroadcastReceiver 사용 방법 (SMS)
  -  Manifest 에 receiver 및 권한 설정

  > Manifest.xml

  ```xml
  <uses-permission android:name="android.permission.READ_SMS"/>
  <uses-permission android:name="android.permission.RECEIVE_SMS"/>

  <receiver
      android:name=".SMSReceiver"
      android:enabled="true"
      android:exported="true">
      <intent-filter>
          <action android:name="android.provider.Telephony.SMS_RECEIVED"/>
      </intent-filter>
  </receiver>
  ```

  - 컴포넌트 단위로 BroadcastReceiver를 상속받아 receiver 를 통해 넘어온 브로드캐스트 메시지를 받아서 처리 후 전달

  > SMSReceiver.java

  ```java
  public class SMSReceiver extends BroadcastReceiver {
      // 등록된 receiver 를 통해 브로드캐스트 메시지가 intent에 담겨 넘어온다.
      @Override
      public void onReceive(Context context, Intent intent) {
          // 로직 실행
          // manifest에 등록한 action인지 확인
          if("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
              Bundle bundle = intent.getExtras();
              Object msgs[] = (Object[]) bundle.get("pdus");

              SmsMessage smsMessage[] = new SmsMessage[msgs.length];
              for (int i = 0; i < msgs.length; i++) {
                  smsMessage[i] = SmsMessage.createFromPdu((byte[]) msgs[i]);
                  String msg = smsMessage[i].getMessageBody().toString();
                  Log.i("Broadcast", "SMS=" + msg);
              }
          }
      }
  }
  ```

  ### 2. 동적으로 BroadcastReceiver 사용 방법 -1
  - BroadcastReceiver를 상속받아 로직을 작성

  > SMSReceiver.java

  ```java
  public class SMSReceiver extends BroadcastReceiver {

      private TextView textView;

      public SMSReceiver(TextView textView){
          this.textView = textView;
      }

      // 등록된 receiver 를 통해 브로드캐스트 메시지가 intent에 담겨 넘어온다.
      @Override
      public void onReceive(Context context, Intent intent) {

          // manifest에 등록한 action인지 확인
          if("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
              Bundle bundle = intent.getExtras();
              Object msgs[] = (Object[]) bundle.get("pdus");

              SmsMessage smsMessage[] = new SmsMessage[msgs.length];
              for (int i = 0; i < msgs.length; i++) {
                  smsMessage[i] = SmsMessage.createFromPdu((byte[]) msgs[i]);
                  String msg = smsMessage[i].getMessageBody().toString();
                  Log.i("Broadcast", "SMS=" + msg);

                  /* (형식)
                  [은행] 인증번호를 입력해주세요
                  인증번호 : 8765
                   */
                  if(msg.startsWith("[은행]")){
                      String verification_number =  msg.split(" : ")[1];
                      textView.setText(verification_number);
                  }
              }
          }
      }
  }
  ```

  - Activity에서 recevier를 등록 및 해제

  > MainActivity.java

  ```java
  private void setSMSReceiver(){
      sMSReceiver = new SMSReceiver(textSMS);
      sMSIntentFilter = new IntentFilter();
      sMSIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
  }

  @Override
  protected void onStart() {
      super.onStart();
      // 리시버를 등록
      registerReceiver(sMSReceiver, sMSIntentFilter);
  }

  @Override
  protected void onStop() {
      super.onStop();
      // 리시버를 해제
      unregisterReceiver(sMSReceiver);
  }
  ```

  ### 2. 동적으로 BroadcastReceiver 사용 방법 -2 (네트워크 상태 확인)
  - mainifest에 네트워크 상태변화 퍼미션 추가 - `android.permission.ACCESS_NETWORK_STATE`
  - 코드상에서 BroadcastReceiver를 new 하여 생성

  ```java
  public class NetworkReceiverActivity extends AppCompatActivity {

      BroadcastReceiver networkBroadcast;
      IntentFilter intentFilter;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_network_receiver);

          setNetworkBrodcastReceiver();
          intentFilter = new IntentFilter();
          intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
      }

      private void setNetworkBrodcastReceiver(){
          networkBroadcast = new BroadcastReceiver() {
              @Override
              public void onReceive(Context context, Intent intent) {
                  if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
                      ConnectivityManager connectivityManager
                              = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                      NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
                      NetworkInfo _wifi_network =
                              connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                      if(_wifi_network != null) {
                          // wifi, 데이터 둘 중 하나라도 있을 경우
                          if(_wifi_network != null && activeNetInfo != null){
                              // 로직 수행
                          }
                          // wifi, 데이터 둘 다 없을 경우
                          else{
                              // 로직 수행
                          }
                      }
                  }
              }
          };
      }

      @Override
      protected void onStart() {
          super.onStart();
          // 리시버 등록
          registerReceiver(networkBroadcast, intentFilter);
      }

      @Override
      protected void onStop() {
          super.onStop();
          // 리시버 해제
          unregisterReceiver(networkBroadcast);
      }
  }
  ```
