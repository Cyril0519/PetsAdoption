package com.petsAdoption.oauth.controller;


import com.google.code.kaptcha.Producer;
import com.petsAdoption.oauth.service.AuthService;
import com.petsAdoption.oauth.util.AuthToken;
import com.petsAdoption.oauth.util.CookieUtil;
import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;
import com.petsAdoption.user.pojo.User;
import com.petsAdoption.user.serivce.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/oauth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private Producer kaptchaProducer;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @DubboReference
    private UserService userService;

    @Value("${auth.clientId}")
    private String clientId;
    @Value("${auth.clientSecret}")
    private String clientSecret;
    @Value("${auth.cookieDomain}")
    private String cookieDomain;
    @Value("${auth.cookieMaxAge}")
    private Integer cookieMaxAge;

    @RequestMapping("/toLogin")
    public String toLogin(@RequestParam(value = "from", required = false, defaultValue = "") String from, Model model) {
        model.addAttribute("from", from);
        return "login";
    }

    @RequestMapping("/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, String> params, HttpServletResponse response) {
        // 校验参数

        String username = params.get("username");
        String rowPassword = params.get("password");
        String password = new String(Base64Utils.decodeFromString(rowPassword));
        String kaptchaFromUser = params.get("code");
        String uuid = params.get("uuid");
        if (StringUtils.isEmpty(username)) {
            throw new RuntimeException("请输入用户名");
        }
        if (StringUtils.isEmpty(password)) {
            throw new RuntimeException("请输入密码");
        }
        if (StringUtils.isEmpty(kaptchaFromUser)) {
            throw new RuntimeException("请输入验证码");
        }
        if (StringUtils.isEmpty(uuid)) {
            throw new RuntimeException("uuid不存在");
        }
        // 校验验证码
        String kaptchaFromSystem = redisTemplate.boundValueOps("kaptcha." + uuid).get();
        if (kaptchaFromSystem==null){
            return new Result<Void>(false, ResultCode.ERROR_OCCUR, "验证码错误或者已过期");
        }
        if(!kaptchaFromSystem.equalsIgnoreCase(kaptchaFromUser)){
            return new Result<Void>(false, ResultCode.ERROR_OCCUR, "验证码错误或者已过期");
        }

        AuthToken authToken = authService.login(username, password, clientId, clientSecret);
        // 将jti的值存入cookie
        this.saveJtiToCookie(authToken.getJti(), response);
        // 返回结果
        return new Result<>(true, ResultCode.OK, "登录成功", authToken.getJti());

    }

    @RequestMapping("/toRegister")
    public String toRegister(@RequestParam(value = "from", required = false) String from, Model model) {
        model.addAttribute("from", from);
        return "register";
    }

    @RequestMapping("/register")
    @ResponseBody
    public Result<String> register(@RequestBody Map<String, String> params, HttpServletResponse response){
        String uuid = params.get("uuid");
        String codeFromUser = params.get("captcha");
        if(!this.isCorrectCaptcha(uuid, codeFromUser)){
            return new Result<>(false, ResultCode.ERROR_OCCUR, "验证码错误或已经超时");
        }
        // 用户名
        String username = params.get("username");
        // 密码
        String passwordRow = params.get("password");
        byte[] bytes = Base64Utils.decodeFromString(passwordRow);
        String password = new String(bytes);
            // bcrypt加密
        String passwordEncode = passwordEncoder.encode(password);
        // 性别
        String gender = params.get("gender");
        // 邮箱
        String email = params.get("email");
        // 注册事件
        Date date = new Date();

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncode);
        user.setGender(gender);
        user.setEmail(email);
        user.setRegisterDate(date);
        user.setAuthority("normal");
        user.setCaringIndex(new BigDecimal(0));
        boolean flag = userService.save(user);
        if(flag){
            // 注册成功
            try {
                AuthToken authToken = authService.login(username, password, clientId, clientSecret);
                this.saveJtiToCookie(authToken.getJti(), response);
                // 返回结果
                return new Result<String>(true, ResultCode.OK, "登录成功", authToken.getJti());
            } catch (Exception e) {
                e.printStackTrace();
                return new Result<>(false, ResultCode.ERROR_OCCUR, "注册成功,请登录");
            }
        }else{
            return new Result<>(false, ResultCode.ERROR_OCCUR, "注册失败");
        }

    }
    // 将令牌的短标识jti存入cookie
    private void saveJtiToCookie(String jti, HttpServletResponse response) {
        CookieUtil.addCookie(response, cookieDomain, "/", "uid", jti, cookieMaxAge, false);
    }

    //生成验证码图片
    @GetMapping("/kaptcha")
    public void getKaptcha(@RequestParam("uuid") String uuid,HttpServletResponse response){
        System.out.println(uuid);
        //    生成验证码
        String text= kaptchaProducer.createText();
        //    生成验证码图片
        BufferedImage image = kaptchaProducer.createImage(text);
        //     保存redis中
        redisTemplate.opsForValue().set("kaptcha."+uuid,text,2*60, TimeUnit.SECONDS); // 2分钟过期时间

        response.setContentType("image/png");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("响应验证码失败");
        }
    }


    // 验证码有效性判断
    private boolean isCorrectCaptcha(String uuid, String codeFromUser) {
        String codeFromSystem = redisTemplate.boundValueOps("kaptcha." + uuid).get();
        return codeFromSystem != null && codeFromSystem.equalsIgnoreCase(codeFromUser);
    }
}
