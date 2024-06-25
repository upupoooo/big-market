package org.upup.domain.activity.service.armory;

import java.util.Date;

/**
 * @author upup
 * @description 活动调度【扣减库存】
 * @date 2024/6/25 17:58
 */
public interface IActivityDispatch {
    /**
     * 根据策略ID和奖品ID，扣减奖品缓存库存
     * @param sku 互动sku
     * @param endDateTime 活动结束时间，根据活动结束时间设置加锁的key过期时间为结束时间
     * @return 扣减结果
     */
    boolean subtractionActivitySkuStock(Long sku, Date endDateTime);
}
