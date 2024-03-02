package org.upup.domain.strategy.service.rule.tree.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.upup.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import org.upup.domain.strategy.service.rule.tree.ILogicTreeNode;
import org.upup.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author upup
 * @description 兜底奖励节点
 * @date 2024/2/29 18:06
 */
@Slf4j
@Component("rule_luck_award")
public class RuleLuckAwardLogicTreeNode implements ILogicTreeNode {

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .strategyAwardVO(DefaultTreeFactory.StrategyAwardVO.builder()
                        .awardId(awardId)
                        .awardRuleValue("1,100")
                        .build())
                .build();
    }
}
