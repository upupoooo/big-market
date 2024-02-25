package org.upup.domain.strategy.service.armory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.upup.domain.strategy.model.entity.StrategyAwardEntity;
import org.upup.domain.strategy.model.entity.StrategyEntity;
import org.upup.domain.strategy.model.entity.StrategyRuleEntity;
import org.upup.domain.strategy.repository.IStrategyRepository;
import org.upup.types.enums.ResponseCode;
import org.upup.types.exception.AppException;

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
public class StrategyArmoryDispatch implements IStrategyArmory, IStrategyDispatch {

    @Resource
    private IStrategyRepository strategyRepository;

    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        //1、查询策略配置
        List<StrategyAwardEntity> strategyAwardEntityList = strategyRepository.queryStrategyAwardList(strategyId);
        if (strategyAwardEntityList.isEmpty()) {
            log.info("empty strategyAwardEntityList ~~~");
            return false;
        }
        assembleLotteryStrategy(String.valueOf(strategyId), strategyAwardEntityList);

        //2、权重策略配置-适用于rule-weight权重规则配置
        StrategyEntity strategyEntity = strategyRepository.queryStrategyEntityByStrategyId(strategyId);
        if (null == strategyEntity) return true;
        String ruleWeight = strategyEntity.getRuleWeight();
        if (null == ruleWeight) return true;
        StrategyRuleEntity strategyRuleEntity = strategyRepository.queryStrategyRule(strategyId, ruleWeight);
        if (null == strategyRuleEntity) {
            throw new AppException(ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getCode(), ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getInfo());
        }
        Map<String, List<Integer>> ruleWeightValueMap = strategyRuleEntity.getRuleWeightValues();
        Set<String> keys = ruleWeightValueMap.keySet();
        for (String key : keys) {
            List<Integer> ruleWeightValues = ruleWeightValueMap.get(key);
            List<StrategyAwardEntity> strategyAwardEntitiesClone = new ArrayList<>(strategyAwardEntityList);
            strategyAwardEntitiesClone.removeIf(e -> !ruleWeightValues.contains(e.getAwardId()));
            assembleLotteryStrategy(String.valueOf(strategyId).concat("_").concat(key), strategyAwardEntitiesClone);
        }
        return true;
    }

    private void assembleLotteryStrategy(String key, List<StrategyAwardEntity> strategyAwardEntityList) {
        //1、获取最小概率值
        BigDecimal minAwardRate = strategyAwardEntityList.stream().map(StrategyAwardEntity::getAwardRate).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
        //2、获取概率值总和
        BigDecimal totalAwardRate = strategyAwardEntityList.stream().map(StrategyAwardEntity::getAwardRate).reduce(BigDecimal.ZERO, BigDecimal::add);
        //3、用 1 % 0.0001 获取概率范围，百分位、千分位、万分位
        BigDecimal rateRange = totalAwardRate.divide(minAwardRate, 0, RoundingMode.CEILING);
        //4、生成策略奖品概率查找表，【这里指需要在list集合中，存放上对应的奖品占位即可，占位越多等于概率越高】
        List<Integer> strategyAwardSearchRateTables = new ArrayList<>(rateRange.intValue());
        for (StrategyAwardEntity strategyAwardEntity : strategyAwardEntityList) {
            BigDecimal awardRate = strategyAwardEntity.getAwardRate();
            //计算出每个概率值需要存放查找表的数量，循环填充
            for (int i = 0; i < rateRange.multiply(awardRate).setScale(0, RoundingMode.CEILING).intValue(); i++) {
                strategyAwardSearchRateTables.add(strategyAwardEntity.getAwardId());
            }
        }
        //5、对存储的奖品进行乱序操作
        Collections.shuffle(strategyAwardSearchRateTables);
        //6、生成出对应的Map集合，key值就是后续的概率值，多个key值对应同一个奖品id
        Map<Integer, Integer> shuffleStrategyAwardSearchRateTableMap = new HashMap<>();
        for (int i = 0; i < strategyAwardSearchRateTables.size(); i++) {
            shuffleStrategyAwardSearchRateTableMap.put(i, strategyAwardSearchRateTables.get(i));
        }
        //7、放入redis
        strategyRepository.storeStrategyAwardSearchRateTableMap(key, strategyAwardSearchRateTables.size(), shuffleStrategyAwardSearchRateTableMap);
    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        int rateRange = strategyRepository.getRateRange(strategyId);
        return strategyRepository.getStrategyAwardAssemble(String.valueOf(strategyId), new SecureRandom().nextInt(rateRange));
    }

    @Override
    public Integer getRandomAwardId(Long strategyId, String ruleWeightValue) {
        String key = String.valueOf(strategyId).concat("_").concat(ruleWeightValue);
        int rateRange = strategyRepository.getRateRange(key);
        return strategyRepository.getStrategyAwardAssemble(key, new SecureRandom().nextInt(rateRange));
    }
}
