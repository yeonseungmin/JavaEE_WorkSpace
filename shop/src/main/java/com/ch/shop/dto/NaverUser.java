package com.ch.shop.dto;

import lombok.Data;

/*
 {
 
 
 } 
 */
@Data
public class NaverUser {
	private String id;				//실제 사용할 정보
	private String email;			//실제 사용할 정보
	private String name;			//실제 사용할 정보
	private String nickname;
	private String profile_image;
	private String gender;
	private String age;
	private String birthday;
	
}
