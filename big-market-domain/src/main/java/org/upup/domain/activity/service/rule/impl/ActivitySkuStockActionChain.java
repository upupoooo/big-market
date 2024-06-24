package org.upup.domain.activity.service.rule.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.upup.domain.activity.model.entity.ActivityCountEntity;
import org.upup.domain.activity.model.entity.ActivityEntity;
import org.upup.domain.activity.model.entity.ActivitySkuEntity;
import org.upup.domain.activity.service.rule.AbstractActionChain;

/**
 * @author upup
 * @description 商品库存规则节点
 * @date 2024/6/24 16:20
 */
@Slf4j
@Component("activity_sku_stock_action")
public class ActivitySkuStockActionChain extends AbstractActionChain {

    @Override
    public boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity) {
        log.info("活动责任链-商品库存处理【校验&扣减】开始。");

        return true;
    }

}

