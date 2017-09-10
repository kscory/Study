# 난이도 7 문제
* 그림</br>
![](https://github.com/Lee-KyungSeok/ControlFlowExample/blob/master/Difficulty7/difficulty_7.PNG)

## __풀이__

1. 라인수 만큼 반복
2. 시작 공백 표시 (입력값이 k라면)</br>
 2.1 0+1+2+3+4+...+k-1, ..., 0+1+2, 0+1, 0...</br>
 2.2 k=6 이면 => 0+1+2+3+4+5, 0+1+2+3+4, 0+1+2+3, ... 으로 공백 표시</br>
3. 왼쪽 값(mark) 표시
4. 값 사이 공백 표시</br>
 4.1 공통 공백 표시 </br>
 4.2 0을 제외한 추가 공백 표시 </br>
5. 오른쪽 값(mark) 표시

#### __소스 코드__
``` java
public class Test07 {

	public void run(String mark, int lines) {

    // 라인수 만큼 반복
		for(int lineIndex=0; lineIndex<lines ; lineIndex=lineIndex+1) {

			//처음 공백 출력
			for(int firstSpaceIndex=0 ; firstSpaceIndex<lines-lineIndex-1 ; firstSpaceIndex++) {
				for(int i=0 ; i<firstSpaceIndex+1 ; i++)
				{
					System.out.print(" ");
				}
			}

			//왼쪽 mark
			System.out.print(mark);

      //값 사이 공백 표시
			//가운데 공백 출력
			for(int innerSpaceIndex=0 ; innerSpaceIndex<lines ; innerSpaceIndex=innerSpaceIndex+1) {
				System.out.print(" ");
			}
			//추가 공백 출력
			for(int innerSpaceIndex2=0 ; innerSpaceIndex2<lineIndex ; innerSpaceIndex2++) {
				for(int i=0 ; i<2*(lines-innerSpaceIndex2)-2 ; i++)
				{
					System.out.print(" ");
				}
			}

			//오른쪽 mark
				System.out.println(mark);
		}
	}

}
```
