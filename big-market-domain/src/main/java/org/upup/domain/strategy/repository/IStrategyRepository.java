package org.upup.domain.strategy.repository;

import org.upup.domain.strategy.model.entity.StrategyAwardEntity;
import org.upup.domain.strategy.model.entity.StrategyEntity;
import org.upup.domain.strategy.model.entity.StrategyRuleEntity;
import org.upup.domain.strategy.model.vo.RuleTreeVO;
import org.upup.domain.strategy.model.vo.StrategyAwardRuleModelVO;
import org.upup.domain.strategy.model.vo.StrategyAwardStockKeyVO;

import java.util.List;
import java.util.Map;

/**
 * @author upup
 * @description 策略仓储接口
 * @date 2024/2/24 18:02
 */
public interface IStrategyRepository {
    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    void storeStrategyAwardSearchRateTableMap(String key, int rateRange, Map<Integer, Integer> shuffleStrategyAwardSearchRateTableMap);

    int getRateRange(Long strategyId);

    int getRateRange(String key);

    Integer getStrategyAwardAssemble(String key, int rateKey);

    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);

    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel);

    String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel);

    String queryStrategyRuleValue(Long strategyId, String ruleModel);

    StrategyAwardRuleModelVO queryStrategyAwardRuleModelVO(Long strategyId, Integer awardId);

    RuleTreeVO queryRuleTreeVOByTreeId(String treeId);

    void cacheStrategyAwardCount(String cacheKey, Integer awardCount);

    StrategyAwardStockKeyVO takeQueueValue();

    void reduceStrategyAwardStock(Long strategyId, Integer awardId);

    Boolean subtractionAwardStock(String cacheKey);

    void awardStockConsumeSendQueue(StrategyAwardStockKeyVO strategyAwardStockKeyVO);
}
