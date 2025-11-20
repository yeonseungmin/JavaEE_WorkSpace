package com.ch.site1118.util;

import java.security.MessageDigest;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



//이메일을 발송해주는 객체 정의
//javaSE 기반의 메일을 전송해주는 API 가 .jar 형태로 지원됨
//activation-1.1.1.jar,javax.mail-1.5.5.jar
public class EmailManager {
	String host ="smtp.gmail.com"; //사용하고자 하는 메일 주소
	String user = "to20864548@gmail.com";//메일 서버의 사용자 계정
	String passward = "rqsh wfyc lsgw bqzq";//앱비밀번호
	Properties props =new Properties(); // java.util.map의 자식 key-value 쌍을 갖는 데이터 형식
	
	//메일 발송 메서드 
	//to 메개변수로 메일 받을 회원가입한자
	public void send(String to) {
		//props 객체에 필요한 모든 설정 정보의 쌍을 대입하자.
		//참고로 key 값은 이미 정해져 있어서 아래를 따르자 key,value
		props.put("mail.smtp.host",host);
		props.put("mail.smtp.port",465);//구글 smtp(보내는 메일 서버)의 포트번호
		props.put("mail.smtp.auth","true");
		props.put("mail.smtp.ssl.enable","true");
		props.put("mail.smtp.ssl.protocols","TLSv1.2");//TLS 버전 강제
		
		//Session생성 javax.mail
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication(user,passward);
			}
		});
		//제목, 내용 등의 메일작성
		MimeMessage message = new MimeMessage(session);
		//메일 발송자
		try {
			message.setFrom(new InternetAddress(user));
			//이메일 받을 사람 
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("회원가입 축하 드립니다.");
			message.setContent("<h3>덩훈~덩훈~</h3>","text/html;charset=utf-8");
			//메일 발송 시점!!
			Transport.send(message);
			
			System.out.println("이메일 발송 성공");
		} catch (Exception e) {
			System.out.println("이메일 발송 실패");
		}
	}
}
