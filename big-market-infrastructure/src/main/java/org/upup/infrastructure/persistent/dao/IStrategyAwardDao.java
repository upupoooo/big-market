package org.upup.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import org.upup.domain.strategy.model.vo.StrategyAwardStockKeyVO;
import org.upup.infrastructure.persistent.po.StrategyAward;

import java.util.List;

/**
 * @author upup
 * @description 抽奖策略奖品明细配置 - 概率、规则 DAO
 * @date 2024/2/24 01:31
 */
@Mapper
public interface IStrategyAwardDao {

    List<StrategyAward> queryStrategyAwardList();

    List<StrategyAward> queryStrategyAwardListByStrategyId(Long strategyId);

    StrategyAward queryStrategyAward(Long strategyId, Integer awardId);

    void reduceStrategyAwardStock(StrategyAward strategyAward);

    void updateStrategyAwardStock(StrategyAwardStockKeyVO strategyAwardStockKeyVO);
}
