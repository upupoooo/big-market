package org.upup.domain.strategy.service.rule.chain.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.upup.domain.strategy.repository.IStrategyRepository;
import org.upup.domain.strategy.service.rule.chain.AbstractLogicChain;
import org.upup.types.common.Constants;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author upup
 * @description 白名单责任链
 * @date 2024/2/28 16:13
 */
@Slf4j
@Component("rule_whitelist")
public class WhiteListLogicChain extends AbstractLogicChain {

    @Resource
    IStrategyRepository repository;

    @Override
    protected String ruleModel() {
        return "rule_whitelist";
    }

    @Override
    public Integer logic(String userId, Long strategyId) {
        log.info("抽奖责任链-白名单开始 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());

        // 查询规则值配置
        String ruleValue = repository.queryStrategyRuleValue(strategyId, ruleModel());
        String[] splitRuleValue = ruleValue.split(Constants.COLON);
        Integer awardId = Integer.parseInt(splitRuleValue[0]);

        // 白名单抽奖判断
        String[] userBlackIds = splitRuleValue[1].split(Constants.SPLIT);
        if (Arrays.asList(userBlackIds).contains(userId)) {
            log.info("抽奖责任链-白名单接管 userId: {} strategyId: {} ruleModel: {} awardId: {}", userId, strategyId, ruleModel(), awardId);
            return awardId;
        }

        // 过滤其他责任链
        log.info("抽奖责任链-白名单放行 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
        return next().logic(userId, strategyId);
    }
}
