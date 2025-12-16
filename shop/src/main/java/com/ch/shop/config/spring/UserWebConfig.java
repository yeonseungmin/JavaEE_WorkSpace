package com.ch.shop.config.spring;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.ch.shop.controller.shop.BoardController;
import com.ch.shop.model.board.MybatisBoardDAO;

/*
 * !!! 이 클래스는 로직을 작성하기 위함이 아니라 애플리케이션에서 사용할 빈(객체)들 및 그들간의 관계(weaving)까지
 *  명시하기 위한 목적의 클래스이며, 쇼핑몰의 일반 유저들이 보게 되는 애플리케이션 쪽 빈(@Bean) 들을 관리한다
 * 
 * 
 * */
@Configuration //단지 xml 을 대신한 설정용 클래스에 불과해!!
@EnableWebMvc 	//!!!!!!!! 필 수 설 정 !!!!!!!! 스플링이 지원하는 MVC 프레임 워크를 사용하기 위한 어노테이션)
// @ComponentScan = 일일이 빈으로 등록할 필요가 없는 많이 알려진 빈들을 가리켜 
//스프링에서는 컴포넌트라고 한다. 또한 이 컴포넌트들은 패키치 위치만 설정해 놓으면
//스프링이 알아서 찾아내서(검색) 인스턴스를 자동으로 만들어준다.
//MVC 에서의 Controller는 @Controller 를 붙임
//MVC 에서의 DAO 는 @Repository 를 붙임
//MVC 에서의 DAO 는 @Service 를 붙임
//MVC 에서의 특정 분류가 딱히 없음에도 자동으로 올리고 싶다면 @Component
@ComponentScan(basePackages = {"com.ch.shop.controller","com.ch.shop.model"} )
public class UserWebConfig {

	//DispatcherServlet이 하위 컨트롤러 부터 반환받은 결과 페이지에 대한 정보는 사실 완전한 jsp 경로가 아님으로
	//이를 해석할 수 있는 자인 ViewResolver 에게 맡겨야 하는데, 이 ViewResolver 중 유달리 접두어와 접미어 방식을
	//이해하는 ViewResolver 를 InternalResourceViewResolver 라고 한다. (이 객체에 사전에 접두어 접미어 등록)
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver rv = new InternalResourceViewResolver();
		//접두어 등록
		rv.setPrefix("/WEB-INF/views");
		//접미어 등록
		rv.setSuffix(".jsp");
		return rv;
	}
	
	
//	컨트롤러 페이지에 @Controller 지정하면 자동으로 올려줌 !!!!!!!!!
//	@Bean	//으로 하면 컨트롤러 하나당 1:1 로 올려야 함
//	public BoardController boardController() {
//		
//		return null;
//	}
	
	
	/*
	 * DB 연동 세팅
	 * 
	 * 스프링이 MVC 프레임워크 중 컨트롤러 영역만을 지원하는 것이 아니라, 데이터베이스 관련 제어도 지원하므로,
	 * 지금까지 순수하게 사용해왔던 mybatis를 스프링이 지원하는 mybatis로 전환해 본다.
	 * 
	 * 전제조건 : 스프링이 지원하는 데이터 연동기술을 사용하려면 Spring JDBC ( 4.3.30 )라이브러리를 추가해야한다. 
	 * ----------------------------------------------------------------------------------------------------
	 * 1) 개발자가 사용하고 싶은 데이터 소스를 결정 - 톰켓이 지원하는 JNDI 사용
	 * ----------------------------------------------------------------------------------------------------
	 * */
	//Javax.sql.DataSource
	public DataSource dataSource() throws NamingException{
		JndiTemplate jndi = new JndiTemplate();	//톰켓에 있는 jdni를 찾는 객체
		return jndi.lookup("java:comp/env/jndi/mysql",DataSource.class);

	}
	
	 /* ----------------------------------------------------------------------------------------------------
	 * 2) 트랜잭션 매니저 등록
	 * 		-스프링은 개발자가 사용하는 기술이 JDBC, Mybatis, Hibernate, JPA 이건 상관없이
	 * 		일관된 방법으로 트랜잭션을 처리할 수 있는 방법을 제공해주는데, 개발자는 자신이 사용하는 기술에 따라
	 * 		적절한 트랜잭션 매니저를 등록해야 한다.
	 * 		예)JDBC 사용시 - DataSourceTransactionManager를 빈으로 등록해야 한다.
	 * 		예)Hibernate 사용시 - HibernateTransactionManager를 빈으로 등록해야 한다.
	 * 		예)Mybatis 사용시 - DataSourceTransactionManager를 빈으로 등록해야 한다.
	 * 								ㄴ 특히 Mybatis 의 경우 JDBC 와 동일한 이유는 Mybatis가 JDBC를 내부적으로 사용하고 있기 때문이다.
	 * 		그리고 이 모든 트랜젝션 매니저의 최상단 객체가 바로 PlatformTransactionManager 이다. 
	 * ----------------------------------------------------------------------------------------------------
	 * */
	
	@Bean	//트랜잭션 !!!!!!!!		sqlSessionFactory.getConfiguration().getEnvironment().....환경설정
	public PlatformTransactionManager transactionManager(SqlSessionFactory sqlSessionFactory) {
		return new DataSourceTransactionManager(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource());
		
	}
	
	/*----------------------------------------------------------------------------------------------------
	 * 3)SqlSession을 관리하는 mybatis의 SqlSessionFactory를 빈으로 등록
	 * ----------------------------------------------------------------------------------------------------
	 */
	@Bean		//spring 이 mybatis를 이해하는 과정
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		//순수 mybatis 프레임워크 자체에서 지원하는 객체가 아니라, mybatis-Spring에서 지원하는 객체인
		//SqlSessionFactoryBean 을 이용하여 설정 xml 파일을 로드한다.
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		//패키지에 포함된 파일의 유형이 클래스가 아닌경우 더이상 패키지로 표현하지말고, 일반 디렉토리로 취급해야한다. "/"
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("com/ch/shop/config/mybatis/config.xml"));
		
		// 세션 팩토리가 톰켓의 내장 서버를 인식하라는 코드
		sqlSessionFactoryBean.setDataSource(dataSource());
		return sqlSessionFactoryBean.getObject();
	}
	

	/*----------------------------------------------------------------------------------------------------
	 * 4) SqlSessionTemplate 빈 등록		(쿼리 수행 객체)
	 * 	 	mybatis 사용시 쿼리문 수행을 위해서는 SqlSession을 이용했으나,
	 * 		 mybatis-Spring 에서는  SqlSessionTemplate 객체를 사용해야 함
	 * ----------------------------------------------------------------------------------------------------
	 */
	@Bean		// 3) 에서 마든 sqlSessionFactory를 weaving , throws 처리
	public SqlSessionTemplate sessionTemplate() throws Exception{
		return new SqlSessionTemplate(sqlSessionFactory());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
