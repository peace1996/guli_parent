package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流
            InputStream inputStream = file.getInputStream();
            // 获取文件名称
            String filename = file.getOriginalFilename();
            String objectName = "edu/";
            //1.在文件夹名称里添加随机唯一值,预防文件名称相同覆盖
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            filename = uuid + filename;
            //2.把文件按日期分类 2021-04-30/xxx.jpg
            //获取当前日期
            String datePath = new DateTime().toString("yyyy-MM-dd");
            filename = datePath + "/" +filename;
            // 调用oss上传方法
            ossClient.putObject(bucketName,objectName+filename,inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            //上传成功之后的路径返回
            String url = "https://"+bucketName+"."+endpoint+"/"+objectName+filename;
            return url;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
