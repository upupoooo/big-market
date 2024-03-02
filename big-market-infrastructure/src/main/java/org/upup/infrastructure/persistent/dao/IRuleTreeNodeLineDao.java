package org.upup.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import org.upup.infrastructure.persistent.po.RuleTreeNodeLine;

import java.util.List;

/**
 * @author upup
 * @description 规则树节点连线表DAO
 * @date 2024/3/2 22:50
 */
@Mapper
public interface IRuleTreeNodeLineDao {

    List<RuleTreeNodeLine> queryRuleTreeNodeLineListByTreeId(String treeId);

}

