package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;
    @PostMapping("upload")
    public R uploadOss(MultipartFile file){
        //先获取到上传文件 MultipartFile
        //上传成功得到url
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
}
