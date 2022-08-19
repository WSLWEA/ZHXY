package com.example.zhxy.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zhxy.mapper.GradeMapper;
import com.example.zhxy.pojo.Grade;
import com.example.zhxy.service.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    @Override
    public IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName) {
        LambdaQueryWrapper<Grade> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(gradeName)){
            queryWrapper.like(Grade::getManager,gradeName);
        }
        queryWrapper.orderByAsc(Grade::getId);
        return baseMapper.selectPage(page,queryWrapper);
    }
}
