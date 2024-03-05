package org.upup.domain.strategy.service.rule.tree;

import org.upup.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author upup
 * @description 规则树接口
 * @date 2024/2/29 17:56
 */
public interface ILogicTreeNode {
    DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue);

}
