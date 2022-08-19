package com.example.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zhxy.pojo.Grade;
import com.example.zhxy.service.GradeService;
import com.example.zhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;
    @ApiOperation("分页查询年级信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "当前页码",required = true,paramType = "path",example = "1",dataType = "int"),
            @ApiImplicitParam(name = "pageSize",value = "每页显示的条数",required = true,paramType = "path",example = "2",dataType = "int"),
            @ApiImplicitParam(name = "gradeName",value = "分页查询的模糊匹配名称",required = false,paramType = "query",dataType = "int")
    })
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(@PathVariable Integer pageNo,
                            @PathVariable Integer pageSize,
                            String gradeName){
        //分页带条件查询
        Page<Grade> page = new Page<>(pageNo,pageSize);
       //通过服务层查询
        IPage<Grade> pageResult = gradeService.getGradeByOpr(page,gradeName);

        return Result.ok(pageResult);
    }
    @ApiOperation("新增或修改年级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grade",value = "班级对象",paramType = "body",dataType = "Grade")
    })
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@RequestBody Grade grade){
        return Result.ok(gradeService.saveOrUpdate(grade));
    }
    //请求 URL: http://localhost:8080/sms/gradeController/deleteGrade
    //请求方法: DELETE
    @ApiOperation("删除年级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "年级的ID",required = true,paramType = "query",dataType = "list")
    })
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@RequestBody List<Integer> id){
        return Result.ok(gradeService.removeByIds(id));
    }

    //请求 URL: http://localhost:8080/sms/gradeController/getGrades
    //请求方法: GET

    @GetMapping("/getGrades")
    public Result getGrades(){
        List<Grade> grade = gradeService.list(null);
        return Result.ok(grade);
    }
}
