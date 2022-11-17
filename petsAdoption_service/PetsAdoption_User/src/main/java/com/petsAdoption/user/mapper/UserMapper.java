package com.petsAdoption.user.mapper;
import java.math.BigDecimal;
import org.apache.ibatis.annotations.Param;

import com.petsAdoption.user.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author wuxingyu
* @description 针对表【t_user(用户表)】的数据库操作Mapper
* @createDate 2022-08-22 21:04:46
* @Entity com.petsAdoption.user.pojo.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    int updateMoneyByUid(@Param("money") BigDecimal money, @Param("uid") String uid);
}




