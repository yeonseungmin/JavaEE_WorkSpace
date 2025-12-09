package com.ch.mvcframework.movie.model;
//javaSE,EE,ME에서 사용가능한 코드임으로 코드 분리
public class MovieManager {
	public String getAdvice(String movie) {
		// 각 영화에 대한 메시지 만들기	
		String msg = "선택한 영화가 없음";
		if(movie!=null){
			if(movie.equals("귀멸의 칼날")){
				msg="최신 일본 애니메이션 개봉작";
			}else if(movie.equals("공각기동대")){
				msg="SF";
			}else if(movie.equals("에이리언")){
				msg="외계 생명체 SF 시리즈";
			}else if(movie.equals("소울")){
				msg="pixar 애니메이션";
			}
		}
		return msg;
	}		
}
