package org.upup.domain.activity.service;

import org.upup.domain.activity.model.entity.ActivityCountEntity;
import org.upup.domain.activity.model.entity.ActivityEntity;
import org.upup.domain.activity.model.entity.ActivitySkuEntity;
import org.upup.domain.activity.repository.IActivityRepository;
import org.upup.domain.activity.service.rule.factory.DefaultActivityChainFactory;

/**
 * @author upup
 * @description 抽奖活动支撑类
 * @date 2024/6/24 16:06
 */
public class RaffleActivitySupport {
    protected IActivityRepository activityRepository;

    protected DefaultActivityChainFactory defaultActivityChainFactory;

    public RaffleActivitySupport(IActivityRepository activityRepository, DefaultActivityChainFactory defaultActivityChainFactory) {
        this.activityRepository = activityRepository;
        this.defaultActivityChainFactory = defaultActivityChainFactory;
    }

    public ActivitySkuEntity queryActivitySku(Long sku) {
        return activityRepository.queryActivitySku(sku);
    }

    public ActivityEntity queryRaffleActivityByActivityId(Long activityId) {
        return activityRepository.queryRaffleActivityByActivityId(activityId);
    }

    public ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId) {
        return activityRepository.queryRaffleActivityCountByActivityCountId(activityCountId);
    }


}
