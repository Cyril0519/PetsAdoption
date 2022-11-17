package com.petsAdoption.fileStroage.handler;

import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FileErrorHandler {
    @ExceptionHandler(RuntimeException.class)
    public Result<Void> errorHandler(Exception e) {
        e.printStackTrace();
        String message = e.getMessage();
        return new Result<>(false, ResultCode.ERROR_OCCUR, message);
    }

}
