package org.upup.domain.strategy.service.rule.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.upup.domain.strategy.model.entity.RuleActionEntity;
import org.upup.domain.strategy.model.entity.RuleMatterEntity;
import org.upup.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import org.upup.domain.strategy.repository.IStrategyRepository;
import org.upup.domain.strategy.service.annotation.LogicStrategy;
import org.upup.domain.strategy.service.rule.ILogicFilter;
import org.upup.domain.strategy.service.rule.factory.DefaultLogicFactory;
import org.upup.types.common.Constants;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author upup
 * @description 【抽奖前规则】黑名单用户过滤规则
 * @date 2024/2/26 15:06
 */
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_BLACKLIST)
public class RuleBackListLogicFilter implements ILogicFilter<RuleActionEntity.RaffleBeforeEntity> {

    @Resource
    private IStrategyRepository repository;

    @Override
    public RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> filter(RuleMatterEntity ruleMatterEntity) {
        log.info("规则过滤-黑名单 userId:{} strategyId:{} ruleModel:{}", ruleMatterEntity.getUserId(), ruleMatterEntity.getStrategyId(), ruleMatterEntity.getRuleModel());
        String userId = ruleMatterEntity.getUserId();
        String ruleValue = repository.queryStrategyRuleValue(ruleMatterEntity.getStrategyId(), ruleMatterEntity.getAwardId(), ruleMatterEntity.getRuleModel());
        if (ruleValue.isEmpty()) return null;
        String[] splitRuleValue = ruleValue.split(Constants.COLON);

        Integer awardId = Integer.parseInt(splitRuleValue[0]);
        List<String> userBlackIds = Arrays.asList(splitRuleValue[1].split(Constants.SPLIT));
        if (userBlackIds.contains(userId)) {
            return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .ruleModel(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode())
                    .data(RuleActionEntity.RaffleBeforeEntity.builder()
                            .strategyId(ruleMatterEntity.getStrategyId())
                            .awardId(awardId)
                            .build())
                    .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                    .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                    .build();
        }
        return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                .build();
    }
}
