package org.upup.domain.strategy.service.rule.chain.factory;

import lombok.*;
import org.springframework.stereotype.Service;
import org.upup.domain.strategy.model.entity.StrategyEntity;
import org.upup.domain.strategy.repository.IStrategyRepository;
import org.upup.domain.strategy.service.rule.chain.ILogicChain;

import java.util.Map;

/**
 * @author upup
 * @description 工厂
 * @date 2024/2/28 16:50
 */
@Service
public class DefaultChainFactory {
    private final Map<String, ILogicChain> logicChainGroup;
    protected IStrategyRepository repository;

    public DefaultChainFactory(Map<String, ILogicChain> logicChainGroup, IStrategyRepository repository) {
        this.logicChainGroup = logicChainGroup;
        this.repository = repository;
    }


    /**
     * 通过策略ID，构建责任链
     *
     * @param strategyId 策略ID
     * @return LogicChain
     */
    public ILogicChain openLogicChain(Long strategyId) {
        StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);
        String[] ruleModels = strategy.ruleModels();

        // 如果未配置策略规则，则只装填一个默认责任链
        if (null == ruleModels || 0 == ruleModels.length) return logicChainGroup.get(LogicModel.RULE_DEFAULT.getCode());

        // 按照配置顺序装填用户配置的责任链；rule_blacklist、rule_weight、rule_whitelist 「注意此数据从Redis缓存中获取，如果更新库表，记得在测试阶段手动处理缓存」
        ILogicChain iLogicChain = logicChainGroup.get(ruleModels[0]);
        ILogicChain current = iLogicChain;

        for (int i = 1; i < ruleModels.length; i++) {
            ILogicChain nextLoginChain = logicChainGroup.get(ruleModels[i]);
            current = current.appendNext(nextLoginChain);
        }

        // 责任链的最后装填默认责任链
        current.appendNext(logicChainGroup.get(LogicModel.RULE_DEFAULT.getCode()));

        return iLogicChain;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /** 抽奖奖品ID - 内部流转使用 */
        private Integer awardId;
        /**  */
        private String logicModel;
    }


    @Getter
    @AllArgsConstructor
    public enum LogicModel {

        RULE_DEFAULT("rule_default", "默认抽奖"),
        RULE_BLACKLIST("rule_blacklist", "黑名单抽奖"),
        RULE_WHITELIST("rule_whitelist", "白名单抽奖"),
        RULE_WEIGHT("rule_weight", "权重规则"),
        ;

        private final String code;
        private final String info;

    }



}
