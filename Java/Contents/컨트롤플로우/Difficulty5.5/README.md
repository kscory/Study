# 난이도 5.5 문제
* 그림</br>
![](https://github.com/Lee-KyungSeok/ControlFlowExample/blob/master/Difficulty5.5/difficulty_5.5.PNG)

## __풀이__

1. "라인수(입력값*2-1)" 만큼 반복
2. 라인수가 입력값보다 작은 경우 3~6 번 실행
3. 시작할 때 공백 ("line번호" 개)
4. 왼쪽값(line번호 0 포함) 표시
5. 값 사이 공백("2*(입력값-line번호) - 3" 개)</br>- 개수 : 왼쪽공백(입력값-line번호-1) + 오른쪽공백(입력값-line번호-2)
6. 오른쪽 값 표시
7. 라인수가 입력값보다 크거나 같은 경우 8~11 번 실행
8. 시작할 때 공백 ("2*입력값-라인번호-2" 개)
9. 왼쪽값(line번호 마지막 포함) 표시
10. 값 사이 공백("2*(line번호-입력값) + 1" 개)</br>- 개수 : 왼쪽공백(line번호-입력값-1) + 오른쪽공백(line번호-입력값-1)
11. 오른쪽 값 표시

#### __소스 코드__
``` java
public class Test055 {

	public void run(String mark, int lines) {

		for(int lineIndex =0 ; lineIndex<lines*2-1 ; lineIndex=lineIndex+1) {

			//라인수가 입력값보다 작은 경우 실행(윗부분)
			if(lineIndex<lines) {

				//처음 공백 출력
				for(int outerSpaceIndex=0 ; outerSpaceIndex<lineIndex ; outerSpaceIndex=outerSpaceIndex+1) {
					System.out.print(" ");
				}

				// 처음 mark 찍기
				System.out.print(mark);

				//가운데 공백 출력
				for(int innerSpaceIndex1=0 ; innerSpaceIndex1<2*(lines-lineIndex)-3 ; innerSpaceIndex1=innerSpaceIndex1+1) {
					System.out.print(" ");
				}

				//마지막 mark 찍기
				if(lineIndex==lines-1) {
					System.out.println("");
				} else {
					System.out.println(mark); 					
				}
			}

			//라인수가 입력값보다 크거나 같은 경우 실행(아래부분)
			else {

				//처음 공백 출력
				for(int downOuterSpaceIndex=0 ; downOuterSpaceIndex<2*lines-lineIndex-2 ; downOuterSpaceIndex=downOuterSpaceIndex+1) {
					System.out.print(" ");
				}

				//처음 mark 출력
				System.out.print(mark);

				//가운데 공백 출력1
				for(int downInnerSpaceIndex=0 ; downInnerSpaceIndex<2*(lineIndex-lines)+1 ; downInnerSpaceIndex=downInnerSpaceIndex+1) {
					System.out.print(" ");
				}

				//마지막 mark 출력
				System.out.println(mark);				
			}
		}		
	}
}
```
