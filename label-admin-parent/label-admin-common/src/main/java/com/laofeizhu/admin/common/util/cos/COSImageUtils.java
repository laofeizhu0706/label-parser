package com.laofeizhu.admin.common.util.cos;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author 老肥猪
 * @since 2019/9/18
 */
public class COSImageUtils {

    public static final String DEFAULT_URL = "https://common-cos-1259627079.picsh.myqcloud.com/";

    /**
     * 获取默认的完整地址
     *
     * @param imageUrl
     * @return
     */
    public static String getDefaultUrl(String imageUrl) {
        return getFullUrl(imageUrl);
    }

    /**
     * 获取默认的完整地址集合
     *
     * @param imageUrl
     * @return
     */
    public static List<String> getDefaultUrlList(String imageUrl) {
        String fullUrl = getFullUrl(imageUrl);
        if (StringUtils.isNoneBlank(fullUrl)) {
            return new ArrayList<>(Arrays.asList(fullUrl.split("(,|(\\|))")));
        }
        return Collections.emptyList();
    }

    /**
     * 获取完整地址
     *
     * @param imageUrl
     * @return
     */
    private static String getFullUrl(String imageUrl) {
        if (StringUtils.isNoneEmpty(imageUrl)) {
            imageUrl = imageUrl.trim();
            String[] urls = imageUrl.split("(,|(\\|))");
            if (urls.length > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < urls.length; i++) {
                    if (urls[i].startsWith("https://") || urls[i].startsWith("http://")) {
                        sb.append(urls[i]);
                        if (i != urls.length - 1) {
                            sb.append("|");
                        }
                    } else {
                        sb.append(COSImageUtils.DEFAULT_URL).append(urls[i]);
                        if (i != urls.length - 1) {
                            sb.append("|");
                        }
                    }
                }
                return sb.toString();
            } else {
                return imageUrl;
            }
        } else {
            return imageUrl;
        }
    }
}
