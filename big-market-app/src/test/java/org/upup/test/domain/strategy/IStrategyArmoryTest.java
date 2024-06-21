package org.upup.test.domain.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.upup.domain.strategy.service.armory.IStrategyArmory;
import org.upup.domain.strategy.service.armory.IStrategyDispatch;

import javax.annotation.Resource;

/**
 * @author upup
 * @description 策略领域测试
 * @date 2024/2/24 22:35
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class IStrategyArmoryTest {

    @Resource
    private IStrategyArmory strategyArmory;
    @Resource
    private IStrategyDispatch strategyDispatch;

    @Before
    public void test_assembleLotteryStrategy(){
        boolean result = strategyArmory.assembleLotteryStrategy(100001L);
        log.info("测试结果:{}",result);
    }

    @Test
    public void text_getRandomAwardId(){
        log.info("测试结果:{} 奖品ID值",strategyDispatch.getRandomAwardId(100001L));
        log.info("测试结果:{} 奖品ID值",strategyDispatch.getRandomAwardId(100001L));
        log.info("测试结果:{} 奖品ID值",strategyDispatch.getRandomAwardId(100001L));
    }


    @Test
    public void text_getRandomAwardId_ruleWeightValue(){
        log.info("测试结果:{} 4000 策略配置",strategyDispatch.getRandomAwardId(100001L,"4000:102,103,104,105"));
        log.info("测试结果:{} 5000 策略配置",strategyDispatch.getRandomAwardId(100001L,"5000:102,103,104,105,106,107"));
        log.info("测试结果:{} 6000 策略配置",strategyDispatch.getRandomAwardId(100001L,"6000:102,103,104,105,106,107,108,109"));
    }
}
