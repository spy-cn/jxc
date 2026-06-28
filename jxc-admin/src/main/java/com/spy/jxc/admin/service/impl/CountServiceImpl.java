package com.spy.jxc.admin.service.impl;

import com.spy.jxc.admin.mapper.CountMapper;
import com.spy.jxc.admin.service.CountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CountServiceImpl implements CountService {

    private final CountMapper countMapper;

    @Override public List<Map<String, Object>> purchaseCount(String s, String e, Integer t) { return countMapper.purchaseCount(s, e, t); }
    @Override public List<Map<String, Object>> saleCount(String s, String e, Integer t) { return countMapper.saleCount(s, e, t); }
    @Override public List<Map<String, Object>> supplierCount(String s, String e) { return countMapper.supplierCount(s, e); }
    @Override public List<Map<String, Object>> customerCount(String s, String e) { return countMapper.customerCount(s, e); }
    @Override public List<Map<String, Object>> saleDayCount(String s, String e) { return countMapper.saleDayCount(s, e); }
    @Override public List<Map<String, Object>> saleMonthCount(String s, String e) { return countMapper.saleMonthCount(s, e); }
}
