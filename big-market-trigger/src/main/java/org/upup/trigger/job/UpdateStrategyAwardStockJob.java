package org.upup.trigger.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.upup.domain.strategy.model.vo.StrategyAwardStockKeyVO;
import org.upup.domain.strategy.service.IRaffleStock;

import javax.annotation.Resource;

/**
 * @author upup
 * @description 更新策略奖品库存任务
 * @date 2024/6/25 20:09
 */
@Slf4j
@Component()
public class UpdateStrategyAwardStockJob {
    @Resource
    private IRaffleStock raffleStock;

    @Scheduled(cron = "0/5 * * * * ?")
    public void exec() {
        try {
            log.info("定时任务，更新策略奖品库存【延迟队列获取，降低对数据库的更新频次，不要产生竞争】");
            StrategyAwardStockKeyVO strategyAwardStockKeyVO = raffleStock.takeQueueValue();
            if (null == strategyAwardStockKeyVO) return;
            log.info("定时任务，更新策略奖品库存 strategyId:{} awardId:{}", strategyAwardStockKeyVO.getStrategyId(), strategyAwardStockKeyVO.getAwardId());
            raffleStock.updateStrategyAwardStock(strategyAwardStockKeyVO);
        } catch (Exception e) {
            log.error("定时任务，更新奖品库存失败", e);
        }
    }
}
