package com.ch.shop.dto;

import lombok.Data;

/*
{
  "id": 123456789,
  "connected_at": "2024-01-01T12:00:00Z",
  "kakao_account": {
    "email": "test@kakao.com",
    "email_needs_agreement": false,
    "profile": {
      "nickname": "카카오유저"
    }
  }
}
*/

@Data
public class KakaoUserResponse {
    private Long id;
    private KakaoAccount kakao_account;
}
