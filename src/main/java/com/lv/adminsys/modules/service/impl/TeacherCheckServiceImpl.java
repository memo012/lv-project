package com.lv.adminsys.modules.service.impl;

import com.lv.adminsys.modules.act.LeaveService;
import com.lv.adminsys.modules.dao.LvLeaveDao;
import com.lv.adminsys.modules.entity.LvCheckEntity;
import com.lv.adminsys.modules.entity.LvLeaveEntity;
import com.lv.adminsys.modules.service.ITeacherCheckService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: qiang
 * @ClassName: TeacherCheckServiceImpl
 * @Description: TODO
 * @Date: 2019/11/15 下午7:43
 * @Version: 1.0
 **/
@Service
@Transactional
public class TeacherCheckServiceImpl implements ITeacherCheckService {

    @Resource
    private LeaveService leaveService;

    @Resource
    private RuntimeService runtimeService;

    @Autowired
    private LvLeaveDao lvLeaveDao;

    @Override
    public List<LvLeaveEntity> queryTaskByUserId(String userId) {
        List<LvLeaveEntity> list = new ArrayList<>();
        List<Task> taskByUserId = leaveService.findTaskByUserId(userId);
        for (Task task:
                taskByUserId) {
            // 怎么通过Task,来取得流程实例的业务单据id
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
            String businessKey = processInstance.getBusinessKey();
            LvLeaveEntity leaveInfo = lvLeaveDao.selectById(businessKey);
            leaveInfo.setTaskId(task.getId());
            list.add(leaveInfo);
        }
        return list;
    }

    @Override
    public void completeTask(String taskId, String userId, String aduit, String comment) {
        leaveService.completeTask(taskId, userId, aduit, comment);
    }
}
