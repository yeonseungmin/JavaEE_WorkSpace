package com.ch.mvcframework.repository;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.dto.Emp;
import com.ch.mvcframework.exception.EmpException;
import com.ch.mvcframework.mybatis.MybatisConfig;

public class EmpDAO {
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();
	
	// 1명 등록
	public void insert(SqlSession sqlSession,Emp emp)	throws EmpException{
//		SqlSession sqlSession = mybatisConfig.getSqlSession();
		
		/*
		 * 강요된 예외 (try~catch) - 개발자가 예외 처리를 하지않으면 
		 * 									IDE 빨간줄 가면서 ,컴파일 불가능.. 강제
		 * */
		try {
			int result = sqlSession.insert("Emp.insert", emp);
//			System.out.println("사원등록 결과"+result);
//			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace(); //에러의 정보를 개발자나, 시스템관리자가 알수 있도록 보고.
			throw new EmpException("사원등록 실패",e);
			
		}
		
//		mybatisConfig.release(sqlSession);
		
		
	}
}
