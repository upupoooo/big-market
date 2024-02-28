package org.upup.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.upup.types.common.Constants;

import java.util.Objects;

/**
 * @author upup
 * @description 策略实体
 * @date 2024/2/25 16:46
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyEntity {
    /**
     * 抽奖策略ID
     */
    private Long strategyId;
    /**
     * 抽奖策略描述
     */
    private String strategyDesc;
    /**
     * 抽奖规则模型
     */
    private String ruleModels;


    public String[] ruleModels() {
        if (StringUtils.isBlank(ruleModels)) return null;
        return this.ruleModels.split(Constants.SPLIT);
    }

    public String getRuleWeight() {
        String[] ruleModels = this.ruleModels();
        if (null == ruleModels) return null;
        for (String ruleModel : ruleModels) {
            if (Objects.equals("rule_weight", ruleModel)) return ruleModel;
        }
        return null;
    }
}
