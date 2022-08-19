package com.example.zhxy.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zhxy.mapper.StudentMapper;
import com.example.zhxy.pojo.LoginForm;
import com.example.zhxy.pojo.Student;
import com.example.zhxy.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper,Student> implements StudentService {

    public Student login(LoginForm loginForm) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getName,loginForm.getUsername());
        queryWrapper.eq(Student::getPassword,loginForm.getPassword());
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Student getStudentById(Long userId) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getId,userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Student> getStudentByOpr(Page<Student> page, Student student) {
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(student.getName())){
            queryWrapper.like(Student::getName,student.getName());
        }
        if (StringUtils.hasText(student.getClazzName())){
            queryWrapper.like(Student::getClazzName,student.getClazzName());
        }

        return baseMapper.selectPage(page,queryWrapper);
    }
}
