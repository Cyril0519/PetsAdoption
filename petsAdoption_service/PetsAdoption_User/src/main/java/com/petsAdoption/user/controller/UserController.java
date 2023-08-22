package com.petsAdoption.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petsAdoption.pojo.Result;
import com.petsAdoption.pojo.ResultCode;

import com.petsAdoption.user.config.TokenDecode;
import com.petsAdoption.user.pojo.User;
import com.petsAdoption.user.serivce.UserService;
import com.petsAdoption.user.service.MoneyFreezeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenDecode tokenDecode;
    @Autowired
    private MoneyFreezeService moneyFreezeService;

    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody User user) {
        userService.save(user);
        return new Result<Void>(true, ResultCode.OK, "注册成功");
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public Result<Void> delete(String uid) {
//        bcript加密
        userService.removeById(uid);
        return new Result<>(true, ResultCode.OK, "注销成功");
    }

    @PutMapping("/update")
    @ResponseBody
    public Result<String> update(@RequestBody User user) {
        if (!StringUtils.isEmpty(user.getUsername())) {
            return new Result<>(false, ResultCode.ERROR_OCCUR, "更新失败", "请求频繁，请稍后再试");
        }

        String uid = this.getUserFromUid().getData().getUid();
        user.setUid(uid);
        userService.updateById(user);
        return new Result<>(true, ResultCode.OK, "更新成功");
    }

    @GetMapping("/getInfo")
    @ResponseBody
    public Result<Map<String,String>> getInfo() {
        String id = tokenDecode.getUserInfo().get("id");
        User user = userService.getById(id);
        Map<String, String> map = new HashMap<>();
        map.put("roles", user.getAuthority());
        map.put("name", user.getUsername());
        map.put("avatar", user.getProfile());
        map.put("introduction", user.getAddress());
        return new Result<>(true, ResultCode.OK, "查询成功", map);
    }

    @GetMapping("/queryById")   // 赵六 123123
    @ResponseBody
    public Result<User> queryById(@RequestParam("uid") String uid) {
        User user = userService.getById(uid);
        return new Result<>(true, ResultCode.OK, "查询成功", user);
    }

    @GetMapping("/queryByUsername")   // 赵六 123123
    @ResponseBody
    public Result<User> queryByUsername(@RequestParam("username") String username) {
        User user = userService.queryByUsername(username);
        return new Result<>(true, ResultCode.OK, "查询成功", user);
    }

    @GetMapping("/existUsername")
    @ResponseBody
    public int existUsername(@RequestParam("username") String username) {
        User user = userService.queryByUsername(username);
        if (user == null) {
            return 0;
        } else {
            return 1;
        }
    }

    @GetMapping("/getUsernameFromUid")
    @ResponseBody
    public Result<String> getUsernameFromUid() {
        String username = tokenDecode.getUserInfo().get("username");
        return new Result<>(true, ResultCode.OK, "查询成功", username);
    }

    @GetMapping("/getUserFromUid")
    @ResponseBody
    public Result<User> getUserFromUid() {
        String username = this.getUsernameFromUid().getData();
        User user = this.queryByUsername(username).getData();
        return new Result<>(true, ResultCode.OK, "查询成功", user);
    }

    @GetMapping("/getUsername")
    @ResponseBody
    public Result<String> getUsername() {
        String username = tokenDecode.getUserInfo().get("username");
        return new Result<>(true, ResultCode.OK, "查询成功", username);
    }

    /*
     * @Description: 减少用户指定的余额
       * @param money 要减少的余额
     * @return: com.petsAdoption.pojo.Result<java.lang.Void>
     * @Author: wuxingyu
     * @Date: 2022/10/16 15:06
     */
    @PutMapping("/decreaseMoney")
    @ResponseBody
    public Result<Void> decreaseMoney(@RequestParam("money") BigDecimal money) {
        // BigDecimal money1 = new BigDecimal(money);
        String uid = tokenDecode.getUserInfo().get("id");
        moneyFreezeService.decreaseMoney(uid, money);
        return new Result<>(true, ResultCode.OK, "扣款成功");
    }

    /*
     * @Description: 添加用户余额
       * @param money ：要添加的余额
     * @return: com.petsAdoption.pojo.Result<java.lang.Void>
     * @Author: wuxingyu
     * @Date: 2022/10/16 15:06
     */
    @PutMapping("/increaseMoney")
    @ResponseBody
    public Result<Void> increaseMoney(@RequestParam("money") BigDecimal money) {
        String uid = tokenDecode.getUserInfo().get("id");
        userService.increaseMoney(uid, money);
        return new Result<>(true, ResultCode.OK, "添加成功");
    }

    // ---------一下权限判断
    @GetMapping("/getUnusualUser/{current}/{size}")
    @ResponseBody
    public Result<Page<User>> getUnusualUser(@PathVariable("current") Integer current, @PathVariable("size") Integer size) {
        Page<User> userPage = userService.selectPage(current, size);
        return new Result<>(true, ResultCode.OK, "查询成功", userPage);
    }

    @PutMapping("/lockUser")
    @ResponseBody
    public Result<Void> lockUser(@RequestParam("id") String id) {
        userService.lockUser(id);
        return new Result<>(true, ResultCode.OK, "锁定成功");
    }

    @PutMapping("/unlockUser")
    @ResponseBody
    public Result<Void> unlockUser(@RequestParam("id") String id) {
        userService.unlockUser(id);
        return new Result<>(true, ResultCode.OK, "解锁成功");
    }
}
