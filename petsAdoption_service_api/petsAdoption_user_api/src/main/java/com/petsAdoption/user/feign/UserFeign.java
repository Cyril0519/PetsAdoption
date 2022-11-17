package com.petsAdoption.user.feign;

import com.petsAdoption.pojo.Result;
import com.petsAdoption.user.pojo.User;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(name = "user")
public interface UserFeign {
    @GetMapping("/user/queryById")
    public Result<User> queryById(@RequestParam("uid") String uid);
    @GetMapping("/user/queryByUsername")   // 赵六 123123
    public Result<User> queryByUsername(@RequestParam("username") String username);
    @PostMapping("/user/add")
    public Result add(@RequestBody User user);

    @PutMapping("/user/decreaseMoney")
    public Result<Void> decreaseMoney(@RequestParam("money") BigDecimal money);
    @PutMapping("/user/increaseMoney")
    public Result<Void> increaseMoney(@RequestParam("money") BigDecimal money);
}
