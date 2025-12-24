package com.ch.shop.dto;

import lombok.Data;

/* 구글 뿐만 아니라 , 네이버, 카카오 등 IDP 연동시 필요한 정보를 담는 객체 */
@Data
public class OAuthClient {
	private String provider; // google, naver, kakao 구분값
	private String clientId; // 앱 등록시 개발자 콘솔에서 발급받은 클라이언트 아이디(보안상 상관없음)
	private String clientSecret;	// 앱 등록시 개발자 콘솔에서 발급받은 클라이언트 비밀번호(보안상 매우 중요) - 비공개 처리
	private String authorizeUrl; // 클라이언트가 sns 로그인 버튼을 누를때 요청대상 URL 
	private String tokenUrl; // 리소스 오너의 정보를 조회할 때 사용할 요청 주소URL 
	private String scope; // 접근 범위 
	private String redirectUri; // Provider 로부터 콜백받을 주소 (가장중요)
}
