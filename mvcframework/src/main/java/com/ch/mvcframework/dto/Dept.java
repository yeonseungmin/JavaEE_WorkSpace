package com.ch.mvcframework.dto;

import java.util.List;

import lombok.Data;

@Data
public class Dept {

	private int deptno;
	private String dname;
	private String loc;
	private List<Emp> list;
}
