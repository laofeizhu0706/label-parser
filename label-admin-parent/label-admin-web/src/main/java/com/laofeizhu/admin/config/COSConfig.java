package com.laofeizhu.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author
 * @create 2019-07-04 15:46
 **/
@ConfigurationProperties(prefix = "cos")
@Component
@Data
public class COSConfig {
    String secretId;
    String secretKey;
    String regionName;
    String bucketName;
}
