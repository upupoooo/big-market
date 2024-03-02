package org.upup.domain.strategy.service.rule.tree.factory.engine;

import org.upup.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author upup
 * @description 规则树组合接口
 * @date 2024/2/29 19:27
 */
public interface IDecisionTreeEngine {
    DefaultTreeFactory.StrategyAwardVO process(String userId, Long strategyId, Integer awardId);
}
