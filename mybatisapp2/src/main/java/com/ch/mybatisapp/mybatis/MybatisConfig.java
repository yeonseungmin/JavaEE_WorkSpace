package com.ch.mybatisapp.mybatis;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/*
 * mybatis 설정 파일은 프로그래밍 언어가 아닌 단순한 설정정보를 가진
 * 리소스 임으로 , 리소스를 해석해줄 객체가 필요함
 * 따라서 아래 클래스가 그 역할을 할 예정
 * */
public class MybatisConfig {
	
	private static MybatisConfig instance;//싱글톤
	private SqlSessionFactory sqlSessionFactory;
	
	public static MybatisConfig getInstance() {
		if(instance ==null) {
			instance = new MybatisConfig();
		}
		return instance;
	}
	
	//팩토리로 부터 쿼리문 실행에 필요한 SqlSession 객체를 가져갈 수 있도록 매서드를 정의
	//외부의 객체는 이메서드 호출만으로, 팩토리의 SqlSession을 얻어갈 수 있다.
	//참고로 SqlSession 은 이미 접속 정보를 가지고 있으며 쿼리문도 실행할수 있는 객체 !!!!!
	//자바 개발자는 기존의 JDBC코드에서 Connection, PreparedStatement를 직접 다루었던
	// 비효율적 코드에서 벗어날수 있따. (Mybatis 덕분)
	public SqlSession getSqlSession() {
		return sqlSessionFactory.openSession();
	}
	
	//SqlSession은 쿼리문 수행후 닫아야 하므로, 아래의 매서드에서 대신 닫아주는 기능을 구현해주자
	public void release(SqlSession sqlSession) {
		if(sqlSession !=null) {
			sqlSession.close();
		}
	}
	
	private MybatisConfig() {
		try {
			String resource = "com/ch/mybatisapp/mybatis/config.xml"; 	//나의 패키지 경로
			/*
			 * MyBatis 를 이용하면 개발자는 더이상 JDBC를 직접 사용하여 데이터베이스 연동 코드를 작성할 필요가 없다.
			 * 이때 개발자가 쿼리문을 수행하기 위해서는 Mybatis 가 제공해주는 SqlSession 객체를 이용해야 하는데.
			 * 이 SqlSession 객체는 SqlSessionFactory로 부터 얻고, 사용이 끝난 후엔 그냥 닫으면 된다.(close)
			 * */
			InputStream inputStream = Resources.getResourceAsStream(resource);
			 sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
