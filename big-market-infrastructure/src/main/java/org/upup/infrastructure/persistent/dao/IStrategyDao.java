package org.upup.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import org.upup.infrastructure.persistent.po.Strategy;

import java.util.List;

/**
 * @author upup
 * @description 抽奖策略 DAO
 * @date 2024/2/24 01:31
 */
@Mapper
public interface IStrategyDao {
    List<Strategy> queryStrategyList();

    Strategy queryStrategyByStrategyId(Long strategyId);
}
