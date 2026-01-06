package com.ch.shop.dto;

import lombok.Data;

/*
 	클라이언트가 비동기로 요청을 시도할때 그 응답정보가 메시지라면,
 	개발자가 일일이 문자열 처리를 하기 보다는 jackson과 같은 자동 컨버터에게 변환을 맡기는게 편하다.
 	컨버터를 이용하기 위해서는 응답정보를 표현한 자바의 클래스가 필요함
 	
 	{
 		"code":200,	404
 		"msg": "등록성공",
 		"success":"Okay,Fail"
 	}
 */

@Data
public class ResponseMessage {

	private String msg;
	
}
