package com.petsAdoption.user.service;

import com.petsAdoption.user.pojo.MoneyFreeze;
import com.baomidou.mybatisplus.extension.service.IService;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

import java.math.BigDecimal;

/**
 * @author wuxingyu
 * @description 针对表【t_money_freeze】的数据库操作Service
 * @createDate 2022-11-12 17:13:33
 */
@LocalTCC
public interface MoneyFreezeService extends IService<MoneyFreeze> {
    @TwoPhaseBusinessAction(name = "decreaseMoney", commitMethod = "confirm", rollbackMethod = "cancel")
    void decreaseMoney(
            @BusinessActionContextParameter(paramName = "uid") String uid,
            @BusinessActionContextParameter(paramName = "money") BigDecimal money);

    boolean confirm(BusinessActionContext ctx);

    boolean cancel(BusinessActionContext ctx);
}
