# Recycler View
- 사용법 (순서)
  - 화면 만들기
  - 데이터를 정의
  - 아답터를 재정의
  - 재정의한 아답터를 생성하면서 데이터를 담는다.
  - 아답터와 RecyclerView 컨테이너를 연결
  - RecyclerView에 레이아웃매니저를 설정
- 참고 : [ListView](https://github.com/Lee-KyungSeok/Study/tree/master/Android/Contents/BasicList)

---
## 0. 화면 만들기
- Recyvler View를 이용하기 위한 Layout

  ### 1. RecyclerView를 library에 추가
  - `tools:listitem="@layout/item_list"`를 이용해 listitem 표시

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/RecyclerViewExample/picture/recycle1.png)

  ### 2. CardView를 library에 추가
  - CardView는 List가 출력되는데 리스트가 오른쪽에서 순서대로 밀려 들어오는 것과 같은데서 사용하기 좋다.
  - CardView 안에 text를 넣어 list를 꾸며줄 수 있다.

  ![](https://github.com/Lee-KyungSeok/Study/blob/master/Android/Contents/RecyclerViewExample/picture/recycle1.png)

---

## Recycler View Code
- RecyclerView 위젯은 ListView의 더욱 향상되고 유연해진 버전으로 한정된 수의 뷰를 유지함으로써 효율적으로 스크롤할 수 있는 큰 데이터 집합을 표시하기 위한 컨테이너
- 사용 순서


  ### 1.
  - dd

  ```java

  ```

---

## 참고 자료
#### 1. [목록 및 카드 생성](https://developer.android.com/training/material/lists-cards.html)

#### 2. [RecyclerView](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html)
