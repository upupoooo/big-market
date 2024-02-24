package org.upup.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import org.upup.infrastructure.persistent.po.Award;

import java.util.List;

/**
 * @author upup
 * @description 奖品表DAO
 * @date 2024/2/24 01:30
 */
@Mapper
public interface IAwardDao {

   List<Award> queryAwardList();

}
