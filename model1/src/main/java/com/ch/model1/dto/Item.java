package com.ch.model1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


// 전국 100대 산 관광정보 ,오픈 API 가장 안쪽에 들어있는 Item 정보를 담기위한 DTO 

/*
 * 
 * */
@Data
@JsonIgnoreProperties(ignoreUnknown = true	)//json문자열과 자바 객체간에 매핑시, 자바객체가 보유하지 않은 속성은 그냥 무시해
public class Item {
	private String placeNm;
	private double lat; //위도
	private double lot; //경도
	private String frtrlNm;
}
