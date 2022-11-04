package com.example.githuboauth2.exception;

// 자원 없음
public class OAuth2NotFoundException extends RuntimeException{
    public OAuth2NotFoundException(String message){
        super(message);
    }
}
