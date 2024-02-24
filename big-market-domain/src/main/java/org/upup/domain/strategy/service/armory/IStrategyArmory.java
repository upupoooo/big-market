package org.upup.domain.strategy.service.armory;

/**
 * @author upup
 * @description 策略装配库（兵工厂），负责初始化策略计算
 * @date 2024/2/24 17:55
 */
public interface IStrategyArmory {
    void assembleLotteryStrategy(Long strategyId);

    Integer getRandomAwardId(Long strategyId);
}
