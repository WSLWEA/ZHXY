package com.example.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zhxy.mapper.AdminMapper;
import com.example.zhxy.pojo.Admin;
import com.example.zhxy.pojo.LoginForm;
import com.example.zhxy.pojo.Student;
import org.springframework.stereotype.Service;


public interface AdminService extends IService<Admin> {
    Admin login(LoginForm loginForm);

    Admin getAdminById(Long userId);

    IPage<Admin> getAdminByOpr(Page<Admin> page, String adminName);
}
