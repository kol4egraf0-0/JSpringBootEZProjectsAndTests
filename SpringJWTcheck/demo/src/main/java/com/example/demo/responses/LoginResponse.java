package com.example.demo.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse { //это для стандартизированного ответа при аутентификации он возвращает клиенту:
    private String token;
    private long expiresIn;

    public LoginResponse(String token, long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }
}
