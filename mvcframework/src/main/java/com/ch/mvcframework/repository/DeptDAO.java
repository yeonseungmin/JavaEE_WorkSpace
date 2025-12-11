package com.ch.mvcframework.repository;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.dto.Dept;
import com.ch.mvcframework.exception.DeptException;
import com.ch.mvcframework.mybatis.MybatisConfig;

public class DeptDAO {
	
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();
	//1건 등록
	public void insert(SqlSession sqlSession,Dept dept) throws DeptException{
//		SqlSession sqlSession = mybatisConfig.getSqlSession();
		try {
			int result = sqlSession.insert("Dept.insert",dept);
//			System.out.println("부서등록 결과"+result);
//			sqlSession.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DeptException("부서등록 실패",e);	//throw 는 예외 발생 코드
			
		}
		
//		mybatisConfig.release(sqlSession);
		
		
	}
}
