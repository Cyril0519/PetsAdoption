package com.petsAdoption.file.hanlder;

import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FileErrorHandler {
    @ExceptionHandler(Exception.class)
    public Result errorHandler(Exception e) {
        e.printStackTrace();
        return new Result(false, ResultCode.ERROR_OCCUR, "文件系统繁忙，请稍后重试");
    }
}
