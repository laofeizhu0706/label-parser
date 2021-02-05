package com.laofeizhu.admin.modules.label.handler;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: laofeizhu (云辰)
 * @Description:
 * @Date: Created in 4:31 下午 2021/2/5
 * @Modified By:
 */
@Component
public class LabelHandlerContext implements ApplicationContextAware {

    private static final String LABEL_PRE = "LABEL_";

    private static final Map<String,ILabelModelHandler> HANDLER_MAP = new ConcurrentHashMap<>();

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        Map<String, ILabelModelHandler> handlerMap
                = applicationContext.getBeansOfType(ILabelModelHandler.class);
        if (MapUtils.isNotEmpty(handlerMap)) {
            for (Map.Entry<String, ILabelModelHandler> entry : handlerMap.entrySet()) {
                if (HANDLER_MAP.containsKey(LABEL_PRE+entry.getValue().scene())) {
                    throw new IllegalArgumentException("Duplicate benefit handler found.");
                }
                HANDLER_MAP.put(LABEL_PRE+entry.getValue().scene(), entry.getValue());
            }
        }
    }


    public ILabelModelHandler getHandler(String scene) {
        if (HANDLER_MAP.containsKey(LABEL_PRE+ scene)) {
            return HANDLER_MAP.get(LABEL_PRE+ scene);
        }
        return HANDLER_MAP.get(LABEL_PRE+LabelHandlerTypeEnum.VALUE.name());
    }
}
