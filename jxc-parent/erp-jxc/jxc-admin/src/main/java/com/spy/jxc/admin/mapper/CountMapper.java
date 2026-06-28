package com.spy.jxc.admin.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface CountMapper {
    List<Map<String, Object>> purchaseCount(@Param("startDate") String s, @Param("endDate") String e, @Param("typeId") Integer typeId);
    List<Map<String, Object>> saleCount(@Param("startDate") String s, @Param("endDate") String e, @Param("typeId") Integer typeId);
    List<Map<String, Object>> supplierCount(@Param("startDate") String s, @Param("endDate") String e);
    List<Map<String, Object>> customerCount(@Param("startDate") String s, @Param("endDate") String e);
    List<Map<String, Object>> saleDayCount(@Param("startDate") String s, @Param("endDate") String e);
    List<Map<String, Object>> saleMonthCount(@Param("startDate") String s, @Param("endDate") String e);
}
