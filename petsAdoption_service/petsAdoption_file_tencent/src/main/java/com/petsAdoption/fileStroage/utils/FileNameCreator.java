package com.petsAdoption.fileStroage.utils;

import java.util.Random;

public class FileNameCreator {
    // 随机文件名生成且不重复
    public synchronized String nameCreator(int workerId){
        long nowTime = System.currentTimeMillis() % (long)1e8;  //当前时间戳后八位
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        Random random = new Random();
        // 最后的结果
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            // 随机选择chars
            int i1 = random.nextInt(chars.length);
            sb.append(chars[i1]);
        }
        sb.append(nowTime);
        sb.append(workerId);
        return sb.toString();
    }
}
