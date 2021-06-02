package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.ExcelSubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author peace
 * @since 2021-05-06
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
//        EasyExcel.read(file, ExcelSubjectData.class, new ExcelListener()).sheet().doRead();
        try{
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream,ExcelSubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllSubject() {
        //查询出所有的一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
        //查询所有的二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);
        //创建list集合，放最终返回数据
        List<OneSubject> finalSubjectList = new ArrayList<>();
        //封装一级分类
        //查询出来的一级分类遍历放入finallist
        for (EduSubject oneEdusubject:oneSubjectList) {
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(subject.getId());
//            oneSubject.setTitle(subject.getTitle());
            //简便写法,复制edusubject到onesubject
            BeanUtils.copyProperties(oneEdusubject,oneSubject);
            //把封装号的一级分类放入最终集合
            finalSubjectList.add(oneSubject);
            //封装二级分类
            //在一级分类遍历中查询所有的二级分类
            List<TwoSubject> twoSubjects = new ArrayList<>();
            for (EduSubject twoEduSubject : twoSubjectList) {
                //判断二级分类是否属于当前一级分类
                if (twoEduSubject.getParentId().equals(oneEdusubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(twoEduSubject,twoSubject);
                    twoSubjects.add(twoSubject);
                }
            }
            //把二级分类放入一级分类中
            oneSubject.setChildren(twoSubjects);
        }
        return finalSubjectList;
    }
}
