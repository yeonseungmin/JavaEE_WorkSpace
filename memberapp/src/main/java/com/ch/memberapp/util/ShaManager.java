package com.ch.memberapp.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/*이 클래스는 평문의 비밀번호를 암호화 시켜 해시로 결과를 반환해 주는 역활을 함
 * java의 암호화 처리는 JavaEE, JavaME 상관없이 JavaSE에서 지원.
 * */
public class ShaManager {
	//누군가가 매서드 호출시 평문을 넘겨주면, 암호화 알고리즘을 사용하여 그 값을 반환하는 매서드
	public static String getHash(String pwd){
		//String password ="minzino";
		
		StringBuffer hexString = new StringBuffer();
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			
			//아래의 매서드를 수행하면,아직 암호화 되지 않은 상태의 바이트 배열로 존재하는 데이터를
			// 함호화 시킴 - 반환값 : 32 바이트의 문자열을 반환함...
			byte[] hash = digest.digest(pwd.getBytes("utf-8"));//매개변수로 바이트 배열을 원함
			//최종적으로 암호화 결과를 모아놓을 객체
			for(int i=0; i<hash.length; i++) {
				//아래의 hash[i] 에 혹여나 , 1로 시작하는 이진수가 있다면 음수로 해석을 하므로, 예상치 못한 암호화 문자열이 반환
				//따라서 byte[i] 번째의 데이터를 양수로 전환하려면 앞에 int 형의 32비트와의 and 연산을 수행함
				String hex = Integer.toHexString(0xff & hash[i]); // 음수가 섞여 잇으면 양수로 전환
				
				//한자리수가 포함되면 64자 미만이 되기 때문에 '0'을 추가하여 64자를 맞추자
				if(hex.length()==1) {
					hexString.append("0");
				}
				
				hexString.append(hex);//누적..
			}
			//할일) 이 비밀번호 평문을 잘게 쪼개자.
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hexString.toString();//StringBuffer 자체가 String 이랑은 틀리므로 toString()메서드로 변환
	}
}

