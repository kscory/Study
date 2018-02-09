# 파이썬에는 Number(int, float), String, Boolean 타입이 존재

# 1. Number
NUMBER = 5
print((NUMBER * 2 + 5)  * 50 + 1769 - 1990) # 529

# 파이썬에서는 최근 연산의 결과값은 _ 에 저장되어 있다.

# cf>변수 사용 이유
# ex1> 1000 만원을 넣었고 5프로씩 이자를 5년동안 받은 경우
#    - (숫자를 사용)
print(1000 + (1000 * 0.05) + (1000 * 0.05) + (1000 * 0.05) + (1000 * 0.05) + (1000 * 0.05))
#    - (변수를 사용) - 숫자를 바꾸기 용이
RATE = 0.07
print(1000 + (1000 * RATE) + (1000 * RATE) + (1000 * RATE) + (1000 * RATE) + (1000 * RATE))

# 변수를 사용한 연산 예제
A = 1 + 3    #덧셈 (4)
B = 9 * 5    #곱셈 (45)
C = 55 / 3   #나눗셈 
D = 100 % 3  #나눗셈 나머지
E = 200 // 7 #나눗셈 몫
F = 2 ** 10  #제곱
print(A, B, C, D, E, F, A+B+C+D+E+F)

# += 연산자
num1 = 1
num1 = num1 + 3
num2 = 1
num2 +=3
print(num1, num2) # 값이 동일
# =을 활용한 연산자 예제
a = 1 + 3 
a *= 5
a -= 3
a /= 4
print(a)


# 2. String
# "" 혹은 '' 와 함께 쓴다. (둘의 차이는 존재하지 않는다)
print("stirng")

# end값 지정 가능
print("end 지정", end="  ")
print("end 확인")

# \' 를 사용하면 문자가 출력이 된다.
print('I\'m KyungSeok')

# String 연산
print("Py" + "thon")
print("very "*3 + "Fun")
#예제
name = "KyungSeok"
age = 26
phone = "010-1234-3456"
print("내 이름은 " + name + " 입니다.", end="  ")
print("나는", age, "살 입니다.") # 변수가 다를 경우 연산을 할 수 없고 "," 를 통해 변수를 구분한다.
print("내 연락처는 " + phone + "입니다.")

# cf> 변수를 입력받기! var = Input('입력하세요!') 
# num = input("나이를 입력하세요! ")
# print("출력된 값은", num, " 입니다.")

# 입력한 값은 모두 String으로 변환이 되므로 타입 변환이 필요하다 (int(), float(), str())

# String format
print("안녕하세요 %s씨, %d년 새해 복 많이 받으세요" %("홍길동", 2018)) # %를 사용
print("안녕하세요 {name}씨, {day}년 새해 복 많이 받으세요".format(name="아무개", day="2017")) # {} 를 사용

# len 함수 (len(string))
key = "python"
print(len(key))

# String은 순서가 있는 자료형
# Index가 존재
# print("Python"[0])
# string = input("좋아하는 프로그래밍 언어를 입력하세요.")
# print(string[len(string)-1]) # 마지막 글자를 출력
# 혹은 [-1] 로 값을 뽑으면 마지막 글자가 출력

# Slice
print("Python"[0:3]) # 3번째 전까지 뽑음 (0,1,2) 출력
print("Python"[0:-1]) # 마지막 전까지 뽑음
print("Python"[:]) # 모두 뽑음
print("Python"[0:6:2]) # : : 인 경우 마지막 숫자만큼 step을 주면서 뽑음
numbers = "0123456789"
print(numbers[::2]) # 짝수만 뽑는다.
# String은 Immutable 하다. (일부분을 수정할 수 없다.)
# ex> a = Python
# a[0] = J -> 불가능

# 예제
likes1 = ""
likes1 += "치킨"
likes1 += ", 고양이"
likes1 += ", 씨리얼"
print(likes1[0:2], "/", likes1[4:7], "/", likes1[9:12])


# 3. Boolean
## 참 거짓을 확인
## True, False
## <단 파이썬은 대문자를 사용하므로 "true"를 사용하면 에러를 발생

# 연산자
# ==, !=, in, not~, <, <=, >, >= 등 존재
print(1==1, 1!=1, 1 in [2,3,4], "p" in "python", "j" not in "python")
# bool() : 어떤값이 참인지 궁금할 때 사용
## False인 경우
### [] : 빈 리스트, '' : 빈 문자열, 
### 0 : 숫자0, None : NoneFalse 친구들
print(bool(1), bool(""), bool('False'), bool(None)) # True, False, True, False 반환