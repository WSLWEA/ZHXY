package com.example.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zhxy.pojo.Clazz;
import com.example.zhxy.pojo.Student;
import com.example.zhxy.service.StudentService;
import com.example.zhxy.util.MD5;
import com.example.zhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
    @Autowired
    private StudentService studentService;
    //请求 URL: http://localhost:8080/sms/studentController/getStudentByOpr/1/3
    //请求方法: GET
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(@PathVariable("pageNo") Integer pageNo,
                                  @PathVariable("pageSize") Integer pageSize,
                                  Student student){
       Page<Student> page = new Page<>(pageNo,pageSize);
       IPage<Student> iPage = studentService.getStudentByOpr(page,student);
       return Result.ok(iPage);
    }

    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@RequestBody Student student){
        student.setPassword(MD5.encrypt(student.getPassword()));
        return Result.ok(studentService.saveOrUpdate(student));
    }

    //请求 URL: http://localhost:8080/sms/studentController/delStudentById
    //请求方法: DELETE

    @DeleteMapping("/delStudentById")
    public Result delStudentById(@RequestBody List<Integer> id){
        return Result.ok(studentService.removeByIds(id));
    }
}
