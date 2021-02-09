package com.laofeizhu.admin.modules.label.service.impl;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import com.laofeizhu.admin.modules.label.entity.LabelProductSubTag;
import com.laofeizhu.admin.modules.label.handler.LabelHandlerContext;
import com.laofeizhu.admin.modules.label.handler.LabelHandlerTypeEnum;
import com.laofeizhu.admin.modules.label.mapper.LabelProductSubTagMapper;
import com.laofeizhu.admin.modules.label.service.ILabelProductSubTagService;
import com.laofeizhu.config.RuleLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Objects;

/**
 * @Description: 商品子标签
 * @Author: laofeizhu
 * @Date: 2021-02-02
 * @Version: V1.0
 */
@Service
public class LabelProductSubTagServiceImpl extends ServiceImpl<LabelProductSubTagMapper, LabelProductSubTag> implements ILabelProductSubTagService {

    private static final String PROUDCT_TAG_LABEL_DRL_PATH = "drl/ProductTagLabel.drl";

    private static final String MODEL = "{0}:UserLabelVo({1})\n";
    private static final String LABEL_MODEL = " label==\"{0}\" ";



    @Autowired
    private LabelHandlerContext context;

    @Override
    public void addTag(LabelProductSubTag labelProductSubTag) {
        String baseModel = null;
        try (InputStream stream = LabelProductSubTagServiceImpl.class.getClassLoader().getResourceAsStream(PROUDCT_TAG_LABEL_DRL_PATH)) {
            if (Objects.nonNull(stream)) {
                baseModel = IoUtil.readUtf8(stream);
            }
        } catch (Exception ex) {
            throw new RuntimeException("no such file");
        }
        if (Objects.nonNull(baseModel)) {
            StringBuilder models = new StringBuilder();
            for (String label : Sets.newHashSet(labelProductSubTag.getSubTag().split(","))) {
                models.append(MessageFormat.format(LABEL_MODEL, label)).append("||");
            }
            String modelString = null;
            if (models.lastIndexOf("||")!=0) {
                modelString = models.substring(0, models.lastIndexOf("||"));
            } else {
                modelString = models.toString();
            }
            modelString = MessageFormat.format(MODEL, "tag", modelString);
            labelProductSubTag.setContent(MessageFormat.format(baseModel, modelString, labelProductSubTag.getName()));
            save(labelProductSubTag);
        }
    }

    @Override
    public void updateTag(LabelProductSubTag labelProductSubTag) {
        String baseModel = null;
        try (InputStream stream = RuleLoader.class.getClassLoader().getResourceAsStream(PROUDCT_TAG_LABEL_DRL_PATH)) {
            if (Objects.nonNull(stream)) {
                baseModel = IoUtil.readUtf8(stream);
            }
        } catch (Exception ex) {
            throw new RuntimeException("no such file");
        }
        if (Objects.nonNull(baseModel)) {
            StringBuilder models = new StringBuilder();
            for (String label : Sets.newHashSet(labelProductSubTag.getSubTag().split(","))) {
                models.append(MessageFormat.format(LABEL_MODEL, label)).append("||");
            }
            String modelString = null;
            if (models.lastIndexOf("||")!=0) {
                modelString = models.substring(0, models.lastIndexOf("||"));
            } else {
                modelString = models.toString();
            }
            modelString = MessageFormat.format(MODEL, "tag", modelString);
            labelProductSubTag.setContent(MessageFormat.format(baseModel, modelString, labelProductSubTag.getName()));
            updateById(labelProductSubTag);
        }
    }
}
