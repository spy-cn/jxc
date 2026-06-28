package com.spy.jxc.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spy.jxc.admin.entity.Log;
import java.util.List;

public interface LogService extends IService<Log> {
    List<Log> listWithUser(Log query);
    void saveLog(String logType, String content, Integer userId);
    void record(String logType, String content);
}
