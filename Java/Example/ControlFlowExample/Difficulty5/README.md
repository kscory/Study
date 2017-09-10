# 난이도 5 문제
* 그림</br>
![](https://github.com/Lee-KyungSeok/ControlFlowExample/blob/master/Difficulty5/difficulty_5.PNG)

## __풀이__

1. "라인수(입력값*2-1)" 만큼 반복
2. 라인수가 입력값보다 작은 경우 3~6 번 실행
3. 시작할 때 공백 ("입력값-line번호-1" 개)
4. 왼쪽(line번호 0 포함)값 표시
5. 값 사이 공백("2*line번호 - 1" 개)
6. 오른쪽 값 표시
7. 라인수가 입력값보다 크거나 같은 경우 6~7 번 실행
8. 시작할 때 공백 ("line번호-입력값+1" 개)
9. 왼쪽(line번호 마지막 포함)값 표시
10. 값 사이 공백("4\*입력값-2\*line번호-5" 개)</br>- 개수 : 2(line번호)+(-5-4(line번호-입력값))
11. 오른쪽 값 표시

#### __소스 코드__
``` java
public class Test05 {

	public void run(String mark, int lines) {

		for(int lineIndex =0 ; lineIndex<lines*2-1 ; lineIndex=lineIndex+1) {

			//라인수가 입력값보다 작은 경우 실행
			if(lineIndex<lines) {

				//처음 공백 출력
				for(int outerSpaceIndex=0 ; outerSpaceIndex<lines-lineIndex-1 ; outerSpaceIndex=outerSpaceIndex+1) {
					System.out.print(" ");
				}

				// 왼쪽 mark 찍기
				System.out.print(mark);

				//가운데 공백 출력하기
				for(int innerSpaceIndex=0 ; innerSpaceIndex<lineIndex*2-1 ; innerSpaceIndex=innerSpaceIndex+1) {
					System.out.print(" ");
				}

				//오른쪽 mark 찍기
				if(lineIndex==0) {
					System.out.println("");
				} else {
					System.out.println(mark); 					
				}
			}

			//라인수가 입력값보다 크거나 같은 경우 실행
			else {

				//처음 공백 출력
				for(int downOuterSpaceIndex=0 ; downOuterSpaceIndex<lineIndex-lines+1 ; downOuterSpaceIndex=downOuterSpaceIndex+1) {
					System.out.print(" ");
				}

				//왼쪽 mark 출력
				System.out.print(mark);

				//가운데 공백 출력
				for(int downInnerSpaceIndex=0 ; downInnerSpaceIndex<4*lines-2*lineIndex-5 ; downInnerSpaceIndex=downInnerSpaceIndex+1) {
					System.out.print(" ");
				}

				//오른쪽 mark 출력
				if(lineIndex!=lines*2-2) {
					System.out.println(mark);					
				} else {
					System.out.println("");
				}
			}
		}
	}
}
```
