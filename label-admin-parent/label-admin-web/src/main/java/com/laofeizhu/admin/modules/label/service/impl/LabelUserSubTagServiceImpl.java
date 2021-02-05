package com.laofeizhu.admin.modules.label.service.impl;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import com.laofeizhu.admin.modules.label.entity.LabelUserSubTag;
import com.laofeizhu.admin.modules.label.handler.LabelHandlerContext;
import com.laofeizhu.admin.modules.label.handler.LabelHandlerTypeEnum;
import com.laofeizhu.admin.modules.label.mapper.LabelUserSubTagMapper;
import com.laofeizhu.admin.modules.label.service.ILabelUserSubTagService;
import com.laofeizhu.config.RuleLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Objects;

/**
 * @Description: 用户子标签
 * @Author: laofeizhu
 * @Date: 2021-02-02
 * @Version: V1.0
 */
@Service
public class LabelUserSubTagServiceImpl extends ServiceImpl<LabelUserSubTagMapper, LabelUserSubTag> implements ILabelUserSubTagService {

    private static final String USER_TAG_LABEL_DRL_PATH = "drl/UserTagLabel.drl";

    @Autowired
    private LabelHandlerContext context;

    @Override
    public void addTag(LabelUserSubTag labelUserSubTag) {
        String baseModel = null;
        try (InputStream stream = LabelUserSubTagServiceImpl.class.getClassLoader().getResourceAsStream(USER_TAG_LABEL_DRL_PATH)) {
            if (Objects.nonNull(stream)) {
                baseModel = IoUtil.readUtf8(stream);
            }
        } catch (Exception ex) {
            throw new RuntimeException("no such file");
        }
        if (Objects.nonNull(baseModel)) {
            StringBuilder models = new StringBuilder();
            for (String label : Sets.newHashSet(labelUserSubTag.getSubTag().split(","))) {
                if (label.contains("~")) {
                    models.append(context.getHandler(LabelHandlerTypeEnum.RANGE.name()).get(label));
                } else {
                    models.append(context.getHandler(LabelHandlerTypeEnum.VALUE.name()).get(label));
                }
            }
            labelUserSubTag.setContent(MessageFormat.format(baseModel, models.toString(), labelUserSubTag.getName()));
            save(labelUserSubTag);
        }
    }

    @Override
    public void updateTag(LabelUserSubTag labelUserSubTag) {
        String baseModel = null;
        try (InputStream stream = RuleLoader.class.getClassLoader().getResourceAsStream(USER_TAG_LABEL_DRL_PATH)) {
            if (Objects.nonNull(stream)) {
                baseModel = IoUtil.readUtf8(stream);
            }
        } catch (Exception ex) {
            throw new RuntimeException("no such file");
        }
        if (Objects.nonNull(baseModel)) {
            StringBuilder models = new StringBuilder();
            for (String label : Sets.newHashSet(labelUserSubTag.getSubTag().split(","))) {
                if (label.contains("~")) {
                    models.append(context.getHandler(LabelHandlerTypeEnum.RANGE.name()).get(label));
                } else {
                    models.append(context.getHandler(LabelHandlerTypeEnum.VALUE.name()).get(label));
                }
            }
            labelUserSubTag.setContent(MessageFormat.format(baseModel, models.toString(), labelUserSubTag.getName()));
            updateById(labelUserSubTag);
        }
    }
}
