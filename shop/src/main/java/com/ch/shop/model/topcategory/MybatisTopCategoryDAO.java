package com.ch.shop.model.topcategory;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/* TopCategoryDAO 를 구현한 객체
 * 추후 Service 객체는 아래의 객체를 바로 보유하지 않고 , 보다 느슨하게 상위 객체인 그냥 TopCategoryDAO보유해야 한다
 * */

@Repository
public class MybatisTopCategoryDAO implements TopCategoryDAO{
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate; 

	@Override
	public List selectAll() {
		
		return sqlSessionTemplate.selectList("TopCategory.selectAll");
	}

}
