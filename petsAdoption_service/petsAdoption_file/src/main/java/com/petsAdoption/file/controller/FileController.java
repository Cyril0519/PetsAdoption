package com.petsAdoption.file.controller;

import com.petsAdoption.file.util.FastDFSClient;
import com.petsAdoption.file.util.FastDFSFile;
import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {
    @PostMapping("/uploadImg")
    public Result uploadFile(@RequestParam("imgFile") MultipartFile file) {
        try {
            //判断文件是否存在
            if (file == null) {
                throw new RuntimeException("文件不存在");
            }
            //获取文件的完整名称
            String originalFilename = file.getOriginalFilename();
            if (StringUtils.isEmpty(originalFilename)) {
                throw new RuntimeException("文件不存在");
            }

            //获取文件的扩展名称  abc.jpg   jpg
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

            //获取文件内容
            byte[] content = file.getBytes();

            //创建文件上传的封装实体类
            FastDFSFile fastDFSFile = new FastDFSFile(originalFilename, content, extName);

            //基于工具类进行文件上传,并接受返回参数  String[]
            String[] uploadResult = FastDFSClient.upload(fastDFSFile);

            //封装返回结果
            String url = FastDFSClient.getTrackerUrl() + uploadResult[0] + "/" + uploadResult[1];
            return new Result(true, ResultCode.OK, "文件上传成功", url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, ResultCode.ERROR_OCCUR, "文件上传失败");
    }

    @DeleteMapping("/deleteFile")
    public Result deleteFile(@RequestParam("url") String url) throws Exception {
        String[] groupNameAndRemoteFileNameFromUrl = FastDFSClient.getGroupNameAndRemoteFileNameFromUrl(url);
        String groupName = groupNameAndRemoteFileNameFromUrl[0];
        String remoteFileName = groupNameAndRemoteFileNameFromUrl[1];
        int i = FastDFSClient.deleteFile(groupName, remoteFileName);
        if (i == 0) {
            return new Result(true, ResultCode.OK, "删除成功");
        }else {
            return new Result(false, ResultCode.ERROR_OCCUR, "删除失败");
        }
    }
}
