package com.petsAdoption.wishList.handler;

import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.petsAdoption.wishList.controller")
public class ErrorHandler {
    @ExceptionHandler(RuntimeException.class)
    public Result<Void> errorHandler(RuntimeException e) {
        String message = e.getMessage();
        return new Result<>(false, ResultCode.ERROR_OCCUR, message);
    }
}
