package com.laofeizhu.label.handler;

import com.laofeizhu.label.service.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author laofeizhu
 * @Date 2021/1/29
 */
@Component
@Slf4j
public class ListenerLabelDrlHandler {

    @Autowired
    @Qualifier("drlRecommendServiceImpl")
    public RecommendService recommendService;

    @PostConstruct
    public void init() {
        new Thread(()->{
            for(;;) {
                try {
                    recommendService.refresh();
                    Thread.sleep(2000L);
                } catch (Exception e) {
                    log.error("recommendService refresh error", e);
                }
            }
        }).start();
    }
}
