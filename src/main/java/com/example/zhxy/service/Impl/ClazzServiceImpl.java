package com.example.zhxy.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zhxy.mapper.ClazzMapper;
import com.example.zhxy.pojo.Clazz;
import com.example.zhxy.service.ClazzService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {

    @Override
    public IPage<Clazz> getClazzByOpr(Page<Clazz> page,Clazz clazz) {
        LambdaQueryWrapper<Clazz> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(clazz.getName())){
            queryWrapper.like(Clazz::getName,clazz.getName());
        }
        if (StringUtils.hasText(clazz.getGrade_name())){
            queryWrapper.like(Clazz::getGrade_name,clazz.getGrade_name());
        }
        queryWrapper.orderByAsc(Clazz::getNumber);
       return baseMapper.selectPage(page,queryWrapper);
    }
}
