# LoginActivity
  - [Map구조를 이용하여 데이터를 주고받기_코드 링크](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/LoginHttp/LoginHttp)
  - [Json을 이용하여 데이터 주고받기_코드 링크](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/LoginHttp/HttpSignin)

---
## 공통 세팅 부분
  ### 1. 기본 세팅
  - LoginActivity로 프로젝트 생성
  - mainifest에 인터넷 퍼미션 추가
  - string 파일에서 Email을 ID로 변경
  - (Json 형식인 경우) Gradle에 Gson 라이브러리 추가

  > mainifest.xml

  ```xml
  <uses-permission android:name="android.permission.INTERNET"/>
  ```

  > values/strings.xml

  ```xml
  <string name="prompt_email">ID</string>
  ```

  > Gradle의 Dependency

  ```
  compile 'com.google.code.gson:gson:2.8.2'
  ```

---

## Map형태로 데이터 주고받기
  ### 1. LoginActivity (로그인 데이터 입력 엑티비티)
  - 입력한 데이터를 Map 형태로 바꾸어 Remote에 데이터를 전송
  - 결과값을 받아 OK이면 Toast를 띄운다.

  > LoginActivity.java

  ```java
  public class UserLoginTask extends AsyncTask<Void, Void, String> {

      private final String mEmail;
      private final String mPassword;

      UserLoginTask(String email, String password) {
          mEmail = email;
          mPassword = password;
      }

      @Override
      protected String doInBackground(Void... params) {

          Map<String,String> signInfo = new HashMap<>();
          signInfo.put("id",mEmail);
          signInfo.put("pw",mPassword);
          String result = Remote.sendPost("http://192.168.0.6:9000/signin",signInfo);

          return result;
      }

      @Override
      protected void onPostExecute(final String result) {
          mAuthTask = null;
          showProgress(false);
          if ("OK".equals(result)) {
              Toast.makeText(LoginActivity.this, "Welcome!", Toast.LENGTH_LONG).show();
          } else {
              mPasswordView.setError(getString(R.string.error_incorrect_password));
              mPasswordView.requestFocus();
          }
      }

      @Override
      protected void onCancelled() {
          mAuthTask = null;
          showProgress(false);
      }
  }
  ```

  ### 2. 서버와 연동하는 모듈 (Remote 클래스)
  - Map 형태로 받은 데이터를 String (`"id=aaa&pw=dfdfdffdf"`)의 형태로 바꾸어 전송
  - `con.setDoOutput(true);` : 서버통신에서 출력 가능한 상태로 만든다.
  - 그 후 `write`를 사용하여 출력

  > Remote.jaca

  ```java
  public class Remote {
      public static String sendPost(String address, Map data) {
          String result = "";
          try {
              URL url = new URL(address);
              HttpURLConnection con = (HttpURLConnection) url.openConnection();
              con.setRequestMethod("POST");

              // Post data를 전송하는 부분 ===================
              String postdata = "";
              for (Object key : data.keySet()) {
                  // &는 두번째 줄부터 붙이므로 제거해야 한다.
                  postdata += "&" + key + "=" + data.get(key);
              }
              postdata = postdata.substring(1); //제일 앞 & 제거
              con.setDoOutput(true);
              OutputStream os = con.getOutputStream();
              os.write(postdata.getBytes());
              // 스트림은 항상 닫아 주어야 한다.
              os.flush();
              os.close();
              //==============================================

              if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                  InputStreamReader isr = new InputStreamReader(con.getInputStream());
                  BufferedReader br = new BufferedReader(isr);
                  String temp = "";
                  while ((temp = br.readLine()) != null) {
                      result += temp;
                  }
                  br.close();
                  isr.close();
              } else {
                  Log.e("ServerError", con.getResponseCode() + "");
              }
              con.disconnect();

          } catch (MalformedURLException e) {
              e.printStackTrace();
          } catch (IOException e) {
              e.printStackTrace();
          }
          return result;
      }
  }
  ```
---

## Json형태로 데이터 주고받기
  ### 1. Json 형식을 자바 클래스로(model 패키지)
  - Sign 클래스에 id,pw 정의
  - Result 클래스에 결과값 및 msg 정의
  - Result 클래스에 결과가 잘 이루어졌는지 확인하는 메소드 정의

  > Sign.java

  ```java
  public class Sign {
      private String id;
      private String pw;

      public String getId() { return id; }
      public void setId(String id) { this.id = id; }
      public String getPw() { return pw; }
      public void setPw(String pw) { this.pw = pw; }
  }
  ```

  > Result.java

  ```java
  public class Result {
      public static final String OK = "200";
      public static final String FAIL = "300";

      private String code;
      private String msg;

      public String getCode() { return code; }
      public void setCode(String code) { this.code = code; }
      public String getMsg() { return msg; }
      public void setMsg(String msg) { this.msg = msg; }
      // 통신 결과값을 반환해주는 메소드를 작성
      public boolean isOK(){
          return OK.equals(code);
      }
  }
  ```

  ### 2. LoginActivity (로그인 데이터 입력 엑티비티)
  - 입력한 데이터를 Gson을 이용하여 jsonstring 형태로 변환(`toJson`)
  - 결과값을 받아 정의한 Result 클래스의 code가 OK이면 Toast 출력

  > LoginActivity.java

  ```java
  public class UserLoginTask extends AsyncTask<Void, Void, String> {

      private final String mEmail;
      private final String mPassword;

      UserLoginTask(String email, String password) {
          mEmail = email;
          mPassword = password;
      }

      @Override
      protected String doInBackground(Void... params) {

          // json 형식으로 반환
          Sign sign = new Sign();
          sign.setId(mEmail);
          sign.setPw(mPassword);
          // jsonString 변환
          String json = new Gson().toJson(sign);
          String result = Remote.sendPost("http://192.168.0.6:9000/signin",json);
          return result;
      }

      @Override
      protected void onPostExecute(final String result) {
          mAuthTask = null;
          showProgress(false);

          Result rst = new Gson().fromJson(result,Result.class);
          if (rst.isOK()) {
              Toast.makeText(LoginActivity.this, "Welcome!", Toast.LENGTH_LONG).show();
          } else {
              mPasswordView.setError(getString(R.string.error_incorrect_password));
              mPasswordView.requestFocus();
          }
      }

      @Override
      protected void onCancelled() {
          mAuthTask = null;
          showProgress(false);
      }
  ```

  ### 2. 서버와 연동하는 모듈 (Remote 클래스)
  - jsonString의 형태로 데이터를 받아 이를 그대로 서버로 전송
    - 서버에서는 이를 파싱하여 사용한다.
  - 서버에서 결과를 보내주면 이를 LoginActivity로 전송

  > Remote.jaca

  ```java
  public class Remote {
      public static String sendPost(String address, String json){
          String result = "";

          try {
              URL url = new URL(address);
              HttpURLConnection con = (HttpURLConnection) url.openConnection();
              con.setRequestMethod("POST");
              // Post로 데이터 전송 =================
              con.getDoOutput();
              OutputStream os = con.getOutputStream();
              os.write(json.getBytes());
              os.flush();
              os.close();
              //=====================================
              if(con.getResponseCode()==HttpURLConnection.HTTP_OK) {
                  InputStreamReader isr = new InputStreamReader(con.getInputStream());
                  BufferedReader br = new BufferedReader(isr);
                  String temp = "";
                  while ((temp = br.readLine()) != null) {
                      result += temp;
                  }
                  br.close();
                  isr.close();
                  con.disconnect();
              } else {
                  Log.e("ServerError", con.getResponseCode() + "");
              }
          } catch (MalformedURLException e) {
              e.printStackTrace();
          } catch (IOException e) {
              e.printStackTrace();
          }
          return result;
      }
  }
  ```

---

## (node.js)서버와의 통신 결과
- [참고_login server 구축](https://github.com/Lee-KyungSeok/Study/tree/master/Node.js/server_db_basic)

![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/LoginHttp/picture/result.png)

---

## LoginActivty의 모든 코드
  ### 1. Map형태로 데이터 주고받기

  > LgoinActivity.java

  ```java
  /**
   * A login screen that offers login via email/password.
   */
  public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

      private static final int REQUEST_READ_CONTACTS = 0;


      private static final String[] DUMMY_CREDENTIALS = new String[]{
              "foo@example.com:hello", "bar@example.com:world"
      };
      /**
       * Keep track of the login task to ensure we can cancel it if requested.
       */
      private UserLoginTask mAuthTask = null;

      // UI references.
      private AutoCompleteTextView mEmailView;
      private EditText mPasswordView;
      private View mProgressView;
      private View mLoginFormView;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_login);
          // Set up the login form.
          mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
          populateAutoComplete();

          mPasswordView = (EditText) findViewById(R.id.password);
          mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
              @Override
              public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                  if (id == R.id.login || id == EditorInfo.IME_NULL) {
                      attemptLogin();
                      return true;
                  }
                  return false;
              }
          });

          Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
          mEmailSignInButton.setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View view) {
                  attemptLogin();
              }
          });

          mLoginFormView = findViewById(R.id.login_form);
          mProgressView = findViewById(R.id.login_progress);
      }

      private void populateAutoComplete() {
          if (!mayRequestContacts()) {
              return;
          }

          getLoaderManager().initLoader(0, null, this);
      }

      private boolean mayRequestContacts() {
          if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
              return true;
          }
          if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
              return true;
          }
          if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
              Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                      .setAction(android.R.string.ok, new View.OnClickListener() {
                          @Override
                          @TargetApi(Build.VERSION_CODES.M)
                          public void onClick(View v) {
                              requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                          }
                      });
          } else {
              requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
          }
          return false;
      }

      /**
       * Callback received when a permissions request has been completed.
       */
      @Override
      public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults) {
          if (requestCode == REQUEST_READ_CONTACTS) {
              if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                  populateAutoComplete();
              }
          }
      }

      private void attemptLogin() {
          if (mAuthTask != null) {
              return;
          }

          // 에러 리셋
          mEmailView.setError(null);
          mPasswordView.setError(null);

          // 시도한 로그인 정보 저장
          String email = mEmailView.getText().toString();
          String password = mPasswordView.getText().toString();

          boolean cancel = false;
          View focusView = null;

          // 비밀번호 체크
          if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
              mPasswordView.setError(getString(R.string.error_invalid_password));
              focusView = mPasswordView;
              cancel = true;
          }

          // 이메일 체크(아이디 체크)
          if (TextUtils.isEmpty(email)) {
              mEmailView.setError(getString(R.string.error_field_required));
              focusView = mEmailView;
              cancel = true;
          }
          /* 이메일 형식 체크 => 사용하지 않음
          else if (!isEmailValid(email)) {
              mEmailView.setError(getString(R.string.error_invalid_email));
              focusView = mEmailView;
              cancel = true;
          }
          */

          if (cancel) {
              focusView.requestFocus();
          } else {
              // 정상인 경우 이메일 프로세스 실행
              showProgress(true);
              mAuthTask = new UserLoginTask(email, password);
              mAuthTask.execute((Void) null);
          }
      }
      // 이메일 형식(@ 있는지 체크)
      private boolean isEmailValid(String email) {
          //TODO: Replace this with your own logic
          return email.contains("@");
      }
      // 비밀번호가 4자리 이상인지 체크
      private boolean isPasswordValid(String password) {
          //TODO: Replace this with your own logic
          return password.length() > 4;
      }

      /**
       * Shows the progress UI and hides the login form.
       */
      @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
      private void showProgress(final boolean show) {
          // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
          // for very easy animations. If available, use these APIs to fade-in
          // the progress spinner.
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
              int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

              mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
              mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                      show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                  @Override
                  public void onAnimationEnd(Animator animation) {
                      mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                  }
              });

              mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
              mProgressView.animate().setDuration(shortAnimTime).alpha(
                      show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                  @Override
                  public void onAnimationEnd(Animator animation) {
                      mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                  }
              });
          } else {
              // The ViewPropertyAnimator APIs are not available, so simply show
              // and hide the relevant UI components.
              mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
              mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
          }
      }

      @Override
      public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
          return new CursorLoader(this,
                  // Retrieve data rows for the device user's 'profile' contact.
                  Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                          ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                  // Select only email addresses.
                  ContactsContract.Contacts.Data.MIMETYPE +
                          " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                  .CONTENT_ITEM_TYPE},

                  // Show primary email addresses first. Note that there won't be
                  // a primary email address if the user hasn't specified one.
                  ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
      }

      @Override
      public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
          List<String> emails = new ArrayList<>();
          cursor.moveToFirst();
          while (!cursor.isAfterLast()) {
              emails.add(cursor.getString(ProfileQuery.ADDRESS));
              cursor.moveToNext();
          }

          addEmailsToAutoComplete(emails);
      }

      @Override
      public void onLoaderReset(Loader<Cursor> cursorLoader) {

      }

      private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
          //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
          ArrayAdapter<String> adapter =
                  new ArrayAdapter<>(LoginActivity.this,
                          android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

          mEmailView.setAdapter(adapter);
      }


      private interface ProfileQuery {
          String[] PROJECTION = {
                  ContactsContract.CommonDataKinds.Email.ADDRESS,
                  ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
          };

          int ADDRESS = 0;
          int IS_PRIMARY = 1;
      }

      /**
       * Represents an asynchronous login/registration task used to authenticate
       * the user.
       */
      public class UserLoginTask extends AsyncTask<Void, Void, String> {

          private final String mEmail;
          private final String mPassword;

          UserLoginTask(String email, String password) {
              mEmail = email;
              mPassword = password;
          }

          @Override
          protected String doInBackground(Void... params) {
              /* try {
                  // progressBar를 보여주기 위해 2초씩 쉼
                  Thread.sleep(2000);
              } catch (InterruptedException e) {
                  return false;
              } */

              Map<String,String> signInfo = new HashMap<>();
              signInfo.put("id",mEmail);
              signInfo.put("pw",mPassword);
              String result = Remote.sendPost("http://192.168.0.6:9000/signin",signInfo);

              return result;
          }

          @Override
          protected void onPostExecute(final String result) {
              mAuthTask = null;
              showProgress(false);
              if ("OK".equals(result)) {
                  Toast.makeText(LoginActivity.this, "Welcome!", Toast.LENGTH_LONG).show();
              } else {
                  mPasswordView.setError(getString(R.string.error_incorrect_password));
                  mPasswordView.requestFocus();
              }
          }

          @Override
          protected void onCancelled() {
              mAuthTask = null;
              showProgress(false);
          }
      }
  }
  ```

  ### 2. Json형태로 데이터 주고받기

  ```java
  public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

      private static final int REQUEST_READ_CONTACTS = 0;


      private static final String[] DUMMY_CREDENTIALS = new String[]{
              "foo@example.com:hello", "bar@example.com:world"
      };
      /**
       * Keep track of the login task to ensure we can cancel it if requested.
       */
      private UserLoginTask mAuthTask = null;

      // UI references.
      private AutoCompleteTextView mEmailView;
      private EditText mPasswordView;
      private View mProgressView;
      private View mLoginFormView;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_login);
          // Set up the login form.
          mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
          populateAutoComplete();

          mPasswordView = (EditText) findViewById(R.id.password);
          mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
              @Override
              public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                  if (id == R.id.login || id == EditorInfo.IME_NULL) {
                      attemptLogin();
                      return true;
                  }
                  return false;
              }
          });

          Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
          mEmailSignInButton.setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View view) {
                  attemptLogin();
              }
          });

          mLoginFormView = findViewById(R.id.login_form);
          mProgressView = findViewById(R.id.login_progress);
      }

      private void populateAutoComplete() {
          if (!mayRequestContacts()) {
              return;
          }

          getLoaderManager().initLoader(0, null, this);
      }

      private boolean mayRequestContacts() {
          if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
              return true;
          }
          if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
              return true;
          }
          if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
              Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                      .setAction(android.R.string.ok, new View.OnClickListener() {
                          @Override
                          @TargetApi(Build.VERSION_CODES.M)
                          public void onClick(View v) {
                              requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                          }
                      });
          } else {
              requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
          }
          return false;
      }

      /**
       * Callback received when a permissions request has been completed.
       */
      @Override
      public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults) {
          if (requestCode == REQUEST_READ_CONTACTS) {
              if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                  populateAutoComplete();
              }
          }
      }
      // 실제 로그인 처리
      private void attemptLogin() {
          if (mAuthTask != null) {
              return;
          }

          // 에러 리셋
          mEmailView.setError(null);
          mPasswordView.setError(null);

          // 시도한 로그인 정보 저장
          String email = mEmailView.getText().toString();
          String password = mPasswordView.getText().toString();

          boolean cancel = false;
          View focusView = null;

          // 비밀번호 체크
          if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
              mPasswordView.setError(getString(R.string.error_invalid_password));
              focusView = mPasswordView;
              cancel = true;
          }

          // 이메일 체크(아이디 체크)
          if (TextUtils.isEmpty(email)) {
              mEmailView.setError(getString(R.string.error_field_required));
              focusView = mEmailView;
              cancel = true;
          }
          /* 이메일 형식 체크 => 사용하지 않음
          else if (!isEmailValid(email)) {
              mEmailView.setError(getString(R.string.error_invalid_email));
              focusView = mEmailView;
              cancel = true;
          }
          */

          if (cancel) {
              focusView.requestFocus();
          } else {
              // 정상인 경우 이메일 프로세스 실행
              showProgress(true);
              mAuthTask = new UserLoginTask(email, password);
              mAuthTask.execute((Void) null);
          }
      }
      // 이메일 형식(@ 있는지 체크)
      private boolean isEmailValid(String email) {
          //TODO: Replace this with your own logic
          return email.contains("@");
      }
      // 비밀번호가 4자리 이상인지 체크
      private boolean isPasswordValid(String password) {
          //TODO: Replace this with your own logic
          return password.length() > 4;
      }

      /**
       * Shows the progress UI and hides the login form.
       */
      @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
      private void showProgress(final boolean show) {
          // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
          // for very easy animations. If available, use these APIs to fade-in
          // the progress spinner.
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
              int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

              mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
              mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                      show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                  @Override
                  public void onAnimationEnd(Animator animation) {
                      mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                  }
              });

              mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
              mProgressView.animate().setDuration(shortAnimTime).alpha(
                      show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                  @Override
                  public void onAnimationEnd(Animator animation) {
                      mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                  }
              });
          } else {
              // The ViewPropertyAnimator APIs are not available, so simply show
              // and hide the relevant UI components.
              mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
              mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
          }
      }

      @Override
      public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
          return new CursorLoader(this,
                  // Retrieve data rows for the device user's 'profile' contact.
                  Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                          ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                  // Select only email addresses.
                  ContactsContract.Contacts.Data.MIMETYPE +
                          " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                  .CONTENT_ITEM_TYPE},

                  // Show primary email addresses first. Note that there won't be
                  // a primary email address if the user hasn't specified one.
                  ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
      }

      @Override
      public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
          List<String> emails = new ArrayList<>();
          cursor.moveToFirst();
          while (!cursor.isAfterLast()) {
              emails.add(cursor.getString(ProfileQuery.ADDRESS));
              cursor.moveToNext();
          }

          addEmailsToAutoComplete(emails);
      }

      @Override
      public void onLoaderReset(Loader<Cursor> cursorLoader) {

      }

      private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
          //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
          ArrayAdapter<String> adapter =
                  new ArrayAdapter<>(LoginActivity.this,
                          android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

          mEmailView.setAdapter(adapter);
      }


      private interface ProfileQuery {
          String[] PROJECTION = {
                  ContactsContract.CommonDataKinds.Email.ADDRESS,
                  ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
          };

          int ADDRESS = 0;
          int IS_PRIMARY = 1;
      }

      /**
       * Represents an asynchronous login/registration task used to authenticate
       * the user.
       */
      public class UserLoginTask extends AsyncTask<Void, Void, String> {

          private final String mEmail;
          private final String mPassword;

          UserLoginTask(String email, String password) {
              mEmail = email;
              mPassword = password;
          }

          @Override
          protected String doInBackground(Void... params) {
              /* try {
                  // progressBar를 보여주기 위해 2초씩 쉼
                  Thread.sleep(2000);
              } catch (InterruptedException e) {
                  return false;
              } */

              // json 형식으로 반환
              Sign sign = new Sign();
              sign.setId(mEmail);
              sign.setPw(mPassword);
              // jsonString 변환
              String json = new Gson().toJson(sign);
              String result = Remote.sendPost("http://192.168.0.6:9000/signin",json);
              return result;
          }

          @Override
          protected void onPostExecute(final String result) {
              mAuthTask = null;
              showProgress(false);

              Result rst = new Gson().fromJson(result,Result.class);
              if (rst.isOK()) {
                  Toast.makeText(LoginActivity.this, "Welcome!", Toast.LENGTH_LONG).show();
              } else {
                  mPasswordView.setError(getString(R.string.error_incorrect_password));
                  mPasswordView.requestFocus();
              }
          }

          @Override
          protected void onCancelled() {
              mAuthTask = null;
              showProgress(false);
          }
      }
  }  
  ```
