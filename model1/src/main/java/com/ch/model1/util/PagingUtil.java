package com.ch.model1.util;

import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;

import lombok.Data;

//페이징 처리 로직을 일일이 변수 선언하여 사용하면 효율성이 떨어지므로,
//재사용 가능한 객체로 정의한다.
@Data
public class PagingUtil {
	int totalRecord; //총 레코드 수
	int pageSize =10; // 한페이지 당 보여질 레코드 수 (참고로 변수명은 오라클 기준)
	int totalPage;//총 페이지 수
	int blockSize=10;//블럭당 보여질 페이지수
	int currentPage=1;// 게시판의 첫 페이지 (커서)
	int firstPage;//블럭당 반복문의 시작 페이지
	int lastPage;//블럭당 반복문의 끝 페이지
	int num;//페이지당 시작 번호
	int curPos;//페이지당 ArrayList의 시작 인덱스
	
	//복잡한 페이징 처리 로직을 아래의 메서드로 처리
	//그리고 아래의 메서드는 사용자가 브라우저로 목록 요청을 할때마다 호출!!
	public void init(List list, HttpServletRequest request) {
		this.totalRecord=list.size();
		this.totalPage=(int)Math.ceil((float)totalRecord/pageSize);
		//사용자가 선택한 페이지 수 처리!
		if(request.getParameter("currentPage") !=null) {//파라미터가 있을때만 사용자가 넘겨준 페이지 명을 this.currentPage에 대입하자.
			this.currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		this.firstPage=this.currentPage-(this.currentPage-1)%this.blockSize;
		this.lastPage= this.firstPage+(this.blockSize-1);
		this.curPos=(this.currentPage-1)*this.pageSize;
		this.num=this.totalRecord-curPos;
		
		
		
	}
	
	
}
