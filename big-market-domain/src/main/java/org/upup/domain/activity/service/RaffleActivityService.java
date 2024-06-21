package org.upup.domain.activity.service;

import org.springframework.stereotype.Service;
import org.upup.domain.activity.repository.IActivityRepository;

/**
 * @author upup
 * @description 抽奖活动服务
 * @date 2024/6/21 19:01
 */
@Service
public class RaffleActivityService extends AbstractRaffleActivity {

    public RaffleActivityService(IActivityRepository activityRepository) {
        super(activityRepository);
    }

}

