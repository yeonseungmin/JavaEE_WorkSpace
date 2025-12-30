package com.ch.shop.controller.shop;


import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ch.shop.dto.GoogleUser;
import com.ch.shop.dto.KakaoAccount;
import com.ch.shop.dto.KakaoUserResponse;
import com.ch.shop.dto.Member;
import com.ch.shop.dto.NaverUser;
import com.ch.shop.dto.NaverUserResponse;
import com.ch.shop.dto.OAuthClient;
import com.ch.shop.dto.OAuthTokenResponse;
import com.ch.shop.dto.Provider;
import com.ch.shop.model.member.MemberService;
import com.ch.shop.model.member.ProviderService;
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
	private RestTemplate restTemplate;	// http 요청능력 + 응답결과를 자동으로 java 객체로 매핑(마치 jackson처럼)
	@Autowired
	private MemberService memberService;
	@Autowired
	private ProviderService providerService;
	
	//회원 로그인폼 요청 처리
	@GetMapping("/member/loginform")
	public String getLoginForm(Model model) {

		
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
	public String handleGoogleCallback(String code,HttpSession session) {
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
		param.add("grant_type","authorization_code");			// 임시코드를 이용하여 토큰을 요청하겠다는 것을 명시
		param.add("code", code); 										// 구글로부터 받은 임시코드를 그대로 추가
		param.add("client_id",google.getClientId());				//전송할 id 추가
		param.add("client_secret", google.getClientSecret());	//전송할 pwd 추가
		param.add("redirect_uri", google.getRedirectUri());		//callback url 추가
		
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
		ResponseEntity<OAuthTokenResponse> response=restTemplate.postForEntity(google.getTokenUrl(), request, OAuthTokenResponse.class);
		log.debug("구글로 부터 받은 응답정보는 ={}",response.getBody());
		
		/*-----------------------------------------------------------------------------------------
		 * 얻어진 토큰으로 구글에 회원정보를 요청해보기
		 * -----------------------------------------------------------------------------------------*/
		OAuthTokenResponse responsebody=response.getBody();
		String access_token = responsebody.getAccess_token();
		log.debug("구글로부터 받은 access_token은 {}",access_token);
		
		/*-----------------------------------------------------------------------------------------
		 * 회원 정보 가져오기
		 * 구글에 요청을 시도하려면 역시나 이번에도 Http프로토콜의 형식을 갖춰야함
		 * -----------------------------------------------------------------------------------------*/
		HttpHeaders userInfoHeaders = new HttpHeaders();
		//내가 바로 토큰을 가진 자임을 알리는 헤더 속성값을 넣어야 함..
		userInfoHeaders.add("Authorization","Bearer "+access_token);
		HttpEntity<String> userInfoRequest = new HttpEntity<>("" ,userInfoHeaders);
		
		// 서버로부터 데이터를 가져오기 임으로  exchange() 사용
		ResponseEntity<GoogleUser> userInfoResponse=restTemplate.exchange(google.getUserInfoUrl(), HttpMethod.GET,userInfoRequest,GoogleUser.class);
		log.debug("사용자 정보는 {}",userInfoResponse);
		
		/*-----------------------------------------------------------------------------------------
		 * 얻어진 유저 정보를 이용하여 할일
		 * 1) 얻어진 회원이 우리의 mysql 존재하는지를 따져
		 * 있다면? 로그인 세션만 부여하고 홈페이지 메인으로 보내기
		 * 없다면? member테이블에 insert 하고 세션부여하고 홈페이지 메인으로 보내기
		 * -----------------------------------------------------------------------------------------
		 * */
		
		GoogleUser user=userInfoResponse.getBody();
		Member member = new Member();
		member.setProvider_userid(user.getId());
		member.setName(user.getName());
		member.setEmail(user.getEmail());
		//select* from provider where provider_name='google; 인 경우르 찾아야기에 ProviderMapper 로 이동
		Provider provider = providerService.selectByName("google");
		member.setProvider(provider);
	

		memberService.registOrUpdate(member);
		
//		/*------------------------------------------------속상한 코드--------------------------------------------*/
//		List topList=topCategoryService.getList();//3단계
//		//4단계 결과 페이지로 가져갈것이 있따.
//		model.addAttribute("topList", topList);
//		/*------------------------------------------------------------------------------------------------------*/
		
		
		//로그인에 성공하면 브라우저를 종료할때 까지는 자신의 정보를 접근할 수 있는 혜택을 부여해야하므로
		// 세션에 회원 정보를 담아 둬야 한다.
		//jsp 의 내장객체중 세션을 담당하는 내장객체명은 session 이고 , 서블릿에서 자료형은 HttpSession이다.
		//jsp 의 내장객체중 요청정보를 담당하는 내장객체명은 request 이고 , 서블릿에서 자료형은 HttpRequest이다.
		session.setAttribute("member", member);
		
		
		return "redirect:/";// 회원 로그인이 처리되면, 쇼핑몰의 메인으로 보내기
	}
	
	//네이버 로그인 (콜백함수)요청 처리
	@GetMapping("/login/callback/naver")
	public String handleNaverLogin(String code, HttpSession session){
		log.debug("네이버에서 발급한 임시코드는 ={}",code);
		/*-----------------------------------------------------------------------------------------
		1) code, client id, client secret 을 구성하여 토큰 발급을 요청
		  ---------------------------------------------------------------------------------------*/
		// 몸체 구성
		OAuthClient client =oauthClients.get("naver");
		MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
		param.add("grant_type","authorization_code");			// 임시코드를 이용하여 토큰을 요청하겠다는 것을 명시
		param.add("code", code); 										// 구글로부터 받은 임시코드를 그대로 추가
		param.add("client_id",client.getClientId());				//전송할 id 추가
		param.add("client_secret", client.getClientSecret());	//전송할 pwd 추가
		param.add("redirect_uri", client.getRedirectUri());		//callback url 추가
		
		//머리 만들기
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);	// 폼전송을위한 헤더값
		
		// 몸 +머리
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(param,headers);
		
		// 토큰 요청하기 (구글, 네이버, 카카오, 마타 등 거의 모든 프로바이더가 토큰을 포함한 응답정보의 내용이 같다.)
		ResponseEntity<OAuthTokenResponse> response = restTemplate.postForEntity(client.getTokenUrl(), request, OAuthTokenResponse.class);
		log.debug("네이버가 응답한 토큰 포함 정보는 ={}",response);
		OAuthTokenResponse responseBody = response.getBody();
		
		/*-----------------------------------------------------------------------------------------
		2) 발급된 토큰을 이용하여 회원 정보 조회하기
		  ---------------------------------------------------------------------------------------*/
		String access_token = responseBody.getAccess_token();
		log.debug("네이버의 토큰은 ={}",access_token);
		
		HttpHeaders userInfoHeaders = new HttpHeaders();
		userInfoHeaders.add("Authorization","Bearer "+access_token);
		
		HttpEntity<String> userInfoRequest = new HttpEntity<>("",userInfoHeaders);// 몸은 비워놓고, 몸과 머리 합쳐 요청 보내기
		//Get 방식으로 사용자 정보 요청하기
		//restTemplate.exchange(client.getUserInfoUrl(), HttpMethod.GET,userInfoRequest,매핑될클래스);
		ResponseEntity<NaverUserResponse> userInfoResponse = restTemplate.exchange(client.getUserInfoUrl(), HttpMethod.GET,userInfoRequest,NaverUserResponse.class);
		
		NaverUserResponse naverUserResponse = userInfoResponse.getBody();
		NaverUser naverUser = naverUserResponse.getResponse();
		
		log.debug("고유 id= {}", naverUser.getId());
		log.debug("이름 = {}", naverUser.getName());
		log.debug("이메일 = {}", naverUser.getEmail());
		
		/*-----------------------------------------------------------------------------------------
		3) 로그인 처리
				- 최초의 로그인 시도자는 회원가입을 처리
				- 기존 가입자는, 로그인만 처리( 회원 정보 업데이트)
				- 세션에 회원정보 저장
		  ---------------------------------------------------------------------------------------*/

		Member member = new Member();
		member.setProvider_userid(naverUser.getId());
		member.setName(naverUser.getName());
		member.setEmail(naverUser.getEmail());
		//select* from provider where provider_name='google; 인 경우르 찾아야기에 ProviderMapper 로 이동
		Provider provider = providerService.selectByName(client.getProvider());
		member.setProvider(provider);
	

		memberService.registOrUpdate(member);
		
		session.setAttribute("member", member);
		
		
		return "redirect:/";
	}
	// 카카오 로그인 (콜백)
	@GetMapping("/login/callback/kakao")
	public String handleKakaoLogin(String code, HttpSession session) {

	    OAuthClient client = oauthClients.get("kakao");

	    // 1️⃣ 토큰 요청
	    MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
	    param.add("grant_type", "authorization_code");
	    param.add("code", code);
	    param.add("client_id", client.getClientId());
	    param.add("client_secret", client.getClientSecret());
	    param.add("redirect_uri", client.getRedirectUri());

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	    OAuthTokenResponse tokenResponse =
	        restTemplate.postForEntity(
	            client.getTokenUrl(),
	            new HttpEntity<>(param, headers),
	            OAuthTokenResponse.class
	        ).getBody();

	    String accessToken = tokenResponse.getAccess_token();

	    // 2️⃣ 사용자 정보 요청
	    HttpHeaders userInfoHeaders = new HttpHeaders();
	    userInfoHeaders.add("Authorization", "Bearer " + accessToken);

	    ResponseEntity<KakaoUserResponse> userInfoResponse =
	        restTemplate.exchange(
	            client.getUserInfoUrl(),
	            HttpMethod.GET,
	            new HttpEntity<>("", userInfoHeaders),
	            KakaoUserResponse.class
	        );

	    KakaoUserResponse kakao = userInfoResponse.getBody();

	    String providerUserId = kakao.getId().toString();
	    String nickname = kakao.getKakao_account()
	                           .getProfile()
	                           .getNickname();

	    // 3️⃣ 로그인 / 회원가입
	    Member member = new Member();
	    member.setProvider_userid(providerUserId);
	    member.setName(nickname);
	    member.setEmail(null); // ⭐ 닉네임만 사용

	    Provider provider = providerService.selectByName("kakao");
	    member.setProvider(provider);

	    memberService.registOrUpdate(member);
	    session.setAttribute("member", member);

	    return "redirect:/";
	}


}
