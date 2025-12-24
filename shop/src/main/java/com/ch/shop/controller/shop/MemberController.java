package com.ch.shop.controller.shop;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ch.shop.dto.OAuthClient;
import com.ch.shop.model.topcategory.TopCategoryService;

import lombok.extern.slf4j.Slf4j;

//일반 유저가 사용하게 될 쇼핑몰 쪽의 회원관련 요청을 처리하는 컨트롤러

@Slf4j
@Controller
public class MemberController {
	@Autowired
	private TopCategoryService topCategoryService;
	@Autowired
	private Map<String, OAuthClient> oauthClients;
	@Autowired
	private RestTemplate restTemplate;
	
	//회원 로그인폼 요청 처리
	@GetMapping("/member/loginform")
	public String getLoginForm(Model model) {
		List topList=topCategoryService.getList();//3단계
		
		//4단계 결과 페이지로 가져갈것이 있따.
		model.addAttribute("topList", topList);
		return "shop/member/login";
	}
	
	//sns 로그인을 희망하는 유저들의 로그인 인증 요청 url 주소를 알려주는 컨트롤러 메서드
	// {} 변수 표시 value 는 @PathVariable("provider") 는 url의 일부를 파라미터화 시키는 기법 REST API 에 사용됨
	@GetMapping("/oauth2/authorize/{provider}")
	@ResponseBody //이 어노테이션을 설정하면, DispatcherServlet은 jsp 와의 매핑을 시도하지 않고 반환값 그대로 응답정보로 보낸다.
	public String getAuthUrl(@PathVariable("provider") String provider) throws Exception{
		OAuthClient oAuthClient=oauthClients.get(provider);
		log.debug(provider+"의 로그인 요청 url은" +oAuthClient.getAuthorizeUrl());
		
		// 이 주소를 이용하여 , 브라우저 사용자는 프로바이더에게 로그인을 요청해야 하는데 이때 요청 파라미터를 갖추어야 
		// 로그인 절차가 성공할수 있다.
		// 요청시 지참할 파라미터에는 clientId, callback url, scope....
		StringBuffer sb = new StringBuffer();
		sb.append(oAuthClient.getAuthorizeUrl()).append("?")
			.append("response_type=code") //이 요청에 의해 인가 code 를 받을것임을 알린다.
			.append("&client_id=").append(urlEncode(oAuthClient.getClientId())) // 클라이언트 아이디 추가
			.append("&redirect_uri=").append(urlEncode(oAuthClient.getRedirectUri())) // 콜백 받을 주소 추가
			.append("&scope=").append(urlEncode(oAuthClient.getScope())); //사용자 정보의 접근 범위 추가
			
		return sb.toString();
	}
	// 웹을통해 파라미터 전송시 문자열이 깨지지 않도록 인코딩 처리를 해주는 메서드
	private String urlEncode(String s) throws Exception{
		return URLEncoder.encode(s, "UTF-8");
	}
	
	/*--------------------------------------------------------------------------
	 * 클라이언트가 동의화면(최초 - 사용자)  또는 로그인( 기존) 요청이 들어오게되고, Provider가 이를 처리하는 과정에서
	 * 개발자가 등록해놓은 callback주소로 임시 코드(Authorize code)를 발급한다.
	 *--------------------------------------------------------------------------*/
	@GetMapping("/login/callback/google")
	public String handleCallback(String code) {
		/*--------------------------------------------------------------------------------
		 *  구글이 보내온 인증 코드와  , 나의 clientId, client Secret 를 조합하여, token을 요청하자.
		 *  결국 개발자가 원하는 것은 사용자의 정보 임으로 , 이 정보를 얻기 위해서는 토큰이 필요함으로 ..
		 *  
		 *-------------------------------------------------------------------------------- */
		log.debug("구글이 발급한 임시 코드는 ="+code);
		
		OAuthClient google =oauthClients.get("google");
		// 구글로부터 받은 임시코드와 나의 정보 (client id, client secret) 를 조합하여 구글에게 보내자.. (토큰을 받으려고)
		// 이때, 구글과 같은 프로바이더와 데이터를 주고 받기 위해서는 HTTP 통신규약을 지켜서 말을 걸때는 머리,몸을 구성하며 요청을 시도해야함
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
		param.add("code", code); // 구글로부터 받은 임시코드를 그대로 추가
		param.add("client_id",google.getClientId());	//전송할 id 추가
		param.add("client_secret", google.getClientSecret());	//전송할 pwd 추가
		param.add("redirect_uri", google.getAuthorizeUrl());		//callback url 추가
		
		HttpHeaders headers = new HttpHeaders();	//머리 만들기
		//아래와 같이 전송 파라미터에 대한 contentType 을 명시하면 , key=value&key=value 방식의 데이터 쌍으로 자동으로 변환
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		//머리와 본문(몸)을 합쳐서 하나의 HTTP 요청 엔터티로 결합 하여 보내자
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(param,headers);
		
		//구글에 요청 시작!! , 스프링에서는 Http요청 후 그 응답 정보를 java객체와 자동으로 매핑해주는 편리한 객체를 지원해 주는데,
		// 그 객체가 바로 RestTemplate (Http 요청 능력 + jackson능력 )
		/*
		 * restTemplate.postForEntity("google토큰발급주소", "요청객체(머리와 몸을 합친)", "결과를 받을 클래스");
		 */
		restTemplate.postForEntity(google.getTokenUrl(), request, 결과를 받을 클래스);
		
		return null;
	}
}
