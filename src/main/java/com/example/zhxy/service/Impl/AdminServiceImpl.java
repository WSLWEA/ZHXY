package com.example.zhxy.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zhxy.mapper.AdminMapper;
import com.example.zhxy.pojo.Admin;
import com.example.zhxy.pojo.LoginForm;
import com.example.zhxy.pojo.Student;
import com.example.zhxy.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Override
    public Admin login(LoginForm loginForm) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getName,loginForm.getUsername());
        queryWrapper.eq(Admin::getPassword,loginForm.getPassword());
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Admin getAdminById(Long userId) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getId,userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Admin> getAdminByOpr(Page<Admin> page, String adminName) {
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(adminName)){
            queryWrapper.like(Admin::getName,adminName);
        }
        return baseMapper.selectPage(page,queryWrapper);
    }
}
