package com.laofeizhu.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.runtime.KieContainer;

import java.io.InputStream;
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
    private static final Map<String, String> contentMap = new ConcurrentHashMap<>();


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
     * 通过路径获取KieContainer
     *
     * @param version 版本
     * @return KieContainer
     */
    public static KieContainer getKieSessionByVersion(String version) {
        return kieContainerMap.get(buildKcontainerName(getNameByVersion(version)));
    }

    /**
     * 通过路径获取Content
     *
     * @param version 版本
     * @return content
     */
    public static String getContentByVersion(String version) {
        return contentMap.get(buildKcontainerName(getNameByVersion(version)));
    }

    /**
     * 通过路径获取Content
     *
     * @param path 路径
     * @return content
     */
    public static String getContentByPath(String path) {
        return contentMap.get(buildKcontainerName(getNameByPath(path)));
    }

    /**
     * 加载给定路径给定的规则，对应一个kmodule
     *
     * @param path
     */
    public static void load(String path) {
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
    public static void reload(String path) {
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
        String content = null;
        try {
            content = FileUtil.readUtf8String(path);
        } catch (Exception e) {
            try (InputStream stream = RuleLoader.class.getClassLoader().getResourceAsStream(path)) {
                if (Objects.nonNull(stream)) {
                    content = IoUtil.readUtf8(stream);
                }
            } catch (Exception ex) {
                throw new RuntimeException("no such file");
            }
        }
        if (Objects.isNull(content) || "".equals(content.trim())) {
            throw new RuntimeException("no such file");
        }
        kieFileSystem.write(fullPath, content);
        kieFileSystem.writeKModuleXML(kieModuleModel.toXML());
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new IllegalStateException("rule error");
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        kieContainerMap.put(buildKcontainerName(name), kieContainer);
        contentMap.put(buildKcontainerName(name), content);
    }

    /**
     * 重新加载给定路径给定的规则，对应一个kmodule
     *
     * @param version
     * @param content
     */
    public static void reloadByVersion(String version, String content) {
        if (Objects.isNull(version) || "".equals(version.trim())) {
            return;
        }
        if (Objects.isNull(content) || "".equals(content.trim())) {
            return;
        }
        String name = getNameByVersion(version);
        KieServices kieServices = KieServices.get();
        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();
        KieBaseModel kieBaseModel = kieModuleModel.newKieBaseModel(buildKbaseName(name));
        kieBaseModel.setDefault(true);
        kieBaseModel.addPackage(MessageFormat.format("rules.scene_{0}", name));
        kieBaseModel.newKieSessionModel(buildKsessionName(name));
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        String fullPath = MessageFormat.format("src/main/resources/rules/scene_{0}/rule.drl", name);
        kieFileSystem.write(fullPath, content);
        kieFileSystem.writeKModuleXML(kieModuleModel.toXML());
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new IllegalStateException("rule error");
        }
        KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
        kieContainerMap.put(buildKcontainerName(name), kieContainer);
        contentMap.put(buildKcontainerName(name), content);
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
     * 获取名字通过路径
     *
     * @param version 版本
     * @return 名字
     */
    private static String getNameByVersion(String version) {
        if (Objects.isNull(version) || "".equals(version.trim())) {
            return NULL;
        }
        return version+"_version";
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
