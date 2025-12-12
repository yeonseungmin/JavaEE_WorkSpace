package com.ch.mvcframework.emp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.controller.Controller;
import com.ch.mvcframework.dto.Dept;
import com.ch.mvcframework.dto.Emp;
import com.ch.mvcframework.emp.model.EmpService;
import com.ch.mvcframework.exception.EmpException;
import com.ch.mvcframework.mybatis.MybatisConfig;
import com.ch.mvcframework.repository.DeptDAO;
import com.ch.mvcframework.repository.EmpDAO;

/*
 * 사원 등록 요청을 처리하는 하위 컨트롤러
 * 3단계: 일시키기 
 * 4단계: 등록 (DML 이므로 생략)
 * */
public class RegistController implements Controller{
	
	private EmpService empService = new EmpService();
	private String viewName;
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//부서 관련 정보 -->Dept2 에 등록
		String deptno=request.getParameter("deptno");
		String dname=request.getParameter("dname");
		String loc=request.getParameter("loc");
	
		Dept dept = new Dept();
		dept.setDeptno(Integer.parseInt(deptno));
		dept.setDname(dname);
		dept.setLoc(loc);
		
		
		//사원 관련 정보 -->Emp2 에 등록
		Emp emp = new Emp();
		String empno=request.getParameter("empno");
		String ename=request.getParameter("ename");
		String sal=request.getParameter("sal");
		
		emp.setEmpno(Integer.parseInt(empno));
		emp.setEname(ename);
		emp.setSal(Integer.parseInt(sal));
		//Emp가 Dept를 has a 관계로 보유하고 있으므로
		//낱개로 전달하지말고, 모아서 전달하자.
		emp.setDept(dept);
		//모델 영역에 일 시키기!!
		//(주의) 구체적으로 일 하지 말고 시키자!!
		//코드가 혼재되므로 , 모델영역을 분리시킬수 없으므로 분리시킬 수 없으므로 재사용성이 떨어짐
		
		try {
			//아래의 레지스트 메서드에는 예외 처리가 전가되는 throws 가 명시되어 있음에도 불구하고
			// RuntimeError 이기 때문에 오류처리가 강제 되지 않으며, 개발자가 직접 핸들링 해줘야 한다.
			empService.regist(emp);	
			viewName = "/emp/regist/result";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			viewName = "/emp/error";
		}
	}

	@Override
	public String getViewName() {
		return viewName;
	}

	@Override
	public boolean isForward() {
		// TODO Auto-generated method stub
		return false;
	}

}
