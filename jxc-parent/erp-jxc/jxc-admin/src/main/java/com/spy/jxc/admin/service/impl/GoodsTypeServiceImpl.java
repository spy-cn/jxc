package com.spy.jxc.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spy.jxc.admin.entity.GoodsType;
import com.spy.jxc.admin.mapper.GoodsTypeMapper;
import com.spy.jxc.admin.service.GoodsTypeService;
import org.springframework.stereotype.Service;

@Service
public class GoodsTypeServiceImpl extends ServiceImpl<GoodsTypeMapper, GoodsType> implements GoodsTypeService {
}
