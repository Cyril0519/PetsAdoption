package com.petsAdoption.fileStroage.service.impl;

import com.petsAdoption.fileStroage.service.FileStorageService;
import com.petsAdoption.fileStroage.utils.FileNameCreator;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    @Autowired
    private COSClient cosClient;
    @Autowired
    private FileNameCreator fileNameCreator;
    String bucketName = "petsadoptoin-1313269480";

    /*
     * @Description:  返回文件上传到的位置
       * @param file
     * @return: java.lang.String
     * @Author: wuxingyu
     * @Date: 2022/10/22 15:32
     */
    @Override
    public String uploadFile(MultipartFile file) {
        if (file == null){
            throw new RuntimeException("file not exist");
        }
        // 指定要上传的文件
        // 获取后缀名
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null){
            throw new RuntimeException("文件名无效");
        }
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        // 指定上传文件名
        String fileName = fileNameCreator.nameCreator(2) + "."+ extName;
        // 上传位置
        String key = "petsImages/" + fileName;

        // 指定文件将要存放的存储桶
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file.getInputStream(), null);
            cosClient.putObject(putObjectRequest);

            return key;
        } catch (IOException e) {
            throw new RuntimeException("图片无效");
        }

    }

    @Override
    public void deleteByKey(String key) {
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        // 指定被删除的文件在 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示删除位于 folder 路径下的文件 picture.jpg
        cosClient.deleteObject(bucketName, key);
    }
}
