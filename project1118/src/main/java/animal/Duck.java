package animal;

public class Duck extends Bird{
	String name = "오리";
	int age = 3;
	static int wing =2;
	
	public void fly() {
		System.out.println("새가 날아요");
		
	}
	
	public static void main(String[] args) {
		int x =6;
		Duck d1 = new Duck();
		Duck d2 = new Duck();
		d1.age =5;
		System.out.println(d1.wing);
		Duck.wing =4;
		System.out.println(d2.wing);
	}
}

/*
 * JVM 3단계
 * | 메서드 영역 					| stack영역 											| heap영역 |
 * 실행할때 (클래스 로드)			|args[null] - int x - d1[주소] - d2[주소]		|String name - int age
 * 																							|wing 변수가 없기에 static 변수의 wing을 참조
 * 																							|원본 코드 Duck.wing 으로 접근하여 거푸짚을 교체													
 */ 
