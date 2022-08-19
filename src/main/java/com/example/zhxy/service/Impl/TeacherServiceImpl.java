package com.example.zhxy.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zhxy.mapper.TeacherMapper;
import com.example.zhxy.pojo.LoginForm;
import com.example.zhxy.pojo.Teacher;
import com.example.zhxy.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Override
    public Teacher login(LoginForm loginForm) {
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getName,loginForm.getUsername());
        queryWrapper.eq(Teacher::getPassword,loginForm.getPassword());
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Teacher getTeacherById(Long userId) {
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getId,userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Teacher> getTeacherByOpr(Page<Teacher> page, Teacher teacher) {
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(teacher.getName())){
            queryWrapper.like(Teacher::getName,teacher.getName());
        }
        if (StringUtils.hasText(teacher.getClazzName())){
            queryWrapper.like(Teacher::getClazzName,teacher.getClazzName());
        }
        return baseMapper.selectPage(page,queryWrapper);
    }
}
