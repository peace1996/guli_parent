package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.handler.PeaceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-12
 */
@Api(tags = "讲师接口")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin //解决跨域问题
public class EduTeacherController {
    @Autowired
    private EduTeacherService service;

    //查询所有信息
    @ApiOperation(value = "查询所有信息")
    @GetMapping("findAll")
    public R findAllTeacher(){
        List<EduTeacher> list = service.list(null);
        return R.ok().data("items",list);
    }
    //逻辑删除
    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("{id}") //id需要通过路径传递
    public R removeTeacher(@ApiParam(name = "id",value = "讲师ID",required = true) @PathVariable String id){ //取出路径中的id值
        boolean flag = service.removeById(id);
        return flag ? R.ok() : R.error();
    }
    //分页查询方法
    @ApiOperation(value = "分页查询方法")
    @PostMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //构建查询条件(多条件组合查询)
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        //判断条件是否为空，不为空就拼接
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            queryWrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create",end);
        }
        //排序
        queryWrapper.orderByDesc("gmt_create");
        //调用方法实现分页
        service.page(pageTeacher,queryWrapper);
        //总记录数
        long total = pageTeacher.getTotal();
        //数据集合
        List<EduTeacher> records = pageTeacher.getRecords();
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);
    }

    /**
     * 添加讲师数据接口
     * @param eduTeacher
     * @return
     */
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = service.save(eduTeacher);
        return save ? R.ok() : R.error();
    }

    //根据id查询讲师信息
    @GetMapping("getTeacherById/{id}")
    public R getTeacherById(@PathVariable String id){
        EduTeacher eduTeacher = service.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }
    //讲师修改
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = service.updateById(eduTeacher);
        return  flag ? R.ok() : R.error();
    }

}

