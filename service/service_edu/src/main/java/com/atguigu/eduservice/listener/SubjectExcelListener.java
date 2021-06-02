package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.ExcelSubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.handler.PeaceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    public EduSubjectService subjectService;

    public SubjectExcelListener() {}

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }
    //读取excel内容，一行一行
    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        if (excelSubjectData == null){
            throw new PeaceException(20001,"文件数据为空");
        }
        //每次读取有两个值，一级分类，二级分类
        //先判断一级分类是否重复
        EduSubject oneSubject = existSubject(subjectService, excelSubjectData.getOneSubjectName(), "0");
        //不存在就添加
        if (oneSubject == null){
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(excelSubjectData.getOneSubjectName());
            subjectService.save(oneSubject);
        }
        //判断二级分类是否重复
        String pid = oneSubject.getId();
        EduSubject twoSubject = existSubject(subjectService, excelSubjectData.getTwoSubjectName(), pid);
        if (twoSubject == null){
            twoSubject = new EduSubject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(excelSubjectData.getTwoSubjectName());
            subjectService.save(twoSubject);
        }

    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
    //判断分类不能重复添加
    public EduSubject existSubject(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject subject = subjectService.getOne(wrapper);
        return subject;
    }
}
