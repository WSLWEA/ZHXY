package com.example.zhxy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zhxy.pojo.Admin;
import com.example.zhxy.pojo.Student;
import com.example.zhxy.service.AdminService;
import com.example.zhxy.util.MD5;
import com.example.zhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/adminController")
public class AdminController {
    @Autowired
    private AdminService adminService;
    //请求 URL: http://localhost:8080/sms/adminController/getAllAdmin/1/3
    //请求方法: GET
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(@PathVariable("pageNo") Integer pageNo,
                              @PathVariable("pageSize") Integer pageSize,
                              String adminName){
        Page<Admin> page = new Page<Admin>(pageNo,pageSize);
        return Result.ok(adminService.getAdminByOpr(page,adminName));
    }

    //请求 URL: http://localhost:8080/sms/adminController/saveOrUpdateAdmin
    //请求方法: POST

    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@RequestBody Admin admin){
        admin.setPassword(MD5.encrypt(admin.getPassword()));
        return Result.ok(adminService.saveOrUpdate(admin));
    }

    //请求 URL: http://localhost:8080/sms/adminController/deleteAdmin
    //请求方法: DELETE
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@RequestBody List<Integer> id){
        return Result.ok(adminService.removeByIds(id));
    }
}
