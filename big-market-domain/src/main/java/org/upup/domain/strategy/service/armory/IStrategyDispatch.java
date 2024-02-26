package org.upup.domain.strategy.service.armory;

/**
 * @author upup
 * @description 策略抽奖调度
 * @date 2024/2/24 23:23
 */
public interface IStrategyDispatch {
    /**
     * 获取抽奖策略装配的随机结果
     * @param strategyId 策略ID
     * @return 抽奖结果 奖品ID
     */
    Integer getRandomAwardId(Long strategyId);


    /**
     * 获取抽奖策略装配的随机结果 权重
     * @param strategyId 策略ID
     * @param ruleWeightValue 权重值
     * @return 抽奖结果 奖品ID
     */
    Integer getRandomAwardId(Long strategyId,String ruleWeightValue);

}
