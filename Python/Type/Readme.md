# Python Type
  - Number (int, float)
  - String
  - Boolean

---

## Type 기본
  ### 1. 변수의 선언
  - 다른 언어와 달리 변수를 따로 선언하지 않고 사용
  - Number는 정수형(int) 와 소수형(float) 으로 나뉨
  - String은 "" 혹은 '' 을 이용하여 선언 (둘의 차이는 없다)
    - 긴 string을 입력할 경우 따옴표 세개로 감싼다.(""")
  - Boolean은 True, False로 존재 (다른언어와 달리 앞이 대문자 주의)

  ```python
  MONEY = 1000 # int형
  RATE = 0.07 # float 형
  NAME = "Kyung" # string 형
  FAKE = True # boolean 형

  # 긴 string 형
  LONG_STRING = """
  안녕하세요 블라음ㄹ
  ㅁ누리두루
  """
  ```

  ### 2. 참고사항
  -  python의 최근 연산의 결과값은 " _ " 에 저장되어 있다.

## Number

  ### 1. 연산
  - 덧셈(+), 뻴샘(-), 곱셈(\*), 나눗셈(/), 나머지(%), 몫(//), 제곱(\*\*)
  - "="을 활용하여 연산 가능 (+=, -=, \*=, /=)

  ```Python
  # 변수를 사용한 연산 예제
  A = 1 + 3    #덧셈
  B = 9 * 5    #곱셈
  C = 55 / 3   #나눗셈
  D = 100 % 3  #나눗셈 나머지
  E = 200 // 7 #나눗셈 몫
  F = 2 ** 10  #제곱
  print(A, B, C, D, E, F, A+B+C+D+E+F)

  # 결과 : 4 45 18.333333333333332 1 28 1024 1120.3333333333333

  a = 1 + 3
  a *= 5
  a -= 3
  a /= 4

  ```

  ### int()
  - number와 String을 int형으로 변환한다.

  ```Python
  age_string = "54"
  age_int = int(age_string)
  ```

---

## String
  ### 1. 연산
  - +, \* 등 이용 가능 (\\ 를 이용하면 " 혹은 ' 문자를 출력할 수 있다.)
  - Number형과 연산은 할 수 없으니 주의

  ```Python
  prgramming = "Python is " + "very"*3 + "Fun"
  name = 'I\'m Gildong'
  age = 51
  print(name + "내 나이는" , age, "입니다.") # 변수가 다를 경우 주의

  ```

  ### 2. Slice
  - String은 순서가 있는 자료형으로 index가 존재한다.
  - String은 Immutable 하다. (일부분을 수정할 수 없다.)
    - ex> a = Python
    - a[0] = J -> 불가능
  - String[숫자] 로 index에 존재하는 값을 읽는다. ([-1] 인 경우 마지막을 가져옴)
  - ":" 를 통해 sliceing 할 수 있다.

  ```Python
  print("Python"[0:3]) # 3번째 전까지 뽑음 (0,1,2) 출력
  print("Python"[0:-1]) # 마지막 전까지 뽑음
  print("Python"[:]) # 모두 뽑음
  print("Python"[0:6:2]) # : : 인 경우 마지막 숫자만큼 step을 주면서 뽑음
  numbers = "0123456789"
  print(numbers[::2]) # 짝수만 뽑는다.
  ```

  ### 3.String Api 및 함수 (그 외에도 여러가지 존재)
  - len(string) : String의 길이를 구한다
  - format()
  - count() : 특정 단어의 수를 구한다
  - replace("이전","이후") : 이전값을 이후값으로 변경
  - upper(), lower() : string을 모두 대소문자로 변경
  - strip(), lstrip(), rstrip() : 순서대로 문자열 양쪽,왼쪽,오른쪽 에 값이 존재하면 모두 제거
  - partition(), rpartition() : 문자열을 분리하는데 순서대로 앞, 뒤에서부터 찾고 구분자를 포함해서 tuple로 생성
  - split(구분자, 숫자) : 구분자를 찾아 숫자개수 만큼 문자를 분리하여 list로 저장(숫자는 없으면 모두 찾음)
  - find(), rfind() : 특정단어를 순서대로 앞, 뒤에서부터 찾아 index를 반환한다.(없으면 -1 리턴)

  ```Python
  len("python") # 6 리턴

  "{name}씨, {day}년 새해 복 많이 받으세요".format(name="아무개", day="2017")
  "World War 2".count("W") # 2 리턴
  "Jython".replace("J","P") # Python 리턴
  "The Old Man".lower() # the old man 리턴
  "--python--".strip("-") # python 리턴
  "010-1234-5678".partition("-") # ("010", "-", "1234-5678") 리턴
  "010-1234-5678".split("-") # [010, 1234, 5678] 리턴
  "I am a boy".find("am") # 2 리턴
  ```

---

## Boolean
  ### 1. 기본
  - ==, !=, in, not~, <, <=, >, >= 등 존재
  - False인 경우
    - [] : 빈 리스트
    - '' : 빈 문자열,
    - 0 : 숫자0
    - None : NoneFalse 친구들
  - bool() : 어떤값이 참인지 궁금할 때 사용

  > dfdf.java

  ```Python
  print()
  print(bool(1), bool(""), bool('False'), bool(None))
  # True, False, True, False 반환
  ```


---

## 참고
  ### 1. print(value, ..., sep = ' ', end = '\n', file = sys.stdout, flush = False)
  - value를 넣을 수 있으며 "," 를 통해 여러 value를 넣을 수 있다.
  - sep : 이를 통해 여러 value 사이의 값을 지정 가능(dafault 는 space)
  - end : 마지막 value 후에 들어갈 값을 지정할 수 있다. (default는 "\\n")
  - flush : boolean을 이용하여 stream을 flush할지 말지 결정

  ```Python
  name = "홍길동"
  final = "니다."
  print("내 이름은 " + name + " 입", final, sep = "" end="!!")
  # 결과 : 내 이름은 홍길동 입니다.!!

  # string format을 출력하는 두가지 방법
  print("안녕하세요 %s씨, %d년 새해 복 많이 받으세요" %("홍길동", 2018)) # %를 사용
  print("안녕하세요 {name}씨, {day}년 새해 복 많이 받으세요".format(name="아무개", day="2017")) # {} 를 사용
  ```

  ### 2. input("보여줄 값")
  - "" 사이에 넣은 값을 터미널에 보여줄 수 있다.
  - 입력값은 항상 string으로 변환되므로 타입 변환이 필요하다. (int(), float(), str())

  ```Python
  name = input("이름을 입력하세요") # name은 string형
  age = int(input("나이를 입력하세요")) # age는 int형
  height = float(input("키를 입력하세요")) # height는 float형
  ```
