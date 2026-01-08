/**
 * 재사용 가능성이 높은 자바스크립트 코드를 라이브러리 화 시키자
 */

/*--------------------------------
정수를 화폐 표기화
Intl 은 JavaScript의 네이티브 내장 객체로써 국제화 표준 객체
--------------------------------*/
const MoneyConverter=new Intl.NumberFormat("ko-KR");

function format( price){
	return MoneyConverter.format(price	);
}