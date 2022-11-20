package com.petsAdoption.user.serivce;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petsAdoption.user.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
* @author wuxingyu
* @description 针对表【t_user(用户表)】的数据库操作Service
* @createDate 2022-08-22 21:04:46
*/
public interface UserService extends IService<User> {

    User queryByUsername(String username);

    void decreaseMoney(String uid, BigDecimal money);

    void increaseMoney(String uid, BigDecimal money);

    Page<User> selectPage(Integer current, Integer size);

    void lockUser(String id);

    public void unlockUser(String id);
}
