package com.ch.shop.util;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ch.shop.exception.DirectoryException;
import com.ch.shop.exception.UploadException;

/*
 * 이 클래스의 목적
 * 기존 코드에서는 컨트롤러가 업로드를 처리하고 있었으므로, 문제
 * 파일 업로드는 모델 영역의 업무이므로 , 절대 컨트롤러가 부담하면 안됨, 따라서 모델영역의 객체이면서
 * DataBase 엄무를 다루지 않는 객체이다. (DAO 아님) 
 * */
@Component	// 스프링은 @Controller, @Service, @Repository 외에, 개잘자가 정의한 객체도 자동으로 ComponentScan의 대상이
					// 될 수 있는데, 이때 개발자가 정의한 객체를 자동으로 빈으로 등록하면 @Component로 선언하면 된다.
public class FileManager {
	/*---------------------------------------------------------------
	 * 원하는 이름으로 디렉토리 만들기
	 * --------------------------------------------------------------*/
	public void makeDirectory(String path) throws DirectoryException{
		// 모든 프로그래밍 언어에서는 디렉토리도 파일로 취급한다.
		File file = new File(path);
		if(file.mkdir() ==false) {
			throw new DirectoryException("디렉토리 생성 실패");
//			file.mkdirs(); // ex) c:/a/b  하면 b뿐만 아니라 a 까지 자동으로 만들어줌
			
		}
	}
	
	
	
	
	
	
	/*---------------------------------------------------------------
	 * 원래 파일에 대한 처리는 트랜잭션의 대상이 되지않는다. 하지만 우리의 경우 상품등록업무에는 파일의 저장도 포함되어 있으므로
	 * 만약 파일 저장에 실패할 경우, Exception을 서비스 객체에 던지면, 서비스는 예외가 팔생할 경우 무조건 트랜잭션을 rollback 해버리므로 
	 * 이 특징을 이용하자.
	 * --------------------------------------------------------------*/
	public void save(MultipartFile multipartFile, String dir,String filename) throws  UploadException{
		File file = new File(dir, filename);
		//임시 디렉토리 또는 메모리 상의 파일 정보를 이용하여 실제 디스크에 저장하는 행동
		try {
			multipartFile.transferTo(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UploadException("파일저장 실패",e);
		}
		
		
		
	}
	
	
	
}
