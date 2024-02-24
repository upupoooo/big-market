package org.upup.domain.strategy.service.armory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.upup.domain.strategy.model.entity.StrategyAwardEntity;
import org.upup.domain.strategy.repository.IStrategyRepository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

/**
 * @author upup
 * @description 策略装配库（兵工厂），负责初始化策略计算
 * @date 2024/2/24 17:57
 */
@Slf4j
@Service
public class StrategyArmory implements IStrategyArmory {

    @Resource
    private IStrategyRepository strategyRepository;

    @Override
    public void assembleLotteryStrategy(Long strategyId) {
        //1、查询策略配置
        List<StrategyAwardEntity> strategyAwardEntityList = strategyRepository.queryStrategyAwardList(strategyId);
        if (strategyAwardEntityList.isEmpty()) {
            log.info("empty strategyAwardEntityList ~~~");
            return;
        }
        //2、获取最小概率值
        BigDecimal minAwardRate = strategyAwardEntityList.stream().map(StrategyAwardEntity::getAwardRate).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
        //3、获取概率值总和
        BigDecimal totalAwardRate = strategyAwardEntityList.stream().map(StrategyAwardEntity::getAwardRate).reduce(BigDecimal.ZERO, BigDecimal::add);
        //4、用 1 % 0.0001 获取概率范围，百分位、千分位、万分位
        BigDecimal rateRange = totalAwardRate.divide(minAwardRate, 0, RoundingMode.CEILING);
        //5、
        List<Integer> strategyAwardSearchRateTables = new ArrayList<>(rateRange.intValue());
        for (StrategyAwardEntity strategyAwardEntity : strategyAwardEntityList) {
            Integer awardId = strategyAwardEntity.getAwardId();
            BigDecimal awardRate = strategyAwardEntity.getAwardRate();
            //计算出每个概率值需要存放查找表的数量，循环填充
            for (int i = 0; i < rateRange.multiply(awardRate).setScale(0, RoundingMode.CEILING).intValue(); i++) {
                strategyAwardSearchRateTables.add(strategyAwardEntity.getAwardId());
            }
        }
        //6、乱序
        Collections.shuffle(strategyAwardSearchRateTables);
        //7、
        Map<Integer, Integer> shuffleStrategyAwardSearchRateTableMap = new HashMap<>();
        for (int i = 0; i < strategyAwardSearchRateTables.size(); i++) {
            shuffleStrategyAwardSearchRateTableMap.put(i, strategyAwardSearchRateTables.get(i));
        }
        //8、放入redis
        strategyRepository.storeStrategyAwardSearchRateTableMap(strategyId, strategyAwardSearchRateTables.size(), shuffleStrategyAwardSearchRateTableMap);
    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        int rateRange = strategyRepository.getRateRange(strategyId);
        return strategyRepository.getStrategyAwardAssemble(strategyId, new SecureRandom().nextInt(rateRange));
    }
}
