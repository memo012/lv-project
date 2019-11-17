package com.lv.adminsys.modules.act;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lv.adminsys.common.utils.TimeUtil;
import com.lv.adminsys.modules.dao.LvLeaveDao;
import com.lv.adminsys.modules.dao.LvTeacherDao;
import com.lv.adminsys.modules.dao.LvUserDao;
import com.lv.adminsys.modules.entity.LvLeaveEntity;
import com.lv.adminsys.modules.entity.LvTeacherEntity;
import com.lv.adminsys.modules.entity.LvUserEntity;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: qiang
 * @ClassName: LeaveService
 * @Description: activiti工具
 * @Date: 2019/11/5 上午10:52
 * @Version: 1.0
 **/
@Service
@Transactional
public class LeaveService {

    @Resource
    private TaskService taskService;

    @Resource
    private RuntimeService runtimeService;

    @Autowired
    private LvLeaveDao lvLeaveDao;

    @Autowired
    private LvTeacherDao lvTeacherDao;

    @Autowired
    private LvUserDao lvUserDao;

    /**
     * 班主任审批  ${leaveService.findTeacherClass(execution)}
     *
     * @param delegateExecution
     * @return
     */
    public List<String> findTeacherClass(DelegateExecution delegateExecution) {
//        delegateExecution.getProcessInstanceBusinessKey();
        LvLeaveEntity lvLeaveEntity = lvLeaveDao.selectOne(new QueryWrapper<LvLeaveEntity>().eq("lv_id", delegateExecution.getProcessInstanceBusinessKey()));
        LvUserEntity userEntity = lvUserDao.selectOne(new QueryWrapper<LvUserEntity>().eq("lv_user_num", lvLeaveEntity.getLvUserNum()));
        return Arrays.asList(userEntity.getLvTeacherNum());
    }

    /**
     * 领导审批  ${leaveService.findLeaderManager(execution)}
     *
     * @param delegateExecution
     * @return
     */
    public List<String> findLeaderManager(DelegateExecution delegateExecution) {
        // 业务ID
//        delegateExecution.getProcessInstanceBusinessKey();
        List<String> list = new ArrayList<>();
        LvLeaveEntity lvLeaveEntity = lvLeaveDao.selectOne(new QueryWrapper<LvLeaveEntity>().eq("lv_id", delegateExecution.getProcessInstanceBusinessKey()));
        // 非教学期间
        if (new TimeUtil().dateToWeek(lvLeaveEntity.getLvBeginTime()).contains("六") ||
                new TimeUtil().dateToWeek(lvLeaveEntity.getLvBeginTime()).contains("日")) {
            List<LvTeacherEntity> lv_teacher_role = lvTeacherDao.selectList(new QueryWrapper<LvTeacherEntity>().eq("lv_role", 3));
            for (LvTeacherEntity lv :
                    lv_teacher_role) {
                list.add(lv.getLvTeacherNum());
            }
            return list;
        }
        // 教学期间
        List<LvTeacherEntity> lv_teacher_role = lvTeacherDao.selectList(new QueryWrapper<LvTeacherEntity>().eq("lv_role", 4));
        for (LvTeacherEntity lv :
                lv_teacher_role) {
            list.add(lv.getLvTeacherNum());
        }
        return list;
    }

    // 修改业务状态
    // #{leaveService.changeStatus(execution, 'ing')}
    // #{leaveService.changeStatus(execution, 'reject')}
    // #{leaveService.changeStatus(execution, 'pass')}

    public void changeStatus(DelegateExecution delegateExecution, String status) {
        System.out.println("现在应该修改请假单状态,状态为:" + status);
        String id = delegateExecution.getProcessInstanceBusinessKey();
        LvLeaveEntity leaveEntity = lvLeaveDao.selectOne(new QueryWrapper<LvLeaveEntity>().eq("lv_id", id));
        // 修改状态
        if (leaveEntity != null) {
            leaveEntity.setLvStatus(status);
        }
        lvLeaveDao.updateById(leaveEntity);
    }

    /**
     * 启动流程
     *
     * @return
     */
    public ProcessInstance startProcess() {
        ProcessInstance leaveProcess = runtimeService.startProcessInstanceByKey("leaveProcess"); // key 对应的是流程图id
        return leaveProcess;
    }

    /**
     * 启动流程
     *
     * @param id 请假单ID
     * @return
     */
    public ProcessInstance startProcess(String id) {
        ProcessInstance leaveProcess = runtimeService.startProcessInstanceByKey("leaveProcess", id);
        String processInstanceId = leaveProcess.getProcessInstanceId();
        LvLeaveEntity lvLeaveEntity = lvLeaveDao.selectById(id);
        lvLeaveEntity.setLvProcessInstanceId(processInstanceId);
        lvLeaveDao.updateById(lvLeaveEntity);
        return leaveProcess;
    }

    /**
     * 查询代办
     *
     * @param userId 审核人ID
     * @return
     */
    public List<Task> findTaskByUserId(String userId) {
        List<Task> list = taskService.createTaskQuery().processDefinitionKey("leaveProcess").taskCandidateOrAssigned(userId).list();
        return list;
    }

    /**
     * 审核任务
     *
     * @param taskId 我审批哪个任务
     * @param userId 审批人是谁
     * @param aduit  审批的意见(通过为"pass";驳回为"reject")
     */
    public void completeTask(String taskId, String userId, String aduit, String comment) {
        // 签收任务
        taskService.claim(taskId, userId);
        // 添加批注时候的审核人，通常应该从session获取
        Authentication.setAuthenticatedUserId(userId);
        // 使用任务id,获取任务对象，获取流程实例id
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //利用任务对象，获取流程实例id
        String processInstancesId=task.getProcessInstanceId();
        taskService.addComment(taskId, processInstancesId, comment);

        // 完成任务
        Map<String, Object> map = new HashMap<>();
        map.put("aduit", aduit);
        taskService.complete(taskId, map);
    }
}
