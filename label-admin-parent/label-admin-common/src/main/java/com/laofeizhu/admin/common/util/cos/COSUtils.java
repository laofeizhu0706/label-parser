package com.laofeizhu.admin.common.util.cos;

import cn.hutool.core.lang.UUID;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author 老肥猪
 * @since 2020-01-10
 */
@Slf4j
public class COSUtils {


    public static COSClient createCOSClient(String secretId, String secretKey, String regionName) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(regionName);
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }

    public static String upload(COSClient client, String bucketName, String filePath, File localFile) {
        try {
            return upload(client, bucketName, new FileInputStream(localFile), filePath, localFile.getName());
        } catch (FileNotFoundException e) {
            log.info("上传文件发生异常:" + e.getMessage(), e);
        }
        return null;
    }

    public static String upload(COSClient client, String bucketName, String filePath, MultipartFile file) {
        try {
            return upload(client, bucketName, file.getInputStream(), filePath, file.getOriginalFilename());
        } catch (IOException e) {
            log.info("上传文件发生异常:" + e.getMessage(), e);
        }
        return null;
    }

    public static String upload(COSClient client, String bucketName, InputStream is, String filePath, String fileName) {
        try {
            // 指定要上传的文件
            // 指定要上传到 COS 上对象键
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(is.available());
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("utf-8");
            String resName = filePath + "/" + UUID.fastUUID().toString() + "." + StringUtils.substringAfterLast(fileName, ".");
            metadata.setContentType(getContentType(fileName));
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, resName, is, metadata);
            PutObjectResult putObjectResult = client.putObject(putObjectRequest);
            return COSImageUtils.getDefaultUrl(resName);
        } catch (IOException | CosClientException ex) {
            log.info("上传文件发生异常:" + ex.getMessage(), ex);
        }
        return null;
    }


    private static String getContentType(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if ("bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if ("gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if ("jpeg".equalsIgnoreCase(fileExtension) || "jpg".equalsIgnoreCase(fileExtension)
                || "png".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if ("html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if ("txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if ("vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if ("ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if ("doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if ("xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        if ("pdf".equalsIgnoreCase(fileExtension)) {
            return "application/pdf";
        }
        if ("xls".equalsIgnoreCase(fileExtension) || "xlsx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-excel";
        }
        return "text/html";
    }

}
