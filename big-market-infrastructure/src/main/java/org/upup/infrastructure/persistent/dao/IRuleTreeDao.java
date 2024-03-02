package org.upup.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import org.upup.infrastructure.persistent.po.RuleTree;

/**
 * @author upup
 * @description 规则树表DAO
 * @date 2024/3/2 22:48
 */
@Mapper
public interface IRuleTreeDao {

    RuleTree queryRuleTreeByTreeId(String treeId);

}

