package com.spy.jxc.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spy.jxc.admin.common.jwt.CurrentUser;
import com.spy.jxc.admin.entity.Log;
import com.spy.jxc.admin.mapper.LogMapper;
import com.spy.jxc.admin.service.LogService;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Override
    public List<Log> listWithUser(Log query) {
        return baseMapper.listWithUser(query);
    }

    @Override
    public void saveLog(String logType, String content, Integer userId) {
        Log log = new Log();
        log.setLogType(logType);
        log.setContent(content);
        log.setLogDate(new Date());
        log.setUserId(userId);
        baseMapper.insert(log);
    }

    @Override
    public void record(String logType, String content) {
        saveLog(logType, content, CurrentUser.getUserId());
    }
}
