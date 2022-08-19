package com.example.zhxy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zhxy.pojo.Teacher;
import com.example.zhxy.service.TeacherService;
import com.example.zhxy.util.MD5;
import com.example.zhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(@PathVariable("pageNo") Integer pageNo,
                              @PathVariable("pageSize") Integer pageSize,
                              Teacher teacher){
        Page<Teacher> page = new Page<>(pageNo,pageSize);
        return Result.ok(teacherService.getTeacherByOpr(page,teacher));
    }

    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@RequestBody Teacher teacher){
        teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        return Result.ok(teacherService.saveOrUpdate(teacher));
    }

    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@RequestBody List<Integer> id){
        return Result.ok(teacherService.removeByIds(id));
    }
}
