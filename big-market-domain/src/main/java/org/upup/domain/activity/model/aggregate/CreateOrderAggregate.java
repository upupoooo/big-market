package org.upup.domain.activity.model.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.upup.domain.activity.model.entity.ActivityAccountEntity;
import org.upup.domain.activity.model.entity.ActivityOrderEntity;

/**
 * @author upup
 * @description 下单聚合对象
 * @date 2024/6/21 18:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderAggregate {

    /**
     * 活动账户实体
     */
    private ActivityAccountEntity activityAccountEntity;
    /**
     * 活动订单实体
     */
    private ActivityOrderEntity activityOrderEntity;

}

