package org.upup.domain.strategy.service.rule.filter;

import org.upup.domain.strategy.model.entity.RuleActionEntity;
import org.upup.domain.strategy.model.entity.RuleMatterEntity;

/**
 * @author upup
 * @description 抽奖规则过滤接口
 * @date 2024/2/26 14:37
 */
public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity> {
    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);
}
