package com.ch.shop.model.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ch.shop.dto.Member;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberDAO memberDAO;
	
	@Override
	public void registOrUpdate(Member member) {
		Member obj =memberDAO.findByProvider(member);
		
		if(obj==null) {
			memberDAO.insert(member); // 회원가입이 안되어 있을 경우만 실행!!!
			log.debug("신규 회원 가입 처리");
		}else {
			//sns 의 회원의 경우 자신의 프로필을 변경할 수 있기 때문에, 우리의 mysql도 그 정보에 맞게 동기화 시켜야함
			memberDAO.update(member);
			log.debug("기존 회원 업데이트 처리");
		}
		
	}

}
