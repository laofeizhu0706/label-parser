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
        KieContainer kieContainer = RuleLoader.getKieSessionByPath(path);
        if (Objects.isNull(kieContainer)) {
            throw new RuntimeException("kieContainer is null");
        }
        return kieContainer.getKieBase().newKieSession();
    }

    /**
     * 获取KieSession
     *
     * @param version 版本
     * @return KieSession
     */
    public static KieSession getKieSessionByVersion(String version) {
        KieContainer kieContainer = RuleLoader.getKieSessionByVersion(version);
        if (Objects.isNull(kieContainer)) {
            throw new RuntimeException("get kie session error");
        }
        return kieContainer.getKieBase().newKieSession();
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

    /**
     * 加载到版本号的缓存中
     * @param version 版本
     * @param content 内容
     */
    public static void loadInVersion(String version ,String content) {
        RuleLoader.reloadByVersion(version, content);
    }

    /**
     * 通过路径加载
     * @param path 路径
     */
    public static void loadByPath(String path) {
        RuleLoader.reload(path);
    }

    public static String getSessionContentByVersion(String version) {
        return RuleLoader.getContentByVersion(version);
    }

    public static String getSessionContentByPath(String path) {
        return RuleLoader.getContentByPath(path);
    }
}
