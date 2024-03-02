package org.upup.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import org.upup.infrastructure.persistent.po.RuleTreeNode;

import java.util.List;

/**
 * @author upup
 * @description 规则树节点表DAO
 * @date 2024/3/2 22:49
 */
@Mapper
public interface IRuleTreeNodeDao {

    List<RuleTreeNode> queryRuleTreeNodeListByTreeId(String treeId);

}

