package org.upup.domain.activity.repository;

import org.upup.domain.activity.model.aggregate.CreateOrderAggregate;
import org.upup.domain.activity.model.entity.ActivityCountEntity;
import org.upup.domain.activity.model.entity.ActivityEntity;
import org.upup.domain.activity.model.entity.ActivitySkuEntity;

/**
 * @author upup
 * @description 活动仓储接口
 * @date 2024/6/21 18:41
 */
public interface IActivityRepository {
    ActivitySkuEntity queryActivitySku(Long sku);

    ActivityEntity queryRaffleActivityByActivityId(Long activityId);

    ActivityCountEntity queryRaffleActivityCountByActivityCountId(Long activityCountId);

    void doSaveOrder(CreateOrderAggregate createOrderAggregate);

}
