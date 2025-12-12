package com.ch.mvcframework.emp.model;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.dto.Emp;
import com.ch.mvcframework.exception.EmpException;
import com.ch.mvcframework.mybatis.MybatisConfig;
import com.ch.mvcframework.repository.DeptDAO;
import com.ch.mvcframework.repository.EmpDAO;

/*
 * 애플리케이션의 영역 중 서비스를 정의한다.
 * Service의 정의 목적
 * 1)서비스가 없을 경우 DAO들에게 일을 시키는 업무가 Controller에게 부담이 됨
 * 		따라서 이 시점부터는 컨트롤러가 아닌 Model의 업무와 뒤 섞여 버림
 * 
 * 2)컨트롤러를 DAO들과 분리시키고, 트랜잭션을 대신 처리할 객체가 필요함
 * 		
 * 
 * Model part
 * */
public class EmpService {
	
	/*
	 * DeptDAO와 EmpDAO가 같은 트랜잭션으로 묶이려면 각각의 DAO는 공통의 SqlSession을 사용해야 한다..
	 * 따라서 이 컨트롤러에서 MybatisConfig으로 부터 SqlSession을 하나 취득한 후 
	 * insert문 호출시 같은 주소값을 갖는 공유된 SqlSession을 나눠주자 (트랜젝션 사용!!!!!)
	 * 
	 * */
	
	//한명의 사원이 입사하면 부서와 사원을 동시에 등록하는 메서드..
	MybatisConfig mybatisConfig = MybatisConfig.getInstance();
	DeptDAO deptDAO = new DeptDAO();
	EmpDAO empDAO = new EmpDAO();
	public void regist(Emp emp) throws EmpException {
		SqlSession sqlSession = mybatisConfig.getSqlSession();
		//mybatis는 Default 가 autocommit=false로 되어있으므로 , 개발자가 별도로 
		// start transaction 을 언급하지 않아도 됨
		try {
			deptDAO.insert(sqlSession,emp.getDept());
			empDAO.insert(sqlSession,emp);
			sqlSession.commit();// 트랜젝션 확정
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback(); // 트랜젝션 롤백
			//아래의 throw 코드에 의해 에러가 발생한다. 따라서 개발자는 2가지중 1를 선택해야한다.
			//1)여기서 try-catch 할지
			//2)외부 메서드 호출자에게 throws 할지 
			throw new EmpException("사원등록 실패",e);
		}finally {
			mybatisConfig.release(sqlSession);
			
		}
	}
	
}
