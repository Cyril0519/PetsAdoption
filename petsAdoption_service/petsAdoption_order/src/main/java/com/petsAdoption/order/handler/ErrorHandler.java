package com.petsAdoption.order.handler;

import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.petsAdoption.order.controller")
public class ErrorHandler {
    @ExceptionHandler(value = RuntimeException.class)
    public Result<Void> errorHandler(RuntimeException e) {
        String message = e.getMessage();
        return new Result<>(false, ResultCode.ERROR_OCCUR, message);
    }
}
