package com.ch.shop.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ch.shop.dto.Color;
import com.ch.shop.dto.Product;
import com.ch.shop.dto.Size;
import com.ch.shop.exception.DirectoryException;
import com.ch.shop.exception.ProductColorException;
import com.ch.shop.exception.ProductException;
import com.ch.shop.exception.ProductImgException;
import com.ch.shop.exception.ProductSizeException;
import com.ch.shop.exception.UploadException;
import com.ch.shop.model.product.ProductService;
import com.ch.shop.model.topcategory.TopCategoryService;

import lombok.extern.slf4j.Slf4j;

/*
 * 쇼핑몰의 관리자에서 상품과 관련된 요청을 처리하는 하위 컨트롤러
 * */
@Controller
@Slf4j
public class ProductController {

	//서비스 보유 
	@Autowired
	private TopCategoryService topCategoryService;
	@Autowired
	private ProductService productService;
	
	//상품 등록 폼 요청 처리
	@GetMapping("/product/registform")
	public String getRegistForm(Model model) {
		//3단계 : 상품페이지에 출력할 상위 카테고리 가져오기
		//List topList = topCategoryService.getList();	//이것도 가능하지만 비동기를 배웠으니
		
		//4단계 : 결과 저장(request에 직접 해야하지만, Spring 에서는 Model 객체를 이용하면 간접적으로 저장이 됨) 
		//jsp까지 topList를 살려서 가야하므로, 포워딩 처리를 해야 함.. 스프링 개발자가 redirect 를 명시하지 않으면 디폴트가 포워딩
		//model.addAttribute("topList",topList);	//위 주석과 동일한 효과
		
		
		return "admin/product/regist";
	}
	
	
	//상품 등록 요청 처리
	//클라이언트가 전송한 데이터의 Content-Type 이 multipart/form-data 즉 텍스트 뿐만 아니라, 바이너리가 포함된 경우
	// 기존의 HttpServletRequest 객체로 바로 받지 못한다. 따라서 개발자가 스트림을 직접 제어하거나 기존에 이미 개발되어진 파일 업로드 컴포넌트 이용
	// 자바 분야에서는 apache 에서 만든 commons fileupload 라이브러리를 많이 사용한다.
	// 따라서 스프링 프레임 웤도 apache commons fileupload를 내부적으로 사용한다.
	@PostMapping("/product/regist")
	@ResponseBody
	//파라미터 중 DTO와 일치하지 않아 , 자동으로 매핑되지 않을경우 개발자가 직접 수동으로 하면 된다.
	public Map<String, String> regist(Product product,int[] color,int[] size) {
		//매개변수로 지정된 객체의 ,html문서의 폼에 지정된 파라미터명이 일치한다면 자동으로 매핑이 이루어짐
		log.debug("선택하신 하위 카테고리 = " + product.getSubCategory().getSubcategory_id());
		log.debug("상품등록 요청받았어 = " + product.getProduct_name());
		log.debug("브랜드 요청받았어 = " + product.getBrand());
		log.debug("가격 요청받았어 = " + product.getPrice());
		log.debug("할인가 요청받았어 = " + product.getDiscount());
		
		
		//색상에 대한 수동 처리
		List colorList = new ArrayList();
		for(int c : color) {
			Color dto = new Color();
			dto.setColor_id(c);
			colorList.add(dto);

		}
		
		List sizeList = new ArrayList();
		//사이즈에 대한 수동 처리
		for(int s : size) {
			Size dto = new Size();
			dto.setSize_id(s);
			sizeList.add(dto);
			
		}
		
		product.setColorList(colorList);
		product.setSizeList(sizeList);
		
		log.debug("소개 = " + product.getIntroduce());
		log.debug("상세소개 = " + product.getDetail());
		log.debug("색상 = " + product.getColorList());
		log.debug("사이즈 = " + product.getSizeList());
		/*------------------------------------------------------------------------------------------------------------------------*/
		/*넘겨 받은 파라미터를 이용하여 상품등록
		 * 상품등록이란 논리적 업무 1개 단위 안에 (product, product_img, product_size, product_color 까지 4개의 업무가 포함됨)
		 * 하지만, 컨트롤러는 4개의 업무로 이루어져 있다는 사실을 몰라야 한다. (대신 서비스가 알아야함)
		 * 
		 */
		/*------------------------------------------------------------------------------------------------------------------------*/
		try {
			productService.regist(product);
		} catch (Exception e) {
			productService.cancelUpload(product);
			throw e;
		}
		
		StringBuffer sb = new StringBuffer();
		/*
		 * 
		 {
		 	"message" : "상품등록"
		 } 
		 
		 */
//		sb.append("{");
//		sb.append("\"message\" : \"상품등록\"");
//		sb.append("}");
		
		
		// JSON 표기를  자바로 표현하면 결국 Map 이다.
		// 따라서 응답정보를 만들때, 개발자가 일일이 JSON 문자열을 생성하면 효율성이 떨어지므로,
		// jackson 라이브러리를 이용한 자바 객체의 변환을 좀더 빠르게 한다.
		Map<String,String> body = new HashMap<>();
		body.put("message", "상품등록 성공");
		return body;
	}
	
	//동기방식 상품 목록 페이지 요청 처리
	@GetMapping("/product/list")
	public String getListPage(Model model) {
		List productList = productService.getList(); //3단계 일시키기
		model.addAttribute("productList",productList);//4 단계 저장
		return "admin/product/list";
	}
	
	
	//비동기 상품 목록 요청 처리
	@GetMapping("/product/async/list") // curl http://localhost:8888/admin/product/async/list
	@ResponseBody //@ResponseBody 를 명시하면 DispatcherServlet 이 응답 결과를 ViewResolver에게 의뢰하지 않음
							// 결과를 jsp 로 보여줄 일이 없는 비동기 요청인 경우 사용함	객체 -> json
	public List<Product> getList(Model model) {
		List productList = productService.getList(); //3단계
		/* model.addAttribute("productList",productList); */ // 별도의 디자인 페이지에서 결과를 보여주는 방식이 아니라 데이터를 json 문자열로 응담해버리는 처리를 해야함으로 
		//4단계 생략	
		
		return productList;
		
		}
	
	
	
	
	
	// 스프링에서는 컨트롤러에 요청처리 매서드들 중 예외를 발생 할 경우, @ExceptionHandler 로 예외를 처리하는 메서드가 자동으로 호출됨
	@ExceptionHandler( {ProductException.class,UploadException.class,DirectoryException.class, ProductColorException.class, ProductSizeException.class,ProductImgException.class})
	@ResponseBody
	public ResponseEntity<Map<String, String>> handle(Exception e) {
		log.debug("상품 등록시 예외가 발생하여, handler 메서드가 호출됨");
		// 예외가 발생하면 파일을 삭제하자.
//		productService.cancelUpload();	
		Map<String,String> body = new HashMap<String, String>();
		body.put("message","등록실패");
		//클라이언트에게 응답 코드를 보내지 않으면, 클라이언트는 성공이라고 생각함. 500에러
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
	}
	
	
	
}
