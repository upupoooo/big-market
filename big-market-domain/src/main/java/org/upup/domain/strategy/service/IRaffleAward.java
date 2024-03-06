package org.upup.domain.strategy.service;

import org.upup.domain.strategy.model.entity.StrategyAwardEntity;

import java.util.List;

/**
 * @author upup
 * @description 策略奖品接口
 * @date 2024/3/6 10:52
 */
public interface IRaffleAward {
    /**
     * 根据策略ID查询抽奖奖品列表配置
     *
     * @param strategyId 策略ID
     * @return 奖品列表
     */
    List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId);

}
