package org.upup.domain.strategy.service.rule.tree.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.upup.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import org.upup.domain.strategy.service.rule.tree.ILogicTreeNode;
import org.upup.domain.strategy.service.rule.tree.factory.engine.DefaultTreeFactory;

/**
 * @author upup
 * @description 次数锁节点
 * @date 2024/2/29 18:06
 */
@Slf4j
@Component("rule_lock")
public class RuleLockLogicTreeNode implements ILogicTreeNode {
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                .build();
    }
}
