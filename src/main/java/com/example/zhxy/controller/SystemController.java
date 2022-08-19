package com.example.zhxy.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.zhxy.pojo.Admin;
import com.example.zhxy.pojo.LoginForm;
import com.example.zhxy.pojo.Student;
import com.example.zhxy.pojo.Teacher;
import com.example.zhxy.service.AdminService;
import com.example.zhxy.service.StudentService;
import com.example.zhxy.service.TeacherService;
import com.example.zhxy.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/sms/system")
public class SystemController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;


    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpSession session, HttpServletResponse response){
        //获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片上的验证码
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        //将验证码文本放入session，为下一次验证做准备
        session.setAttribute("verifiCode",verifiCode);
        //将验证码图片响应给浏览器
        try {
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request){
        HttpSession session = request.getSession();
        String verifiCode = (String) request.getSession().getAttribute("verifiCode");
        String loginVerifiCode = loginForm.getVerifiCode();
        if (StringUtils.isEmpty(verifiCode)){
            return Result.fail().message("验证码失效，请刷新后重试!");
        }
        if (!verifiCode.equalsIgnoreCase(loginVerifiCode)){
            return Result.fail().message("验证码有误，请小心输入后重试!");
        }
        //从session中移除现有验证码
        session.removeAttribute("verifiCode");
        //分用户类型进行校验
        Map<String,Object> map = new HashMap<>();
        switch (loginForm.getUserType()){
            case 1:
                try {
                    Admin admin = adminService.login(loginForm);
                    if (null!=admin) {
                        //用户类型和ID转换成一个密文，以token的形式向客户端反馈
                        map.put("token",JwtHelper.createToken(admin.getId().longValue(),loginForm.getUserType()));
                    }else {
                        throw new Exception("用户名或密码有误!");
                    }
                    return Result.ok(map);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 2:
                try {
                    Student student = studentService.login(loginForm);
                    if (null!=student) {
                        //用户类型和ID转换成一个密文，以token的形式向客户端反馈
                        map.put("token",JwtHelper.createToken(student.getId().longValue(),loginForm.getUserType()));
                    }else {
                        throw new Exception("用户名或密码有误!");
                    }
                    return Result.ok(map);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                try {
                    Teacher teacher = teacherService.login(loginForm);
                    if (null!=teacher) {
                        //用户类型和ID转换成一个密文，以token的形式向客户端反馈
                        map.put("token",JwtHelper.createToken(teacher.getId().longValue(),loginForm.getUserType()));
                    }else {
                        throw new Exception("用户名或密码有误!");
                    }
                    return Result.ok(map);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }
        return Result.fail().message("查无此用户!");
    }

    @GetMapping("/getInfo")
    public Result getInfoByToken(@RequestHeader String token){
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration){
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //从token中解析出用户ID和用户类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        Map<String,Object> map = new HashMap<>();
        switch (userType){
            case 1:
                Admin admin = adminService.getAdminById(userId);
                map.put("userType",1);
                map.put("user",admin);
                break;
            case 2:
                Student student = studentService.getStudentById(userId);
                map.put("userType",1);
                map.put("user",student);
                break;
            case 3:
                Teacher teacher = teacherService.getTeacherById(userId);
                map.put("userType",1);
                map.put("user",teacher);
                break;
        }
        return Result.ok(map);
    }

    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(MultipartFile multipartFile){
        String dirPaht = "D:/learning-materials/zhxy/target/classes/public/upload/student/";
        String portraitPath = "/upload/student/";
        //保存文件
        Map<String, Object> result = UploadFile.getUploadResult(multipartFile, dirPaht, portraitPath);
        //响应图片的路径
        return Result.ok(result.get("portrait_path"));
    }


    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(@PathVariable("oldPwd") String oldPwd,
                            @PathVariable("newPwd") String newPwd,
                            @RequestHeader("token") String token){
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration){
            //token 过期
            return Result.fail().message("Token失效，请重新登录后修改密码!");
        }
        //获取用户ID和类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        oldPwd = MD5.encrypt(oldPwd);
        newPwd = MD5.encrypt(newPwd);

        switch (userType){
            case 1:
                LambdaQueryWrapper<Admin> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(Admin::getId,userId.intValue());
                queryWrapper1.eq(Admin::getPassword,oldPwd);
                Admin admin = adminService.getOne(queryWrapper1);
                if (admin!=null){
                    //修改
                    admin.setPassword(newPwd);
                    adminService.saveOrUpdate(admin);
                }else {
                    return Result.fail().message("原密码有误!");
                }
                break;
            case 2:
                LambdaQueryWrapper<Student> queryWrapper2 = new LambdaQueryWrapper<>();
                queryWrapper2.eq(Student::getId,userId.intValue());
                queryWrapper2.eq(Student::getPassword,oldPwd);
                Student student = studentService.getOne(queryWrapper2);
                if (student!=null){
                    //修改
                    student.setPassword(newPwd);
                    studentService.saveOrUpdate(student);
                }else {
                    return Result.fail().message("原密码有误!");
                }
                break;
            case 3:
                LambdaQueryWrapper<Teacher> queryWrapper3 = new LambdaQueryWrapper<>();
                queryWrapper3.eq(Teacher::getId,userId.intValue());
                queryWrapper3.eq(Teacher::getPassword,oldPwd);
                Teacher teacher = teacherService.getOne(queryWrapper3);
                if (teacher!=null){
                    //修改
                    teacher.setPassword(newPwd);
                    teacherService.saveOrUpdate(teacher);
                }else {
                    return Result.fail().message("原密码有误!");
                }
                break;
        }
        return Result.ok();
    }

}
