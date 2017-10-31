# Firebase Basic 3
  - Firebase 프로젝트 반자동 생성(Authentication)
  - Signup / Signin (Authentication) 기능
  - ㅇㅇ

---

## Firebase 프로젝트 반자동 생성(Authentication) 까지
  ### 1. firebase 프로젝트 생성 순서
  - Tool의 firebase 클릭 -> 오른쪽 탭에서 Authentication + email... 클릭 -> 순서대로 진행 (진행되면 녹색으로 바뀜)

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase3/picture/firbasecreate.png)

  - ① `connect to firebase` : 프로젝트 생성 및 app에 json 파일 넣는것까지 진행됨
  - ② `Authentication to your app` : 클릭시 그래들에 추가됨
  - ③ `Check current auth state` : 전역변수와 onCreate에 인증 state를 추가할 것 (써있는 것_하라는대로 하면 됨)
  - ④ `Sign up new users` : 아래와 같이 메소드를 만들어 Signup 모듈을 붙여넣는다.

  ```java
  public void signup(String email, String password){
      mAuth.createUserWithEmailAndPassword(email, password)
              .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if (task.isSuccessful()) {
                          // Sign in success, update UI with the signed-in user's information
                          Log.d(TAG, "createUserWithEmail:success");
                          FirebaseUser user = mAuth.getCurrentUser();
                          updateUI(user);
                      } else {
                          // If sign in fails, display a message to the user.
                          Log.w(TAG, "createUserWithEmail:failure", task.getException());
                          Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                  Toast.LENGTH_SHORT).show();
                          updateUI(null);
                      }
                  }
              });
  }
  ```

  - ⑤ `Sign in existing users` : Signin 모듈을 붙여 넣는다. (Signup과 동일)

  - ⑥ `Access user information` : 사용자 정보를 가져오기 위한 것 (메소드를 만들어 붙여 넣는다.)

  ```java
  public void getUserInfo(){
      FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
      if (user != null) {
          // Name, email address, and profile photo Url
          String name = user.getDisplayName();
          String email = user.getEmail();
          Uri photoUrl = user.getPhotoUrl();

          // 이메일 검증이 완료되었는지 확인
          boolean emailVerified = user.isEmailVerified();

          // The user's ID, unique to the Firebase project. Do NOT use this value to
          // authenticate with your backend server, if you have one. Use
          // FirebaseUser.getToken() instead.
          String uid = user.getUid();
      }
  }
  ```

  - ⑦ `Optional:Configure ProGuard` : project의 app에 있는 proguard rules에 붙여넣으며 이는 난독하지 말라고 선언함 (ProGuard란 코드를 볼 수 없게끔 난독해주는 것인데 특정 모듈은 난독화시키면 안되기 때문에 선언_ 여기서는 signature랑 annotation을 난독화시키지 않겠다는 것)

  - 그 후 프로젝트가 자동으로 생성되어 있게 되며 프로젝트/Authentication에 들어가서 sign-in method의 status를 바꾼다. (email은 쉽게 설정되지만 나머지는 직접 설정해야함, facebook이 설정하기 가장 까다로움)


---

## Signup / Signin (Authentication) 기능
  ### 1. Layout
  - 보통 Signup / Signin Layout에서는 아래에 아이디/비밀번호를 체크하는 text를 출력한다.
  - Layout 및 결과는 아래와 같다

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicFirebase3/picture/signin.png)

  ### 2. signup 메소드
  - ㅇㅇ

  ```java

  ```

---

## ??? 기능
  ### 1. dd
  - 내용

  ```java

  ```

---

## 참고
