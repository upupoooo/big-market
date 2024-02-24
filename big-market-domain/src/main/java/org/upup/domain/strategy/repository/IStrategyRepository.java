package org.upup.domain.strategy.repository;

import org.upup.domain.strategy.model.entity.StrategyAwardEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author upup
 * @description 策略仓储接口
 * @date 2024/2/24 18:02
 */
public interface IStrategyRepository {
    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    void storeStrategyAwardSearchRateTableMap(Long strategyId, int rateRange, Map<Integer, Integer> shuffleStrategyAwardSearchRateTableMap);


    int getRateRange(Long strategyId);

    Integer getStrategyAwardAssemble(Long strategyId, int rateKey);
}
