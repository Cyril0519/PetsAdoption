package com.petsAdoption.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petsAdoption.user.pojo.User;
import com.petsAdoption.user.service.UserService;
import com.petsAdoption.user.mapper.UserMapper;
import com.sun.org.apache.regexp.internal.REUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author wuxingyu
* @description 针对表【t_user(用户表)】的数据库操作Service实现
* @createDate 2022-08-22 21:04:46
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;


    /*
     * @Description: 查询用户名是否存在，后期允许用户名重复
       * @param username
     * @return: com.petsAdoption.user.pojo.User ，返回查询到的用户
     * @Author: wuxingyu
     * @Date: 2022/10/20 16:38
     */
    @Override
    public User queryByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        List<User> users = userMapper.selectList(queryWrapper);
        if (users == null || users.size() == 0) {
            return null;
        }
        return users.get(0);
    }

    /*
     * @Description: 根据uid，money来减少用户的金额
       * @param uid 用户唯一标识符
       * @param money 减少的金额
     * @return: void
     * @Author: wuxingyu
     * @Date: 2022/10/20 16:39
     */
    @Override
    public void decreaseMoney(String uid, BigDecimal money) {
//        获取用户余额
        User user = this.getById(uid);
        BigDecimal userMoney = user.getMoney();
        if (userMoney.subtract(money).compareTo(BigDecimal.ZERO) >= 0){
//            用户金额大于等于支付金额
            UpdateWrapper<User> query = new UpdateWrapper<>();
            query.eq("uid",uid).set("money",userMoney.subtract(money));
            this.update(query);
        }else{
            throw new RuntimeException("支付失败,您的余额不足");
        }
    }

    /*
     * @Description: 增加用户金额，例如充值等情况
       * @param uid ：增加的用户uid
       * @param money 增加的金额
     * @return: void
     * @Author: wuxingyu
     * @Date: 2022/10/20 16:40
     */
    @Override
    public void increaseMoney(String uid, BigDecimal money) {
        //        获取用户余额
        User user = this.getById(uid);
        BigDecimal userMoney = user.getMoney();
        BigDecimal moneyToIncrease = money;
        BigDecimal totalMoney = userMoney.add(moneyToIncrease);
        //更新
        UpdateWrapper<User> query = new UpdateWrapper<>();
        query.eq("uid",uid).set("money",totalMoney);
        this.update(query);
    }

    @Override
    public Page<User> selectPage(Integer current, Integer size) {
        Page<User> userPage = new Page<>(current, size);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_account_locked", 1);
        Page<User> userPage1 = userMapper.selectPage(userPage, queryWrapper);
        return userPage1;
    }

    @Override
    public void lockUser(String id) {
        UpdateWrapper<User> query = new UpdateWrapper<>();
        query.eq("uid", id);
        query.set("is_account_locked", 1);
        this.update(query);
    }

    @Override
    public void unlockUser(String id) {
        UpdateWrapper<User> query = new UpdateWrapper<>();
        query.eq("uid", id);
        query.set("is_account_locked", 0);
        this.update(query);
    }
}




