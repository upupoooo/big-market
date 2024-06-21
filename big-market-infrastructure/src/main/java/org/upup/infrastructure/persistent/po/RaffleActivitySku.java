package org.upup.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author upup
 * @description 抽奖活动sku持久化对象
 * @date 2024/6/21 16:38
 */
@Data
public class RaffleActivitySku {

    /**
     * 商品sku
     */
    private Long sku;
    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 活动个人参与次数ID
     */
    private Long activityCountId;
    /**
     * 库存总量
     */
    private Integer stockCount;
    /**
     * 剩余库存
     */
    private Integer stockCountSurplus;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}









