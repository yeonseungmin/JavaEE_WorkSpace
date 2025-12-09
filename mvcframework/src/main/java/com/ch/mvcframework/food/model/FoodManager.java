package com.ch.mvcframework.food.model;

public class FoodManager {

	
public String getAdvice(String food) {
			// 각 영화에 대한 메시지 만들기	
			String msg = "선택한 영화가 없음";
			if(food!=null){
				if(food.equals("카레")){
					msg="동훈이가 좋아하던 카레..";
				}else if(food.equals("닭볶음탕")){
					msg="동훈이 좋아하던 닭볶음탕";
				}else if(food.equals("참치마요")){
					msg="태호가 좋아하던 참치마요";
				}else if(food.equals("황태 미역국")){
					msg="태호가 좋아하던 황태미역꾸꾸국";
				}
			}
			return msg;
		}		
}
