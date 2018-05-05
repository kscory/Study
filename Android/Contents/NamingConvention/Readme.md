# Naming Convention
  - Java Naming Convention
  - Android Resource Naming Convention
    - 출처 : [A successful XML naming convention](https://jeroenmols.com/blog/2016/03/07/resourcenaming/)
  - 참고 : 개발하면서 뒤죽박죽으로 만든 네이밍을 통일시켜보자는 취지에서 작성해봄 (무조건적인 규칙이 아님)
    - 앞으로 혼자 개발할때는 이를 적용할 것!

---

## Java Naming Convention
  ### 1. Package
  - 소문자로 작성

  ```
  java, util, lang, mypackage, viewmodel
  ```

  ### 2. class
  - 대문자로 시작
  - 명사
  - CamelCase 명명 규칙을 따름

  ```
  Button, Color, MainActivity
  ```

  ### 3. interface
  - 대문자로 시작
  - 형용사
  - CamelCase 명명 규칙을 따름

  ```
  Runnable, Remote, ActionListenser
  ```

  ### 4. method
  - 소문자로 시작
  - 동사
  - CamelCase 명명 규칙을 따름

  ```
  actionperformed(), main(), onCreate()
  ```

  ### 5. variable
  - 소문자로 시작
  - 명사
  - 일반적인 변수들은 타입과 변수명을 같게
  - CamelCase 명명 규칙을 따름

  ```java
  // 변수명
  firstName, myNumber
  // 일반적인 변수들
  setCafe(Cafe cafe)
  ```

  ### 6. constant
  - 대문자로 시작 및 모두 대문자로 작성
  - 단어가 바뀔때는 `_` 를 이용

  ```
  RED, MAX_PROPORTION, MY_INFO
  ```

---

## Android Resource Naming Convention
  ### 1. 기본 규칙

  ```
  [what]_[where]_[description]_[size]
  ```

  - `what` : activity, fragment, view ...
  - `where` : main, user, setting ...
  - `description` : title, content, profile ...
  - `size` (optional) : 24dp, small, large ...

  ### 2. Layouts

  ```
  [what]_[where].xml
  ```

  - `what` : activity, fragment, view, item, dialog, layout
    - 참고 : layout 이란 `tag` 로 재사용될때 이용한다.
  - `where` : main, user, setting ...
  - ex> activity_main, view_menu, layout_actionbar_backbutton

  ### 3. Strings

  ```
  [where]_[description]
  ```

  - `where` : main, user, setting, all
    - 참고 : all 이란 여러군데에서 공통적으로 사용될때 이용
  - `description` : title, explanation, name
  - ex> all_done, article_title, main_title

  ### 4. Drawables

  ```
  [where]_[description]_[size]
  ```

  - `where` : main, user, setting, all
  - `description` : placehodler, infoicon
  - `size` : large, small, 24dp
  - ex> articledetail_placeholder, all_infoicon_24dp, all_infoicon_large

  ### 5. IDs

  ```
  [what]_[where]_[description]
  ```

  - `what` : tablayout, button, imageview, textview
  - `where` : main, user, setting, all
  - `description` : placehodler, infoicon
  - ex> tablayout_main, imageview_menu_profile, textview_articledetail_title

  ### 6. Dimensions

  ```
  [what]_[where]_[description]_[size]
  ```

  - `what` : width, height, size, margin, padding, elevation, keyline, textsize
  - `where` : main, user, setting, all, main_toolbar
  - `description` : placehodler, infoicon
  - ex> height_toolbar, size_menu_icon, textsize_medium, width_button_user_edit

---

## 참고
  ### 1. CamelCase
  - 이름이 2가지 단어와 혼합되어 있다면, 두 번째 단어는 대문자로 시작
  - 숫자를 먼저입력하면 안된다.
  - 약어의 경우에도 왠만하면 첫 문자만 대문자로 작성

  ### 2. layout 네이밍 규칙을 좀 더 편리하게?
  - 출처 : [레이아웃 리소스 네이밍 규칙](http://www.kmshack.kr/2013/11/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EB%A0%88%EC%9D%B4%EC%95%84%EC%9B%83-%EB%A6%AC%EC%86%8C%EC%8A%A4-%EB%84%A4%EC%9D%B4%EB%B0%8D-%EA%B7%9C%EC%B9%99/)
  - 네이밍을 약어로
    - activity → a
    - fragment → f
    - dialog → d
    - toast → t
    - item → i
    - view → v
    - widget → w
  - ex> `a_setting.xml`, `f_review.xml`
