package com.example.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zhxy.pojo.Clazz;
import com.example.zhxy.service.ClazzService;
import com.example.zhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(@PathVariable("pageNo") Integer pageNo,
                                 @PathVariable("pageSize") Integer pageSize,
                                 Clazz clazz){
        Page<Clazz> page = new Page<>(pageNo,pageSize);
        IPage<Clazz> iPage = clazzService.getClazzByOpr(page,clazz);
        return Result.ok(iPage);
    }

    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@RequestBody Clazz clazz){
        return Result.ok(clazzService.saveOrUpdate(clazz));
    }
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@RequestBody List<Integer> id){
        return Result.ok(clazzService.removeByIds(id));
    }

    @GetMapping("/getClazzs")
    public Result getClazzs(){
        List<Clazz> clazzes = clazzService.list();
        return Result.ok(clazzes);
    }
}
