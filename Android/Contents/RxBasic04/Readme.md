# RxJava Basic4
  - [RxBinding](https://github.com/JakeWharton/RxBinding)
  - RxBinding은 앱을 반응형으로 만들기 위해서 사용한다.

---

## RxBinding 사용
  ### 1. 시작하기
  - Gradle에 RxBinding 및 RxJava 설정

  > app gradle

  ```java
  implementation 'com.jakewharton.rxbinding2:rxbinding-appcompat-v7:2.0.0'
  implementation 'io.reactivex.rxjava2:rxandroid:2.0.1'
  ```

  ### 2. 예시1
  - editText의 textWatcher와 같이 반응형으로 만들 수 있다.
  - Button 클릭시 효과를 반응형으로 줄 수 있다.

  ```java
  // 1. editText에 입력되는 값을 체크해서 실시간으로 Log를 뿌려준다.
  RxTextView.textChangeEvents((EditText)findViewById(R.id.editText))
          .subscribe(ch -> Log.i("RxBinding", "word: " + ch.text()));

  // 2. 버튼을 클릭하면 edditText에 Random 숫자를 입력
  RxView.clicks(findViewById(R.id.button))
          // button 에는 button 타입의 오브젝트가 리턴되는데 이를 문자타입으로 변경
          .map(button -> new Random().nextInt() + "")
          // subscribe 에서는 map 에서 변형된 타입을 받게 된다.
          .subscribe(number -> {
              ((EditText) findViewById(R.id.editText)).setText(number);
          });
  ```

  - 아래와 같이 RxJava를 입력하면 반응형식으로 출력된다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/RxBasic04/picture/ex1.png)

  ### 3. 예시3
  - 로그인 조건을 주어 버튼을 반응형으로 만들어 준다.

  ```java
  // 0. 로그인 체크하기
  Observable<TextViewTextChangeEvent> idEmitter = RxTextView.textChangeEvents(findViewById(R.id.editId));
  Observable<TextViewTextChangeEvent> pwEmitter = RxTextView.textChangeEvents(findViewById(R.id.editPassword));

  // 조건 id가 5자 이상이고, pw가 8자 이상이면 btnSignin의 enable을 true로 아니면 false
  Observable.combineLatest( // combineLatest는 두 인자를 받아 boolean으로 값을 넘겨줄 수 있다.
          idEmitter,
          pwEmitter,
          (id, pw) -> id.text().length() >= 5 && pw.text().length() >= 8
  ).subscribe(
          flag -> findViewById(R.id.btnSignin).setEnabled(flag)
  );  
  ```

  - 아래와 같이 입력조건에 따라 버튼이 활성화 된다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/RxBasic04/picture/ex2.png)

---

## 참고
  ### 1. 예시 3번의 코드를 풀어서 쓰면 아래와 같다

  ```java
  // 원래 예제
  Observable.combineLatest(
          idEmitter,
          pwEmitter,
          (id, pw) -> id.text().length() >= 5 && pw.text().length() >= 8
  ).subscribe(
          flag -> findViewById(R.id.btnSignin).setEnabled(flag)
  );  

  // 풀어서 사용
  Observable.combineLatest(
          idEmitter,
          pwEmitter,
          (TextViewTextChangeEvent id, TextViewTextChangeEvent pw) -> {
              boolean idCheck = id.text().length() >= 5;
              boolean pwCheck = pw.text().length() >= 8;
              if (idCheck && pwCheck)
                  return true;
              else
                  return false;
          }
  ).subscribe(
          flag -> findViewById(R.id.btnSignin).setEnabled(flag)
  );  
  ```

  ### 2. 로그인 이전형식과 비교
  - 아래 코드는 textWatcher를 사용하여 edittext를 반응형으로 만든 것

  ```java
  // 이전
  boolean checkEmail = false;
  boolean checkPassword = false;
  private void enableSignupButton(){
      if(checkEmail && checkPassword && checkRepeat && checkName){
          btnSignup.setEnabled(true);
      }else{
          btnSignup.setEnabled(false);
      }
  }

  private void initView() {
      editEmail = (EditText) findViewById(R.id.editEmail);
      editEmail.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

          }
          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              checkEmail = VerificationUtil.isValidEmail(charSequence.toString());
              enableSignupButton();
          }
          @Override
          public void afterTextChanged(Editable editable) {

          }
      });

      editPassword = (EditText) findViewById(R.id.editPassword);
      editPassword.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

          }

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              checkPassword = VerificationUtil.isValidPassword(charSequence.toString());
              enableSignupButton();
          }

          @Override
          public void afterTextChanged(Editable editable) {

          }
      });
  ```
