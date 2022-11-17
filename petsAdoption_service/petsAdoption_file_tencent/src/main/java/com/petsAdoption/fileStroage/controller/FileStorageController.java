package com.petsAdoption.fileStroage.controller;

import com.petsAdoption.fileStroage.service.FileStorageService;
import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/file")
public class FileStorageController {
    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/uploadImg")
    public Result<String> uploadFile(@RequestParam("imgFile") MultipartFile file) {
        if (file == null) {
            return new Result<>(false, ResultCode.ERROR_OCCUR, "图片不存在");
        }
        String fileLocation = fileStorageService.uploadFile(file);
        return new Result<>(true, ResultCode.OK, "上传成功", fileLocation);
    }

    @DeleteMapping("/deleteImg")
    public Result<Void> deleteImg(@RequestParam("key") String key) {
        fileStorageService.deleteByKey(key);
        return new Result<>(true, ResultCode.OK, "删除成功");
    }
}
