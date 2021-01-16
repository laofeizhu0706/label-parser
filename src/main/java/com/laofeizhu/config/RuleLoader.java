package com.laofeizhu.config;

import cn.hutool.core.io.FileUtil;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.runtime.KieContainer;

import java.io.File;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname RuleLoader
 * @Description TODO
 * @Date 2021/1/16 15:30
 * @Created by laofeizhu
 */
public class RuleLoader {

    private static final String NULL = "null";

    private static final Map<String, KieContainer> kieContainerMap = new ConcurrentHashMap<>();


    /**
     * 通过路径获取KieContainer
     *
     * @param path 路径
     * @return KieContainer
     */
    public static KieContainer getKieSessionByPath(String path) {
        return getKieSessionByPath(path, false);
    }

    /**
     * 通过路径获取KieContainer
     *
     * @param path 路径
     * @return KieContainer
     */
    public static KieContainer getKieSessionByPath(String path, boolean isReload) {
        if (Objects.isNull(path) || "".equals(path.trim())) {
            return null;
        }
        if (isReload) {
            reload(path);
        } else {
            load(path);
        }
        return kieContainerMap.get(buildKcontainerName(getNameByPath(path)));
    }

    /**
     * 获取名字通过路径
     *
     * @param path 路径
     * @return 名字
     */
    private static String getNameByPath(String path) {
        if (Objects.isNull(path) || "".equals(path.trim())) {
            return NULL;
        }
        String name = path.replaceAll("/", "_");
        name = name.replaceAll("\\\\", "_");
        name = name.replaceAll(":", "_");
        name = name.replaceAll("\\.", "_");
        return name;
    }

    /**
     * 加载给定路径给定的规则，对应一个kmodule
     *
     * @param path
     */
    private static void load(String path) {
        if (Objects.isNull(path) || "".equals(path.trim())) {
            return;
        }
        String name = getNameByPath(path);
        if (kieContainerMap.containsKey(buildKbaseName(name))) {
            return;
        }
        reload(path);
    }

    /**
     * 重新加载给定路径给定的规则，对应一个kmodule
     *
     * @param path
     */
    private static void reload(String path) {
        if (Objects.isNull(path) || "".equals(path.trim())) {
            return;
        }
        String name = getNameByPath(path);
        KieServices kieServices = KieServices.get();
        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();
        KieBaseModel kieBaseModel = kieModuleModel.newKieBaseModel(buildKbaseName(name));
        kieBaseModel.setDefault(true);
        kieBaseModel.addPackage(MessageFormat.format("rules.scene_{0}", name));
        kieBaseModel.newKieSessionModel(buildKsessionName(name));
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        String fullPath = MessageFormat.format("src/main/resources/rules/scene_{0}/rule.drl", name);
        String content = FileUtil.readUtf8String(path);
        kieFileSystem.write(fullPath, content);
        kieFileSystem.writeKModuleXML(kieModuleModel.toXML());
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new IllegalStateException("rule error");
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        kieContainerMap.put(buildKcontainerName(name), kieContainer);
    }

    /**
     * 构造kcontainerName
     *
     * @param name name
     * @return kcontainerName
     */
    private static String buildKcontainerName(String name) {
        return "kcontainer_" + name;
    }

    /**
     * 构造kbaseName
     *
     * @param name name
     * @return kbaseName
     */
    private static String buildKbaseName(String name) {
        return "kbase_" + name;
    }

    /**
     * 构造ksessionName
     *
     * @param name name
     * @return ksessionName
     */
    private static String buildKsessionName(String name) {
        return "ksession_" + name;
    }
}
