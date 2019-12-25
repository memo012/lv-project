package com.lv.adminsys.modules.service.web.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lv.adminsys.common.constant.LvException;
import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.common.utils.TimeUtil;
import com.lv.adminsys.modules.dao.LvCheckDao;
import com.lv.adminsys.modules.dao.LvLeaveDao;
import com.lv.adminsys.modules.dao.LvUserDao;
import com.lv.adminsys.modules.entity.LvCheckEntity;
import com.lv.adminsys.modules.entity.LvLeaveEntity;
import com.lv.adminsys.modules.entity.LvUserEntity;
import com.lv.adminsys.modules.service.impl.TeacherCheckServiceImpl;
import com.lv.adminsys.modules.service.web.IWebLeaveService;
import com.lv.adminsys.modules.vo.web.WebLeaveListVO;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Qiang
 * @version 1.0
 * @description TODO
 * @date 2019/12/25 下午5:20
 **/
@Service
@Transactional
public class WebLeaveServiceImpl implements IWebLeaveService {

    /**
     * 老师工号前缀
     */
    @Value("${teacher.start}")
    private String teacherNumStart;

    @Autowired
    private LvLeaveDao lvLeaveDao;

    @Resource
    private HistoryService historyService;

    @Autowired
    private LvCheckDao lvCheckDao;

    @Autowired
    private TeacherCheckServiceImpl teacherCheckServiceImpl;

    @Autowired
    private LvUserDao lvUserDao;


    @Override
    public JSONResult findMessageList(String account) {
        if (StringUtils.isEmpty(account)) {
            return JSONResult.build(402, LvException.ErrorMsg.REQUEST_PARAM_ERROR, null);
        }
        List<WebLeaveListVO> list;
        if (account.startsWith(teacherNumStart)) {
            list = findTeacherList(account);
        } else {
            list = findStudentList(account);
        }
        if (CollectionUtils.isEmpty(list) || list.size() == 0) {
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        return JSONResult.ok(list);
    }

    private List<WebLeaveListVO> findStudentList(String account) {
        List<WebLeaveListVO> list = new ArrayList<>();
        List<LvLeaveEntity> lvLeaveEntities =
                lvLeaveDao.selectList(new QueryWrapper<LvLeaveEntity>()
                        .eq("lv_user_num", account)
                        .ne("lv_status", "ing"));
        if (CollectionUtils.isEmpty(lvLeaveEntities)) {
            return list;
        }
        for (LvLeaveEntity lv :
                lvLeaveEntities) {
            String time = historyProcess(lv.getLvProcessInstanceId());
            list.add(new WebLeaveListVO(
                    lv.getLvStatus(), time, lv.getLvId(), lv.getLvBook(), null, null
            ));
        }
        return list;
    }

    private String historyProcess(String processInstanceId) {
        String time = "";
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricTaskInstanceStartTime().asc()
                .list();
        for (HistoricTaskInstance his :
                list) {
            time = new TimeUtil().getParseDateForSix(his.getEndTime());
        }
        return time;
    }

    private List<WebLeaveListVO> findTeacherList(String account) {
        List<WebLeaveListVO> list = new ArrayList<>();
        List<Task> taskList = teacherCheckServiceImpl.checkTaskByTeacherNum(account);
        if (CollectionUtils.isEmpty(taskList)) {
            return null;
        }
        for (Task task :
                taskList) {
            LvLeaveEntity leaveInfo = teacherCheckServiceImpl.findTask(task.getProcessInstanceId());
            LvUserEntity userEntity = lvUserDao.selectOne(new QueryWrapper<LvUserEntity>().eq("lv_user_num", leaveInfo.getLvUserNum()));
            list.add(new WebLeaveListVO(
                    leaveInfo.getLvStatus(), leaveInfo.getLvCreateTime(), leaveInfo.getLvId(), leaveInfo.getLvBook(), userEntity.getLvUserName(), task.getId()
            ));
        }
        List<LvCheckEntity> lvCheck = lvCheckDao.selectList(new QueryWrapper<LvCheckEntity>().eq("lv_teacher_num", account));
        for (LvCheckEntity lv :
                lvCheck) {
            LvLeaveEntity leaveInfo = lvLeaveDao.selectOne(new QueryWrapper<LvLeaveEntity>().eq("lv_id", lv.getLvId()));
            LvUserEntity userEntity = lvUserDao.selectOne(new QueryWrapper<LvUserEntity>().eq("lv_user_num", leaveInfo.getLvUserNum()));
            list.add(new WebLeaveListVO(
                    leaveInfo.getLvStatus(), leaveInfo.getLvCreateTime(), leaveInfo.getLvId(), lv.getLvBook(), userEntity.getLvUserName(), null
            ));
        }
        return list;
    }
}