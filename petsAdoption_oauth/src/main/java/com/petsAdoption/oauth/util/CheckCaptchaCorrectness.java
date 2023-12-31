package com.petsAdoption.oauth.util;

import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

public class CheckCaptchaCorrectness {
    @Autowired
    private static StringRedisTemplate redisTemplate;


    public static Result<Void> check(String uuid, String kaptchaFromUser) {
        if (StringUtils.isEmpty(uuid)) {
            throw new RuntimeException("uuid不存在");
        }
        if (StringUtils.isEmpty(kaptchaFromUser)) {
            throw new RuntimeException("验证码不存在");
        }
        String kaptchaFromSystem = redisTemplate.boundValueOps("kaptcha." + uuid).get();
        if (kaptchaFromSystem==null){
            return new Result<>(false, ResultCode.ERROR_OCCUR, "验证码已过期");
        }
        if(!kaptchaFromSystem.equalsIgnoreCase(kaptchaFromUser)){
            return new Result<Void>(false, ResultCode.ERROR_OCCUR, "验证码错误或者已过期");
        }
        return new Result<>(true, ResultCode.OK, "正确");
    }
}
