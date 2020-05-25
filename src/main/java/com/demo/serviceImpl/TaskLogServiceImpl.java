package com.demo.serviceImpl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.dao.TaskLogMapper;
import com.demo.entities.TaskLog;
import com.demo.service.ITaskLogService;
import org.springframework.stereotype.Service;

@Service
public class TaskLogServiceImpl extends ServiceImpl<TaskLogMapper, TaskLog> implements ITaskLogService {

}
