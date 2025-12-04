package com.ch.model1.openapi;

/* Java 1.8 샘플 코드 */


import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.catalina.mapper.Mapper;

import com.ch.model1.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B553662/sghtngPoiInfoService/getSghtngPoiInfoList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=z8tNBuvu83117h0V7Zr8WVVRD0OEhI7xpgXJcFgSB8esNPUN3Tq45nE6PIMZ6hHpw6TsRAbHlgKHN9iw4P9zoA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*응답 결과의 출력 방식을 xml, json 형태로 변환 제공될 수 있도록 함*/
        urlBuilder.append("&" + URLEncoder.encode("srchFrtrlNm","UTF-8") + "=" + URLEncoder.encode("가지산", "UTF-8")); /*검색하고자 하는 숲길명 “TEXT”*/
        urlBuilder.append("&" + URLEncoder.encode("srchPlaceTpeCd","UTF-8") + "=" + URLEncoder.encode("CULTURAL", "UTF-8")); /*검색하고자 하는 장소유형코드 “TEXT” CULTURAL:문화자원*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        
        //오픈 API 서버로부터 응답받은 경과는 결국 웹상으로 받았기 때문에, 문자열에 불과하다..
        //하지만 문자열은 현실을 반영한 객체가 아니므로(그냥글씨) 객체처럼 점(.) 찍고 사용할수 없다.
        //이 시점부터 서버로부터 받은 문자열이 만일 JSON 문자열 표기법을 준수했다면, 자바의 객체로 자동 매핑할 기회가 있다.
        //우리가 받은 데이터를 Json 검증기 에서 확인했기에, 문자열을 자바 객체로 자동변환하는 라이브러리 인 jackson을 사용해 보자.
        ObjectMapper mapper = new ObjectMapper();
        ApiResponse data = mapper.readValue(sb.toString(),ApiResponse.class);
        										//첫번째 매게변수엔 서버로부터 가져온 json문자열을 넣고
        										// 두번째 매게변수엔 그결과를 받을 DTO 를 대입해야 한다.
        //모든 데이터가 각각 자동으로 채워진 상태이며 데이터에 대한 접근은 최상위 객체로 부터 점차적으로 접근하면 된다.
        String placeNm=data.getResponse().getBody().getItems().getItem().get(0).getPlaceNm();
        System.out.println(placeNm);
        //System.out.println(sb.toString());
        
        
    }
}
