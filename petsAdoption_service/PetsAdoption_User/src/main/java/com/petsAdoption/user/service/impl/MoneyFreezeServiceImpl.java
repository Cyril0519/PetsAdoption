package com.petsAdoption.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petsAdoption.user.pojo.MoneyFreeze;
import com.petsAdoption.user.mapper.MoneyFreezeMapper;
import com.petsAdoption.user.serivce.UserService;
import com.petsAdoption.user.service.MoneyFreezeService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author wuxingyu
 * @description 针对表【t_money_freeze】的数据库操作Service实现
 * @createDate 2022-11-12 17:13:33
 */
@Service
public class MoneyFreezeServiceImpl extends ServiceImpl<MoneyFreezeMapper, MoneyFreeze> implements MoneyFreezeService {
    @Autowired
    private MoneyFreezeMapper moneyFreezeMapper;
    @Autowired
    private UserService userService;
    @Override

    public void decreaseMoney(String uid, BigDecimal money) {
        // 0.获取事务id
        String xid = RootContext.getXID();
        System.out.println(xid);
        // 1.判断freeze中是否有冻结记录
        MoneyFreeze oldFreeze = moneyFreezeMapper.selectById(xid);
        if (oldFreeze != null) {
            // 说明已经被处理过了,不用再处理了
            return;
        }

        // 1.扣减剩余数量
        userService.decreaseMoney(uid, money);
        // 2.记录冻结金额，事务状态
        MoneyFreeze freeze = new MoneyFreeze();
        freeze.setUserId(uid);
        freeze.setFreezeMoney(money);
        freeze.setState(MoneyFreeze.TRY);
        freeze.setXid(xid);
        moneyFreezeMapper.insert(freeze);
    }

    @Override
    public boolean confirm(BusinessActionContext ctx) {
        // 1.获取事务id
        String xid = ctx.getXid();
        // 2.根据id删除冻结记录
        int count = moneyFreezeMapper.deleteById(xid);
        return count == 1;
    }

    @Override
    public boolean cancel(BusinessActionContext ctx) {
        // 0.查询冻结记录
        String xid = ctx.getXid();
        MoneyFreeze freeze = moneyFreezeMapper.selectById(xid);
        String uid = ctx.getActionContext("uid").toString();

        // 空回滚判断，如果freeze为null，则try还没有执行，需要空回滚
        if (freeze == null) {
            freeze = new MoneyFreeze();
            freeze.setUserId(uid);
            freeze.setFreezeMoney(new BigDecimal(0));
            freeze.setState(MoneyFreeze.CANCEL);
            freeze.setXid(xid);
            moneyFreezeMapper.updateById(freeze);
            return true;
        }

        // 幂等性判断
        if (freeze.getState().equals(MoneyFreeze.CANCEL)) {
            // 相等则证明已经处理过，无序再处理
            return true;
        }

        // 1.恢复可用余额
        userService.increaseMoney(freeze.getUserId(), freeze.getFreezeMoney());
        // 2.将冻结金额清零，状态改为CANCEL
        freeze.setFreezeMoney(new BigDecimal(0));
        freeze.setState(MoneyFreeze.CANCEL);
        int count = moneyFreezeMapper.updateById(freeze);
        return count == 1;
    }
}




