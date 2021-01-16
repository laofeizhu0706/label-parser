package com.laofeizhu.config;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.Objects;

/**
 * @Classname KieSessionHelper
 * @Description TODO
 * @Date 2021/1/16 15:31
 * @Created by laofeizhu
 */
public class KieSessionHelper {

    /**
     * 获取KieSession
     *
     * @param path 路径
     * @return KieSession
     */
    public static KieSession getKieSessionByPath(String path) {
        return RuleLoader.getKieSessionByPath(path).getKieBase().newKieSession();
    }

    /**
     * 获取KieSession
     * @param path 路径
     * @param isReload 是否重新强制加载
     * @return
     */
    public static KieSession getKieSessionByPath(String path, boolean isReload) {
        KieContainer kieContainer = RuleLoader.getKieSessionByPath(path, isReload);
        if (Objects.isNull(kieContainer)) {
            throw new RuntimeException("kieContainer is null");
        }
        return kieContainer.getKieBase().newKieSession();
    }
}
