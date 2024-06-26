package org.upup.domain.strategy.service.raffle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.upup.domain.strategy.model.entity.StrategyAwardEntity;
import org.upup.domain.strategy.model.vo.RuleTreeVO;
import org.upup.domain.strategy.model.vo.StrategyAwardRuleModelVO;
import org.upup.domain.strategy.model.vo.StrategyAwardStockKeyVO;
import org.upup.domain.strategy.repository.IStrategyRepository;
import org.upup.domain.strategy.service.AbstractRaffleStrategy;
import org.upup.domain.strategy.service.IRaffleAward;
import org.upup.domain.strategy.service.IRaffleStock;
import org.upup.domain.strategy.service.armory.IStrategyDispatch;
import org.upup.domain.strategy.service.rule.chain.ILogicChain;
import org.upup.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import org.upup.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import org.upup.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;

import java.util.List;

/**
 * @author upup
 * @description 默认的抽奖策略实现
 * @date 2024/2/26 23:14
 */
@Slf4j
@Service
public class DefaultRaffleStrategy extends AbstractRaffleStrategy implements IRaffleStock, IRaffleAward {

    public DefaultRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch, DefaultChainFactory defaultChainFactory, DefaultTreeFactory defaultTreeFactory) {
        super(repository, strategyDispatch, defaultChainFactory, defaultTreeFactory);
    }

    @Override
    public DefaultChainFactory.StrategyAwardVO raffleLogicChain(String userId, Long strategyId) {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(strategyId);
        return logicChain.logic(userId, strategyId);
    }

    @Override
    public DefaultTreeFactory.StrategyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId) {
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModelVO(strategyId, awardId);
        if (null == strategyAwardRuleModelVO) {
            return DefaultTreeFactory.StrategyAwardVO.builder()
                    .awardId(awardId)
                    .build();
        }
        RuleTreeVO ruleTreeVO = repository.queryRuleTreeVOByTreeId(strategyAwardRuleModelVO.getRuleModels());
        if (null == ruleTreeVO) {
            throw new RuntimeException("存在抽奖策略配置的规则模型 Key，未在库表 rule_tree、rule_tree_node、rule_tree_line 配置对应的规则树信息 " + strategyAwardRuleModelVO.getRuleModels());
        }
        IDecisionTreeEngine treeEngine = defaultTreeFactory.openLogicTree(ruleTreeVO);
        return treeEngine.process(userId, strategyId, awardId);
    }

    @Override
    public StrategyAwardStockKeyVO takeQueueValue() throws InterruptedException {
        return repository.takeQueueValue();
    }

    @Override
    public void reduceStrategyAwardStock(Long strategyId, Integer awardId) {
        repository.reduceStrategyAwardStock(strategyId, awardId);
    }

    @Override
    public List<StrategyAwardEntity> queryRaffleStrategyAwardList(Long strategyId) {
        return repository.queryStrategyAwardList(strategyId);
    }

    @Override
    public void updateStrategyAwardStock(StrategyAwardStockKeyVO strategyAwardStockKeyVO) {
        repository.updateStrategyAwardStock(strategyAwardStockKeyVO);
    }
}
