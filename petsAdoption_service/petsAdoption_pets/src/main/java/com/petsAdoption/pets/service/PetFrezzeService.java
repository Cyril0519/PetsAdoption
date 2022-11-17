package com.petsAdoption.pets.service;

import com.petsAdoption.pets.pojo.PetFrezze;
import com.baomidou.mybatisplus.extension.service.IService;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
* @author wuxingyu
* @description 针对表【t_pet_frezze】的数据库操作Service
* @createDate 2022-11-12 15:31:26
*/
@LocalTCC
public interface PetFrezzeService extends IService<PetFrezze> {
    @TwoPhaseBusinessAction(name = "deduct", commitMethod = "confirm", rollbackMethod = "cancel")
    public void deduct(@BusinessActionContextParameter(paramName = "petId") String petId,
                        @BusinessActionContextParameter(paramName = "count") int count);

    boolean confirm(BusinessActionContext ctx);

    boolean cancel(BusinessActionContext ctx);
}
