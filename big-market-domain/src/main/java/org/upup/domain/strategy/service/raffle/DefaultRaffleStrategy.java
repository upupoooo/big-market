package org.upup.domain.strategy.service.raffle;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.upup.domain.strategy.model.entity.RaffleFactorEntity;
import org.upup.domain.strategy.model.entity.RuleActionEntity;
import org.upup.domain.strategy.model.entity.RuleMatterEntity;
import org.upup.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import org.upup.domain.strategy.repository.IStrategyRepository;
import org.upup.domain.strategy.service.AbstractRaffleStrategy;
import org.upup.domain.strategy.service.armory.IStrategyDispatch;
import org.upup.domain.strategy.service.rule.filter.ILogicFilter;
import org.upup.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author upup
 * @description 默认的抽奖策略实现
 * @date 2024/2/26 23:14
 */
@Slf4j
@Service
public class DefaultRaffleStrategy extends AbstractRaffleStrategy {

    @Resource
    private DefaultLogicFactory logicFactory;

    public DefaultRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch) {
        super(repository, strategyDispatch);
    }

    @Override
    protected RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity raffleFactorEntity, String... logics) {
        if (null == logics || 0 == logics.length)
            return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();
        //key-value:rule_weight-RuleWeightLogicFilter    利用工厂设计模式，获取加了@LogicStrategy自定义注解的bean，并进行组装返回
        Map<String, ILogicFilter<RuleActionEntity.RaffleBeforeEntity>> logicFilterGroupMap = logicFactory.openLogicFilter();

        // 黑名单规则优先过滤-判断是否存在rule_blacklist
        String ruleBackList = Arrays.stream(logics).filter(str -> str.contains(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode()))
                .findFirst()
                .orElse(null);

        if (StringUtils.isNotBlank(ruleBackList)) {
            //获取加了@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_BLACKLIST) 的bean
            ILogicFilter<RuleActionEntity.RaffleBeforeEntity> logicFilter = logicFilterGroupMap.get(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode());
            RuleMatterEntity ruleMatterEntity = new RuleMatterEntity();
            ruleMatterEntity.setUserId(raffleFactorEntity.getUserId());
            ruleMatterEntity.setAwardId(ruleMatterEntity.getAwardId());
            ruleMatterEntity.setStrategyId(raffleFactorEntity.getStrategyId());
            ruleMatterEntity.setRuleModel(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode());

            RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> ruleActionEntity = logicFilter.filter(ruleMatterEntity);
            if (!RuleLogicCheckTypeVO.ALLOW.getCode().equals(ruleActionEntity.getCode())) {
                return ruleActionEntity;
            }
        }

        // 白名单规则优先优先级同黑名单一致-判断是否存在rule_whitelist
        String ruleWhiteList = Arrays.stream(logics).filter(str -> str.contains(DefaultLogicFactory.LogicModel.RULE_WHITELIST.getCode()))
                .findFirst()
                .orElse(null);

        if (StringUtils.isNotBlank(ruleWhiteList)) {
            //获取加了@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_WHITELIST) 的bean
            ILogicFilter<RuleActionEntity.RaffleBeforeEntity> logicFilter = logicFilterGroupMap.get(DefaultLogicFactory.LogicModel.RULE_WHITELIST.getCode());
            RuleMatterEntity ruleMatterEntity = new RuleMatterEntity();
            ruleMatterEntity.setUserId(raffleFactorEntity.getUserId());
            ruleMatterEntity.setAwardId(ruleMatterEntity.getAwardId());
            ruleMatterEntity.setStrategyId(raffleFactorEntity.getStrategyId());
            ruleMatterEntity.setRuleModel(DefaultLogicFactory.LogicModel.RULE_WHITELIST.getCode());

            RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> ruleActionEntity = logicFilter.filter(ruleMatterEntity);
            if (!RuleLogicCheckTypeVO.ALLOW.getCode().equals(ruleActionEntity.getCode())) {
                return ruleActionEntity;
            }
        }

        // 顺序过滤剩余规则
        List<String> ruleList = Arrays.stream(logics)
                .filter(s -> !s.equals(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode()))
                .filter(s -> !s.equals(DefaultLogicFactory.LogicModel.RULE_WHITELIST.getCode()))
                .collect(Collectors.toList());
        if (ruleList.isEmpty()) return null;
        RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> ruleActionEntity = null;
        for (String ruleModel : ruleList) {
            ILogicFilter<RuleActionEntity.RaffleBeforeEntity> logicFilter = logicFilterGroupMap.get(ruleModel);
            RuleMatterEntity ruleMatterEntity = new RuleMatterEntity();
            ruleMatterEntity.setUserId(raffleFactorEntity.getUserId());
            ruleMatterEntity.setAwardId(ruleMatterEntity.getAwardId());
            ruleMatterEntity.setStrategyId(raffleFactorEntity.getStrategyId());
            ruleMatterEntity.setRuleModel(ruleModel);
            ruleActionEntity = logicFilter.filter(ruleMatterEntity);
            // 非放行结果则顺序过滤
            log.info("抽奖前规则过滤 userId: {} ruleModel: {} code: {} info: {}", raffleFactorEntity.getUserId(), ruleModel, ruleActionEntity.getCode(), ruleActionEntity.getInfo());
            if (!RuleLogicCheckTypeVO.ALLOW.getCode().equals(ruleActionEntity.getCode())) return ruleActionEntity;
        }
        return ruleActionEntity;
    }

    @Override
    protected RuleActionEntity<RuleActionEntity.RaffleCenterEntity> doCheckRaffleCenterLogic(RaffleFactorEntity raffleFactorEntity, String... logics) {
        if (null == logics || 0 == logics.length)
            return RuleActionEntity.<RuleActionEntity.RaffleCenterEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();

        //key-value:rule_lock-RuleLockLogicFilter    利用工厂设计模式，获取加了@LogicStrategy自定义注解的bean，并进行组装返回
        Map<String, ILogicFilter<RuleActionEntity.RaffleCenterEntity>> logicFilterGroupMap = logicFactory.openLogicFilter();

        RuleActionEntity<RuleActionEntity.RaffleCenterEntity> ruleActionEntity = null;
        for (String ruleModel : logics) {
            ILogicFilter<RuleActionEntity.RaffleCenterEntity> logicFilter = logicFilterGroupMap.get(ruleModel);
            if (null == logicFilter) continue;
            RuleMatterEntity ruleMatterEntity = new RuleMatterEntity();
            ruleMatterEntity.setUserId(raffleFactorEntity.getUserId());
            ruleMatterEntity.setAwardId(raffleFactorEntity.getAwardId());
            ruleMatterEntity.setStrategyId(raffleFactorEntity.getStrategyId());
            ruleMatterEntity.setRuleModel(ruleModel);
            ruleActionEntity = logicFilter.filter(ruleMatterEntity);
            // 非放行结果则顺序过滤
            log.info("抽奖中规则过滤 userId: {} ruleModel: {} code: {} info: {}", raffleFactorEntity.getUserId(), ruleModel, ruleActionEntity.getCode(), ruleActionEntity.getInfo());
            if (!RuleLogicCheckTypeVO.ALLOW.getCode().equals(ruleActionEntity.getCode())) return ruleActionEntity;
        }
        return ruleActionEntity;
    }

    @Override
    protected RuleActionEntity<RuleActionEntity.RaffleAfterEntity> doCheckRaffleAfterLogic(RaffleFactorEntity raffleFactorEntity, String... logics) {
        if (null == logics || 0 == logics.length)
            return RuleActionEntity.<RuleActionEntity.RaffleAfterEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();
        return null;
    }
}
