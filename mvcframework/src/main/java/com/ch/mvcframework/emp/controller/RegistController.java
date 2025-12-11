package com.ch.mvcframework.emp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.controller.Controller;
import com.ch.mvcframework.dto.Dept;
import com.ch.mvcframework.dto.Emp;
import com.ch.mvcframework.mybatis.MybatisConfig;
import com.ch.mvcframework.repository.DeptDAO;
import com.ch.mvcframework.repository.EmpDAO;

/*
 * 사원 등록 요청을 처리하는 하위 컨트롤러
 * 3단계: 일시키기 
 * 4단계: 등록 (DML 이므로 생략)
 * */
public class RegistController implements Controller{
	/*
	 * DeptDAO와 EmpDAO가 같은 트랜잭션으로 묶이려면 각각의 DAO는 공통의 SqlSession을 사용해야 한다..
	 * 따라서 이 컨트롤러에서 MybatisConfig으로 부터 SqlSession을 하나 취득한 후 
	 * insert문 호출시 같은 주소값을 갖는 공유된 SqlSession을 나눠주자 (트랜젝션 사용!!!!!)
	 * 
	 * */
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();
	DeptDAO deptDAO = new DeptDAO();
	EmpDAO empDAO = new EmpDAO();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		
		
		//부서 관련 정보 -->Dept2 에 등록
		String deptno=request.getParameter("deptno");
		String dname=request.getParameter("dname");
		String loc=request.getParameter("loc");
	
		Dept dept = new Dept();
		dept.setDeptno(Integer.parseInt(deptno));
		dept.setDname(dname);
		dept.setLoc(loc);
		
		
		//사원 관련 정보 -->Emp2 에 등록
		String empno=request.getParameter("empno");
		String ename=request.getParameter("ename");
		String sal=request.getParameter("sal");
		
		Emp emp = new Emp();
		emp.setEmpno(Integer.parseInt(empno));
		emp.setEname(ename);
		emp.setSal(Integer.parseInt(sal));
		
		//mybatis는 Default 가 autocommit=false로 되어있으므로 , 개발자가 별도로 
		// start transaction 을 언급하지 않아도 됨
		try {
			deptDAO.insert(sqlSession,dept);
			empDAO.insert(sqlSession,emp);
			sqlSession.commit();// 트랜젝션 확정
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback(); // 트랜젝션 롤백
		}finally {
			mybatisConfig.release(sqlSession);
			
		}
		
		
	}

	@Override
	public String getViewName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isForward() {
		// TODO Auto-generated method stub
		return false;
	}

}
