# 난이도 6 문제
* 그림</br>
![](https://github.com/Lee-KyungSeok/ControlFlowExample/blob/master/Difficulty6/difficulty_6.PNG)

## __풀이__

1. 라인수 만큼 반복
2. 시작 공백 표시 (입력값이 k라면)</br>
 2.1  0, k-1, (k-1)+(k-2), (k-1)+(k-2)+(k-3)...</br>
 2.2 k=6 이면 => 0, 6, 6+5, 6+5+4 ... 으로 공백 표시
3. 한줄 당 (mark + 공백)을 세트로 표시</br>
 3.1 mark 표시</br>
 3.2 공백 표시("입력값-라인번호-1" 개)</br>

#### __소스 코드__
``` java
public class Test06 {

	public void run(String mark, int lines) {

		// 라인수만큼 반복
		for(int lineIndex=0; lineIndex<lines ; lineIndex=lineIndex+1) {

			//처음 공백 출력
			for(int firstSpaceIndex=0 ; firstSpaceIndex<lineIndex ; firstSpaceIndex++) {
				for(int i=0 ; i<lines-firstSpaceIndex-1 ; i++)
				{
					System.out.print(" ");
				}
			}

			//(mark+공백)을 한세트로 출력
			for(int setIndex=0;setIndex<lines-lineIndex; setIndex=setIndex+1) {
				//mark하고
				System.out.print(mark);
				//가운데 공백 출력
				for(int middleSpaceIndex=0; middleSpaceIndex<lines-lineIndex-1 ; middleSpaceIndex=middleSpaceIndex+1) {
					System.out.print(" ");
				}
			}

				System.out.println("");

		}
	}

}
```
