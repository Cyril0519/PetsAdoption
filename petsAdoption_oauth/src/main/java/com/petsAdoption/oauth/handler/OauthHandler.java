package com.petsAdoption.oauth.handler;

import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.petsAdoption.oauth.controller")
public class OauthHandler {
    @ExceptionHandler(Exception.class)
    public Result<Void> errorhandler(Exception e) {
        e.printStackTrace();
        String message = e.getMessage();
        return new Result<>(false, ResultCode.ERROR_OCCUR, message);
    }
}
