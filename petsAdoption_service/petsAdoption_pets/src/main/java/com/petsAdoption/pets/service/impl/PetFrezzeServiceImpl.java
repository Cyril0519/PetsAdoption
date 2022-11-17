package com.petsAdoption.pets.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.petsAdoption.pets.mapper.PetsDetailMapper;
import com.petsAdoption.pets.pojo.PetFrezze;
import com.petsAdoption.pets.service.PetFrezzeService;
import com.petsAdoption.pets.mapper.PetFrezzeMapper;
import com.petsAdoption.pets.service.PetsDetailService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author wuxingyu
* @description 针对表【t_pet_frezze】的数据库操作Service实现
* @createDate 2022-11-12 15:31:26
*/
@Service
public class PetFrezzeServiceImpl extends ServiceImpl<PetFrezzeMapper, PetFrezze> implements PetFrezzeService{
    @Autowired
    private PetsDetailService petsDetailService;
    @Autowired
    private PetFrezzeMapper petFrezzeMapper;

    @Override
    @Transactional
    public void deduct(String petId, int count) {
        // 0.获取事务id
        String xid = RootContext.getXID();
        System.out.println(xid);
        // 1.判断freeze中是否有冻结记录
        PetFrezze oldFreeze = petFrezzeMapper.selectById(xid);
        if (oldFreeze != null) {
            // 说明已经被处理过了,不用再处理了
            return;
        }

        // 1.扣减剩余数量
        petsDetailService.deduct(petId, count);
        // 2.记录冻结金额，事务状态
        PetFrezze freeze = new PetFrezze();
        freeze.setPetId(petId);
        freeze.setPetCount(count);
        freeze.setState(PetFrezze.TRY);
        freeze.setXid(xid);
        petFrezzeMapper.insert(freeze);
    }

    @Override
    public boolean confirm(BusinessActionContext ctx) {
        // 1.获取事务id
        String xid = ctx.getXid();
        // 2.根据id删除冻结记录
        int count = petFrezzeMapper.deleteById(xid);
        return count == 1;
    }

    @Override
    public boolean cancel(BusinessActionContext ctx) {
        // 0.查询冻结记录
        String xid = ctx.getXid();
        PetFrezze freeze = petFrezzeMapper.selectById(xid);
        String petId = ctx.getActionContext("petId").toString();

        // 空回滚判断，如果freeze为null，则try还没有执行，需要空回滚
        if (freeze == null) {
            freeze = new PetFrezze();
            freeze.setPetId(petId);
            freeze.setPetCount(0);
            freeze.setState(PetFrezze.CANCEL);
            freeze.setXid(xid);
            petFrezzeMapper.updateById(freeze);
            return true;
        }

        // 幂等性判断
        if (freeze.getState().equals(PetFrezze.CANCEL)) {
            // 相等则证明已经处理过，无序再处理
            return true;
        }

        // 1.恢复可用余额
        petsDetailService.refund(freeze.getPetId(), freeze.getPetCount());
        // 2.将冻结金额清零，状态改为CANCEL
        freeze.setPetCount(0);
        freeze.setState(PetFrezze.CANCEL);
        int count = petFrezzeMapper.updateById(freeze);
        return count == 1;
    }
}




