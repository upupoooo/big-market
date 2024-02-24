package org.upup.infrastructure.persistent.repository;

import org.springframework.stereotype.Repository;
import org.upup.domain.strategy.model.entity.StrategyAwardEntity;
import org.upup.domain.strategy.repository.IStrategyRepository;
import org.upup.infrastructure.persistent.dao.IStrategyAwardDao;
import org.upup.infrastructure.persistent.dao.IStrategyDao;
import org.upup.infrastructure.persistent.po.StrategyAward;
import org.upup.infrastructure.persistent.redis.IRedisService;
import org.upup.types.common.Constants;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author upup
 * @description 策略仓储实现
 * @date 2024/2/24 18:03
 */
@Repository
public class StrategyRepository implements IStrategyRepository {

    @Resource
    private IStrategyAwardDao strategyAwardDao;
    @Resource
    private IRedisService redisService;

    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId;
        List<StrategyAwardEntity> strategyAwardEntities = redisService.getValue(cacheKey);
        if (strategyAwardEntities != null && !strategyAwardEntities.isEmpty()) return strategyAwardEntities;
        //从库中查询
        List<StrategyAward> strategyAwardList = strategyAwardDao.queryStrategyAwardListByStrategyId(strategyId);
        if (strategyAwardList.isEmpty()) return new ArrayList<>();

        List<StrategyAwardEntity> strategyAwardEntityList = new ArrayList<>();
        for (StrategyAward strategyAward : strategyAwardList) {
            StrategyAwardEntity strategyAwardEntity = StrategyAwardEntity.builder()
                    .strategyId(strategyAward.getStrategyId())
                    .awardId(strategyAward.getAwardId())
                    .awardCount(strategyAward.getAwardCount())
                    .awardCountSurplus(strategyAward.getAwardCountSurplus())
                    .awardRate(strategyAward.getAwardRate())
                    .build();

            strategyAwardEntityList.add(strategyAwardEntity);
        }
        redisService.setValue(cacheKey, strategyAwardEntityList);
        return strategyAwardEntityList;
    }

    @Override
    public void storeStrategyAwardSearchRateTableMap(Long strategyId, int rateRange, Map<Integer, Integer> shuffleStrategyAwardSearchRateTableMap) {
        //1、存储抽奖策略范围值，如10000，用于生成10000以内的随机数
        redisService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + strategyId, rateRange);
        //2、存储概率查找表
        Map<Integer, Integer> cacheRateTable = redisService.getMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + strategyId);
        cacheRateTable.putAll(shuffleStrategyAwardSearchRateTableMap);
    }

    @Override
    public int getRateRange(Long strategyId) {
        return redisService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + strategyId);
    }

    @Override
    public Integer getStrategyAwardAssemble(Long strategyId, int rateKey) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + strategyId, rateKey);
    }
}
