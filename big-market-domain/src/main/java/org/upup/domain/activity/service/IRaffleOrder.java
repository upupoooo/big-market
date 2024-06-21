package org.upup.domain.activity.service;

import org.upup.domain.activity.model.entity.ActivityOrderEntity;
import org.upup.domain.activity.model.entity.ActivityShopCartEntity;

/**
 * @author upup
 * @description 下单活动接口
 * @date 2024/6/21 18:33
 */
public interface IRaffleOrder {
    /**
     * 以sku创建抽奖活动订单，获得参与抽奖资格（可消耗的次数）
     *
     * @param activityShopCartEntity 活动sku实体，通过sku领取活动。
     * @return 活动参与记录实体
     */
    ActivityOrderEntity createRaffleActivityOrder(ActivityShopCartEntity activityShopCartEntity);
}
