package com.spy.jxc.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spy.jxc.admin.entity.Supplier;
import com.spy.jxc.admin.mapper.SupplierMapper;
import com.spy.jxc.admin.service.SupplierService;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements SupplierService {
}
