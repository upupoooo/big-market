package org.upup.domain.activity.service.rule.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.upup.domain.activity.model.entity.ActivityCountEntity;
import org.upup.domain.activity.model.entity.ActivityEntity;
import org.upup.domain.activity.model.entity.ActivitySkuEntity;
import org.upup.domain.activity.model.valobj.ActivityStateVO;
import org.upup.domain.activity.service.rule.AbstractActionChain;
import org.upup.types.enums.ResponseCode;
import org.upup.types.exception.AppException;

import java.util.Date;

/**
 * @author upup
 * @description 活动规则过滤【日期、状态】
 * @date 2024/6/24 16:19
 */
@Slf4j
@Component("activity_base_action")
public class ActivityBaseActionChain extends AbstractActionChain {
    @Override
    public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {
        log.info("活动责任链-基础信息【有效期、状态、库存(sku)】校验开始。sku:{} activityId:{}", activitySkuEntity.getSku(), activityEntity.getActivityId());
        // 校验；活动状态
        if(!ActivityStateVO.open.equals(activityEntity.getState())){
            throw new AppException(ResponseCode.ACTIVITY_STATE_ERROR.getCode(), ResponseCode.ACTIVITY_STATE_ERROR.getInfo());
        }
        // 校验；活动日期「开始时间 <- 当前时间 -> 结束时间」
        Date currentDate = new Date();
        if(currentDate.before(activityEntity.getBeginDateTime()) || currentDate.after(activityEntity.getEndDateTime())){
            throw new AppException(ResponseCode.ACTIVITY_DATE_ERROR.getCode(), ResponseCode.ACTIVITY_DATE_ERROR.getInfo());
        }
        // 校验；活动sku库存 「剩余库存从缓存获取的」
        if (activitySkuEntity.getStockCountSurplus() <= 0) {
            throw new AppException(ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getCode(), ResponseCode.ACTIVITY_SKU_STOCK_ERROR.getInfo());
        }
        return next().action(activitySkuEntity, activityEntity, activityCountEntity);
    }


}
