package org.upup.domain.activity.service.rule;

import org.upup.domain.activity.model.entity.ActivityCountEntity;
import org.upup.domain.activity.model.entity.ActivityEntity;
import org.upup.domain.activity.model.entity.ActivitySkuEntity;

/**
 * @author upup
 * @description 下单规则过滤接口
 * @date 2024/6/24 16:12
 */
public interface IActionChain extends IActionChainArmory {

    boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);

}

