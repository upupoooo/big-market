package org.upup.test.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.upup.domain.strategy.service.armory.IStrategyArmory;
import org.upup.domain.strategy.service.rule.chain.ILogicChain;
import org.upup.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import org.upup.domain.strategy.service.rule.chain.impl.RuleWeightLogicChain;
import org.upup.domain.strategy.service.rule.filter.impl.RuleLockLogicFilter;

import javax.annotation.Resource;

/**
 * @author upup
 * @description 抽奖责任链测试，验证不同的规则走不同的责任链
 * @date 2024/2/28 17:01
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class LogicChainTest {
    @Resource
    private RuleWeightLogicChain ruleWeightLogicChain;
    @Resource
    private RuleLockLogicFilter ruleLockLogicFilter;
    @Resource
    private IStrategyArmory strategyArmory;
    @Resource
    private DefaultChainFactory defaultChainFactory;

    @Before
    public void setUp() {
        log.info("装配结果:{}",strategyArmory.assembleLotteryStrategy(100001L));
        log.info("装配结果:{}",strategyArmory.assembleLotteryStrategy(100002L));
        log.info("装配结果:{}",strategyArmory.assembleLotteryStrategy(100003L));

        ReflectionTestUtils.setField(ruleWeightLogicChain, "userScore", 40500L);
        ReflectionTestUtils.setField(ruleLockLogicFilter, "userRaffleCount", 10L);
    }

    @Test
    public void test_LogicChain_rule_blacklist() {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        Integer awardId = logicChain.logic("user001", 100001L);
        log.info("测试抽奖结果：{}", awardId);
    }

    @Test
    public void test_LogicChain_rule_whitelist() {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        Integer awardId = logicChain.logic("upup", 100001L);
        log.info("测试抽奖结果：{}", awardId);
    }

    @Test
    public void test_LogicChain_rule_weight() {
        // 通过反射 mock 规则中的值
        ReflectionTestUtils.setField(ruleWeightLogicChain, "userScore", 4900L);

        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        Integer awardId = logicChain.logic("upup", 100001L);
        log.info("测试抽奖结果：{}", awardId);
    }

    @Test
    public void test_LogicChain_rule_default() {
        // 通过反射 mock 规则中的值
        ReflectionTestUtils.setField(ruleWeightLogicChain, "userScore", 0L);

        ILogicChain logicChain = defaultChainFactory.openLogicChain(100001L);
        Integer awardId = logicChain.logic("upup1", 100001L);
        log.info("测试结果：{}", awardId);
    }



}
