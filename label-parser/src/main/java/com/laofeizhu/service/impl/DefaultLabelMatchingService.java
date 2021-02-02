package com.laofeizhu.service.impl;

import com.laofeizhu.config.KieSessionHelper;
import com.laofeizhu.enums.ReadFromType;
import com.laofeizhu.model.Result;
import com.laofeizhu.model.UserLabelVo;
import com.laofeizhu.service.ILabelMatchingService;
import org.kie.api.runtime.KieSession;

import java.util.List;
import java.util.Objects;

/**
 * @Author: laofeizhu (云辰)
 * @Description:
 * @Date: Created in 5:18 下午 2021/2/2
 * @Modified By:
 */
public class DefaultLabelMatchingService implements ILabelMatchingService {

    private String version;

    private String content;

    private static final String DEFAULT_DRL_PATH = "rules/user/label.drl";

    private ReadFromType fromType;

    private Boolean isReload;

    private String filePath;

    private DefaultLabelMatchingService(String filePath, Boolean isReload) {
        this.filePath = filePath;
        this.isReload = Objects.isNull(isReload) ? false : isReload;
        this.fromType = ReadFromType.PATH;
    }

    private DefaultLabelMatchingService(String version, String drlContent) {
        this.version = version;
        this.fromType = ReadFromType.CONTENT;
        this.content = drlContent;
        if (drlContent != null && drlContent.trim().length() > 0) {
            KieSessionHelper.loadInVersion(version, drlContent);
        }
    }

    public static ILabelMatchingService build(String filePath) {
        return new DefaultLabelMatchingService(filePath, false);
    }

    public static ILabelMatchingService build() {
        return new DefaultLabelMatchingService(DEFAULT_DRL_PATH, false);
    }

    public static ILabelMatchingService build(String filePath, Boolean isReload) {
        return new DefaultLabelMatchingService(filePath, isReload);
    }

    public static ILabelMatchingService build(String version, String content) {
        DefaultLabelMatchingService service = new DefaultLabelMatchingService(version, content);
        if (content != null && content.trim().length() > 0) {
            KieSessionHelper.loadInVersion(version, content);
        }
        ;
        return service;
    }

    @Override
    public String getMatchingLabel(List<UserLabelVo> userLabels) {
        if (Objects.nonNull(userLabels) && userLabels.size() > 0) {
            KieSession kieSession = null;
            switch (this.fromType) {
                case PATH:
                    kieSession = KieSessionHelper.getKieSessionByPath(filePath, isReload);
                    break;
                case CONTENT:
                    kieSession = KieSessionHelper.getKieSessionByVersion(version);
                    break;
                default:
                    kieSession = KieSessionHelper.getKieSessionByVersion(version);
                    break;
            }
            for (UserLabelVo userLabel : userLabels) {
                kieSession.insert(userLabel);
            }
            Result result = new Result();
            kieSession.insert(result);
            kieSession.fireAllRules();
            kieSession.dispose();
            if (result.getResults().size() > 0) {
                return result.getResults().get(0);
            }
        }
        return null;
    }
}
