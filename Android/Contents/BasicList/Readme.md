# BasicList
- ListView와 Adapter 이용한 list의 구현

---

## 개요
- ListView, Adapter, Data Set, Android(System), Holder 관계 및
</br> ListView가 만들어지는 과정

#### 1. View가 null 이라면 View를 생성해준다.
- View가 만들어지지 않았다면 View는 null로 존재하므로 View를 생성해준다.
- 이때, View가 생성될 시 Holder도 함께 생성하고, 이를 이용하여 View를 재사용하기 위해 Holder에 View들을 담게된다.(Tag를 달아서, Android(System)은 Holder의 존재를 모르고 있다.)
- View를 생성한 후 dataset에서 data를 가져온다.

![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicList/picture/listview1.png)

#### 2. 화면에 보여지는 만큼만 View를 생성한다.
- View들은 Android(System)에 저장되어 있다는 것을 생각.

![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicList/picture/listview2.png)

#### 3. 스크롤을 내리게 되면 새로운 data를 보여주기 위해 getView를 실행한다.
- 이 때 View는 이미 생성되어 있는, 화면에서 사라진 View를 넘겨준다.

![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicList/picture/listview3.png)

#### 4. View를 받아서 Holder에 저장된 위젯의 데이터를 바꾼다.
- 만약 Holder를 사용하지 않는다면 위젯들을 새로 new 해주어야 하므로 효율성이 떨어진다.
![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicList/picture/listview4.png)

#### 5. listView 에 바뀐 View를 적용한다.

![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/BasicList/picture/listview5.png)

---

## 버튼 디자인
1. resource/drawble 디렉토리에 <shape> 라벨의 xml 생성
2. Layout에서 background에 ""@drawable/xml파일 이름" 적용
#### ※ [버튼 디자인 참고 사이트](http://angrytools.com/android/button/)

### 1. __dresource/drawble 디렉토리에 xml 생성__
> 예시

```xml

```
### 2. __Layout에서 적용__


## 소스 링크
1.
