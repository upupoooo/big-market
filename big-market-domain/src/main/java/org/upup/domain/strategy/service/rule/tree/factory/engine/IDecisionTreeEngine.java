package org.upup.domain.strategy.service.rule.tree.factory.engine;

/**
 * @author upup
 * @description 规则树组合接口
 * @date 2024/2/29 19:27
 */
public interface IDecisionTreeEngine {
    DefaultTreeFactory.StrategyAwardData process(String userId, Long strategyId, Integer awardId);
}
