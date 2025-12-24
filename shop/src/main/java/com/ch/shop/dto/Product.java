package com.ch.shop.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Product {

	private int product_id;
	private String product_name;
	private String brand;
	private int price;
	private int discount;
	private List<Color> colorList;	//색상
	private List<Size> sizeList;	//사이즈
	private List<ProductImg> productImgList;
	private String introduce;
	private String detail;
	private SubCategory subCategory;	//하위카테고리
	
	
	private MultipartFile[] photo;
	//MultipartFile 은 업로드된 이미지 1 개에 대한 정보를 가진 객체..
	// 사용시 주의할점 : 반드시 <input type ="file"> 의 이름과 일치해야 한다.
	// 즉 파라미터 명과 MultipartFile의 변수명이 일치해야 한다.
	//하지만, 아직 하드디스크에 저장된 상태는 아니며, 메모리 보관된 상태임..
	// 그럼 언제 저장되나? 개발자가 transferTo() 메서드를 호출할때, 임시 디렉토리나 메모리에 존재하던
	//파일이 실제 개발자가 지정한 디렉토리와 파일명으로 존재하게됨.. 이때 임시 디렉토리 안에있는 파일은 자동으로 제거됨

	// 가장 중요 이미지 처리 (Spring 관여)
	
	
}
