package org.upup.domain.strategy.service.rule.tree.factory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.upup.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import org.upup.domain.strategy.model.vo.RuleTreeVO;
import org.upup.domain.strategy.service.rule.tree.ILogicTreeNode;
import org.upup.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import org.upup.domain.strategy.service.rule.tree.factory.engine.impl.DecisionTreeEngine;

import java.util.Map;

/**
 * @author upup
 * @description 规则树工厂
 * @date 2024/2/29 18:28
 */
@Service
public class DefaultTreeFactory {
    private final Map<String, ILogicTreeNode> logicTreeNodeGroup;

    public DefaultTreeFactory(Map<String, ILogicTreeNode> logicTreeNodeGroup) {
        this.logicTreeNodeGroup = logicTreeNodeGroup;
    }

    public IDecisionTreeEngine openLogicTree(RuleTreeVO ruleTreeVO) {
        return new DecisionTreeEngine(logicTreeNodeGroup, ruleTreeVO);
    }


    /**
     * 决策树个动作实习
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TreeActionEntity {
        private RuleLogicCheckTypeVO ruleLogicCheckType;
        private StrategyAwardVO strategyAwardVO;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO{
        /** 抽奖奖品ID - 内部流转使用 */
        private Integer awardId;
        /** 抽奖奖品规则 */
        private String awardRuleValue;
    }
}
