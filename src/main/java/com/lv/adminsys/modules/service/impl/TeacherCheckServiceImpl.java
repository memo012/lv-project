package com.lv.adminsys.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lv.adminsys.common.constant.LvException;
import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.common.utils.TimeUtil;
import com.lv.adminsys.modules.act.LeaveService;
import com.lv.adminsys.modules.dao.LvLeaveDao;
import com.lv.adminsys.modules.dao.LvUserDao;
import com.lv.adminsys.modules.entity.LvLeaveEntity;
import com.lv.adminsys.modules.entity.LvUserEntity;
import com.lv.adminsys.modules.service.AsyncService;
import com.lv.adminsys.modules.service.ITeacherCheckService;
import com.lv.adminsys.modules.vo.leave.CompleteTaskRequest;
import com.lv.adminsys.modules.vo.leave.TeacherCheckResponse;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qiang
 * @version 1.0
 * @description 审核人审核接口
 * @date 2019/11/15 下午7:43
 **/
@Service
@Transactional
public class TeacherCheckServiceImpl implements ITeacherCheckService {

    private final String CHECK_PASS = "pass";

    private final String CHECK_REJECT = "reject";

    @Resource
    private LeaveService leaveService;

    @Resource
    private RuntimeService runtimeService;

    @Autowired
    private LvLeaveDao lvLeaveDao;

    @Autowired
    private LvUserDao lvUserDao;

    @Autowired
    private AsyncService asyncService;


    @Override
    public JSONResult queryTaskByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return JSONResult.build(402, LvException.ErrorMsg.REQUEST_PARAM_ERROR, null);
        }
        List<TeacherCheckResponse> list = new ArrayList<>();
        List<Task> taskByUserId = checkTaskByTeacherNum(userId);
        if (CollectionUtils.isEmpty(taskByUserId)) {
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        for (Task task :
                taskByUserId) {
            LvLeaveEntity leaveInfo = findTask(task.getProcessInstanceId());
            if (leaveInfo != null) {
                LvUserEntity useMsgByUserNum = lvUserDao.findUseMsgByUserNum(leaveInfo.getLvUserNum());
                Integer leaveNum = lvLeaveDao.selectCount(new QueryWrapper<LvLeaveEntity>().eq("lv_user_num", leaveInfo.getLvUserNum()));
                try {
                    list.add(new TeacherCheckResponse(
                            useMsgByUserNum.getLvUserName(), useMsgByUserNum.getLvUserNum(),
                            new TimeUtil().StringFourTime(leaveInfo.getLvBeginTime()),
                            new TimeUtil().StringFourTime(leaveInfo.getLvEndTime()), leaveNum,
                            task.getId(), leaveInfo.getLvId()
                    ));
                } catch (ParseException e) {
                    return JSONResult.build(500, LvException.ErrorMsg.DATE_ERROR, null);
                }
            }
        }
        return JSONResult.ok(list);
    }

    /**
     * 查询老师未审核任务
     *
     * @param userId 老师工号
     */
    public List<Task> checkTaskByTeacherNum(String userId) {
        return leaveService.findTaskByUserId(userId);
    }

    /**
     *
     * @param processInstanceId 进程id
     * @return
     */
    public LvLeaveEntity findTask(String processInstanceId){
        // 怎么通过Task,来取得流程实例的业务单据id
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String businessKey = processInstance.getBusinessKey();
        return lvLeaveDao.selectById(businessKey);
    }



    @Override
    public JSONResult completeTask(CompleteTaskRequest taskRequest) {
        if (!taskRequest.createValidate()) {
            return JSONResult.build(402, LvException.ErrorMsg.REQUEST_PARAM_ERROR, null);
        }
        if (CHECK_PASS.equals(taskRequest.getAduit())) {
            taskRequest.setComment("pass:批准该同学请假");
        }
        if (CHECK_REJECT.equals(taskRequest.getAduit())) {
            if (StringUtils.isEmpty(taskRequest.getComment())) {
                taskRequest.setComment("reject:该同学不符合请假条件");
            } else {
                taskRequest.setComment("reject:" + taskRequest.getComment());
            }
        }
        asyncService.addCheckMess(taskRequest.getUserId(), taskRequest.getLvId());
        leaveService.completeTask(taskRequest.getTaskId(), taskRequest.getUserId(), taskRequest.getAduit(), taskRequest.getComment());
        return JSONResult.ok();
    }
}
