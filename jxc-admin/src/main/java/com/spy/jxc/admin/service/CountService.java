package com.spy.jxc.admin.service;

import java.util.List;
import java.util.Map;

public interface CountService {
    List<Map<String, Object>> purchaseCount(String startDate, String endDate, Integer typeId);
    List<Map<String, Object>> saleCount(String startDate, String endDate, Integer typeId);
    List<Map<String, Object>> supplierCount(String startDate, String endDate);
    List<Map<String, Object>> customerCount(String startDate, String endDate);
    List<Map<String, Object>> saleDayCount(String startDate, String endDate);
    List<Map<String, Object>> saleMonthCount(String startDate, String endDate);
}
