package com.ch.shop.model.member;

import com.ch.shop.dto.Member;

public interface MemberService {

	//회원가입(db insert, 홈페이지 회원인 경우 비밀번호 암호화 email 발송)
	// 그리고 이미 회원으로 강비되어 있따면, 가입시키지 않는 처리..또는 회원 정보가 변경되었을 경우
	// 변경을 반영하는 처리...
	public void registOrUpdate(Member member);
	
	
	
	
}
