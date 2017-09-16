# BasicWidget
Toggle / CheckBox / RadioButton / ProgressBar / Switch / SeekBar / RatingBar / Spinner


## 1. ToggleButton
#### 1. 위젯변수를 선언

```java
ToggleButton toggleButton;
```

#### 2. 위젯변수를 화면과 연결

```java
 toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
```

#### 3. 리스너 / 메소드 설정
- 리스너 : OnCehckedChangedListener
- 메소드 : onCehcedChanged
 </br> - check는 toggleButton이 클릭되었는지 확인

```java
CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
      if(check){
          textResult.setText("토글버튼이 켜졌습니다");
      }else{
          textResult.setText("토글버튼이 꺼졌습니다");
      }
    }
};
```

#### 4. 리스너 연결
```java
toggleButton.setOnCheckedChangeListener(checkedChangeListener);
```
---

## 2. CheckBox
#### 1. 위젯변수를 선언

```java
CheckBox checkDog, checkPig;
```

#### 2. 위젯변수를 화면과 연결

```java
checkDog = (CheckBox) findViewById(R.id.checkDog);
checkPig = (CheckBox) findViewById(R.id.checkPig);
checkCow = (CheckBox) findViewById(R.id.checkCow);
```

#### 3. 리스너 연결 / 메소드 설정
- 리스너 : OnCehckedChangedListener
- 메소드 : onCehcedChanged
 </br> - check는 checkBox가 클릭되었는지 확인
- 체크박스의 경우 전체 경우를 하나의 배열로 지정해서 넣는 경우가 많다.

```java
ArrayList<String> checkedList = new ArrayList<>();
CompoundButton.OnCheckedChangeListener checkboxListner = new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
        // 체크박스 처리
        switch(compoundButton.getId()){
            case R.id.checkDog:
                    checkedList.add("개");
                    if(check){
                }else{
                    checkedList.remove("개");
                }
                break;
            case R.id.checkPig:
                if(check){
                    checkedList.add("돼지");
                }else{
                    checkedList.remove("돼지");
                }
                break;
        }
        String checkedResult = "";
        for(String item : checkedList){
            checkedResult += item + " ";
        }
        textResult.setText(checkedResult + "(이)가 체크되었습니다.");
    }
};
```

#### 4. 리스너 연결

```java
checkDog.setOnCheckedChangeListener(checkboxListner);
checkPig.setOnCheckedChangeListener(checkboxListner);
checkCow.setOnCheckedChangeListener(checkboxListner);
```

---
## 3. RadioGroup (RadioButton)
RadioButton의 경우 Group을 정하여 그 안에 Button을 설정한다.
#### 1. 위젯변수를 선언

```java
private RadioGroup radioGroup;
private RadioButton radioRed,  radioGreen, radioBlue;
```

#### 2. 위젯변수를 화면과 연결

```java
radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
radioRed = (RadioButton) findViewById(R.id.radioRed);
radioGreen = (RadioButton) findViewById(R.id.radioGreen);
radioBlue = (RadioButton) findViewById(R.id.radioBlue);
```

#### 3. 리스너 / 메소드 설정
- 리스너 : OnCehckedChangedListener
- 메소드 : onCehcedChanged
 </br> - radio id는 RadioButton의 id

```java
RadioGroup.OnCheckedChangeListener radioListener = new RadioGroup.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int radio_id) {
        switch(radio_id){
            case R.id.radioRed:
                textResult.setText("빨간불이 켜졌습니다.");
                break;
            case R.id.radioGreen:
                textResult.setText("녹색불이 켜졌습니다.");
                break;
            case R.id.radioBlue:
                textResult.setText("파란불이 켜졌습니다.");
                break;
        }
    }
};
```

#### 4. 리스너 연결
```java
radioGroup.setOnCheckedChangeListener(radioListener);
```

---
## 4. ProgressBar & Switch
Switch를 이용해 ProgressBar를 보였다 사라졌다 하게 한다.
### 가. ProgressBar
#### 1. 위젯변수를 선언

```java
ProgressBar progressBar
```

#### 2. 위젯변수를 화면과 연결

```java
progressBar = (ProgressBar) findViewById(R.id.progressBar);
```

#### 3. ProgressBar 상태 설정
- INVISIBLE -- 화면에 안보이는데 자리는 차지하고 있다
- VISIBLE   -- 현재 화면에 보이는 상태
- GONE      -- 화면에서 사라진 상태

```java
progressBar.setVisibility(View.INVISIBLE); //VISIBLE, GONE도 가능
```
### 나. Switch
#### 1. 위젯변수를 선언

```java
Switch mSwitch
```

#### 2. 위젯변수를 화면과 연결

```java
mSwitch = (Switch) findViewById(R.id.mSwitch);
```

#### 3. 리스너 / 메소드 설정
- 리스너 : OnCehckedChangedListener
- 메소드 : onCehcedChanged
 </br> - check는 switch가 클릭되었는지 확인

```java
CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
      if(check){
          progressBar.setVisibility(View.VISIBLE);
      }else{
          progressBar.setVisibility(View.INVISIBLE);
      }
    }
};
```

#### 4. 리스너 연결
```java
mSwitch.setOnCheckedChangeListener(checkedChangeListener);
```

---
## 5. SeekBar
#### 1. 위젯변수를 선언

```java
SeekBar seekBar;
```

#### 2. 위젯변수를 화면과 연결

```java
seekBar = (SeekBar) findViewById(R.id.seekBar);
```

#### 3. 리스너 / 메소드 설정 및 리스너 연결
onCreate 메소드 안에서 한번에 함
- 리스너 : setOnSeekBarChangeListener
- 메소드 : onProgressChanged
 </br> - progress는 seekBar의 위치

```java
seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
      // textSeekBarResult는 textView
        textSeekBarResult.setText(progress+"");
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
});
```

---
## 6. RatingBar
#### 1. 위젯변수를 선언

```java
RatingBar ratingBar;
```

#### 2. 위젯변수를 화면과 연결

```java
ratingBar = (RatingBar) findViewById(R.id.ratingBar);
```

#### 3. 리스너 / 메소드 설정 및 리스너 연결
onCreate 메소드 안에서 한번에 함
- 리스너 : setOnSeekBarChangeListener
- 메소드 : onProgressChanged
</br> - progress는 seekBar의 위치
</br> - setStepSize : 단위 설정 가능

```java
ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        ratingBar.setRating(rating);
        ratingBar.setStepSize(0.2F);
        textRatingBarResult.setText(rating+"");
    }
});
```
---

## 7. Spinner
#### 1. 위젯변수를 선언

```java
Spinner spinner;
```

#### 2. 스피너에 입력될 데이터 정의
```java
String data[] = {"월", "화", "수", "목", "금", "토", "일"};
```

#### 3. 스피너와 데이터를 연결하는 아답터를 정의

```java
ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, data);
```

#### 4. 아답터와 스피너(리스트)를 연결

```java
spinner = (Spinner) findViewById(R.id.spinner);
spinner.setAdapter(adapter);
```

#### 5. 스피너에 리스너 연결
- 리스너 : setOnItemSelectedListener
- 메소드 : onItemSelected
```java
spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    // position : spinner의 인덱스로 data의 인덱스와 동일
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedValue = data[position];
        textView.setText(selectedValue);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
});
```
---


## Spinner 참고 사항
- spinner의 경우 아답터를 이용하여 data와 spinner의 독립성을 보장한다.
- 일종의 MVC / MVP 패턴으로 인식 가능
- adapter 이용 시 데이터를 가공하여 보여줄 수 있다.
![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicWidget/picture/spinner.png)
