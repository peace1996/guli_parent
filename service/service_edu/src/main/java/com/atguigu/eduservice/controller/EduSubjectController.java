package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author peace
 * @since 2021-05-06
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    /**
     * 添加课程分类
     * @param file  获取上传过来的文件，读取文件内容
     * @return
     */
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    /**
     * 查询所有课程内容信息（树形结构）
     * @return
     */
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> subjects =  subjectService.getAllSubject();
        return R.ok().data("list",subjects);
    }

}

