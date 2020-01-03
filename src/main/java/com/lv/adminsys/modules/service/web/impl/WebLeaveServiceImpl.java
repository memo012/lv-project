package com.lv.adminsys.modules.service.web.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lv.adminsys.common.constant.LvException;
import com.lv.adminsys.common.enums.AccountTypeEnum;
import com.lv.adminsys.common.enums.MessageMarkEnum;
import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.common.utils.TimeUtil;
import com.lv.adminsys.modules.dao.LvLeaveDao;
import com.lv.adminsys.modules.dao.LvMessageDao;
import com.lv.adminsys.modules.dao.LvUserDao;
import com.lv.adminsys.modules.entity.LvLeaveEntity;
import com.lv.adminsys.modules.entity.LvMessageEntity;
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
    private LvMessageDao lvMessageDao;

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

    @Override
    public JSONResult markMsgIsRead(String lvMessageId, Integer accountType) {
        return markMsgStatus(lvMessageId, accountType, MessageMarkEnum.IS_READ.getTypeName());
    }

    /**
     * @param lvMessageId 消息id
     * @param accountType 账号类型  0 -- 学生  1 -- 老师
     * @param markType    标记类型   1 -- 已读  -1  -- 删除   2 -- 全部标记  -2 -- 全部删除
     * @return 结果
     */
    private JSONResult markMsgStatus(String lvMessageId, Integer accountType, Integer markType) {
        if (StringUtils.isEmpty(lvMessageId) || accountType == null) {
            return JSONResult.build(402, LvException.ErrorMsg.REQUEST_PARAM_ERROR, null);
        }
        // 判断是学生或老师
        if (accountType.equals(AccountTypeEnum.STUDENT_TYPE.getTypeName())) {
            // 删除操作 还是  标记操作
            if (markType.equals(MessageMarkEnum.IS_READ.getTypeName())) {
                return markStudentStatus(lvMessageId, MessageMarkEnum.IS_READ.getTypeName());
            }
            if (markType.equals(MessageMarkEnum.IS_DELETE.getTypeName())) {
                return markStudentStatus(lvMessageId, MessageMarkEnum.IS_DELETE.getTypeName());
            }
            if (markType.equals(MessageMarkEnum.IS_ALL_READ.getTypeName())) {
                return markStudentAllStatus(lvMessageId, MessageMarkEnum.IS_ALL_READ.getTypeName());
            }
            if (markType.equals(MessageMarkEnum.IS_ALL_DELETE.getTypeName())) {
                return markStudentAllStatus(lvMessageId, MessageMarkEnum.IS_ALL_DELETE.getTypeName());
            }
        } else {
            // 老师
            if (markType.equals(MessageMarkEnum.IS_READ.getTypeName())) {
                return markTeacherStatus(lvMessageId, MessageMarkEnum.IS_READ.getTypeName());
            }
            if (markType.equals(MessageMarkEnum.IS_DELETE.getTypeName())) {
                return markTeacherStatus(lvMessageId, MessageMarkEnum.IS_DELETE.getTypeName());
            }
            if (markType.equals(MessageMarkEnum.IS_ALL_READ.getTypeName())) {
                return markTeacherAllStatus(lvMessageId, MessageMarkEnum.IS_ALL_READ.getTypeName());
            }
            if (markType.equals(MessageMarkEnum.IS_ALL_DELETE.getTypeName())) {
                return markTeacherAllStatus(lvMessageId, MessageMarkEnum.IS_ALL_DELETE.getTypeName());
            }
        }
        return JSONResult.errorMsg("处理错误");
    }


    @Override
    public JSONResult markMsgDelete(String lvMessageId, Integer accountType) {
        return markMsgStatus(lvMessageId, accountType, MessageMarkEnum.IS_DELETE.getTypeName());
    }

    @Override
    public JSONResult markAllMsgIsRead(String account, Integer accountType) {
        return markMsgStatus(account, accountType, MessageMarkEnum.IS_ALL_READ.getTypeName());
    }

    @Override
    public JSONResult markAllMsgDelete(String account, Integer accountType) {
        return markMsgStatus(account, accountType, MessageMarkEnum.IS_ALL_DELETE.getTypeName());
    }


    private JSONResult markStudentAllStatus(String account, Integer typeName) {
        List<LvLeaveEntity> leaveIsRead = lvLeaveDao.selectList(new QueryWrapper<LvLeaveEntity>().eq("lv_user_num", account).ne("lv_book", MessageMarkEnum.IS_DELETE.getTypeName()));
        if (CollectionUtils.isEmpty(leaveIsRead)) {
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        if (typeName.equals(MessageMarkEnum.IS_ALL_READ.getTypeName())) {
            for (LvLeaveEntity lv :
                    leaveIsRead) {
                lv.setLvBook(MessageMarkEnum.IS_READ.getTypeName());
                lvLeaveDao.updateById(lv);
            }
        } else if (typeName.equals(MessageMarkEnum.IS_ALL_DELETE.getTypeName())) {
            for (LvLeaveEntity lv :
                    leaveIsRead) {
                lv.setLvBook(MessageMarkEnum.IS_DELETE.getTypeName());
                lvLeaveDao.updateById(lv);
            }
        }
        return JSONResult.ok();
    }

    private JSONResult markStudentStatus(String lvMessageId, Integer typeName) {
        LvLeaveEntity leaveEntity = lvLeaveDao.selectById(lvMessageId);
        if (judgeObjectParamIsBlank(leaveEntity)) {
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        if (typeName.equals(MessageMarkEnum.IS_READ.getTypeName())) {
            leaveEntity.setLvBook(MessageMarkEnum.IS_READ.getTypeName());
        } else {
            leaveEntity.setLvBook(MessageMarkEnum.IS_DELETE.getTypeName());
        }
        int i = lvLeaveDao.updateById(leaveEntity);
        if (i > 0) {
            return JSONResult.ok();
        }
        return JSONResult.errorMsg("修改失败");
    }

    private <T> Boolean judgeObjectParamIsBlank(T messageEntity) {
        if (messageEntity == null) {
            return true;
        }
        return false;
    }

    private JSONResult markTeacherAllStatus(String account, Integer typeName) {
        List<LvMessageEntity> teacherLists = lvMessageDao.selectList(new QueryWrapper<LvMessageEntity>().eq("lv_teacher_num", account).eq("lv_book", MessageMarkEnum.NO_READ.getTypeName()));
        if (CollectionUtils.isEmpty(teacherLists)) {
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        if (typeName.equals(MessageMarkEnum.IS_ALL_READ.getTypeName())) {
            for (LvMessageEntity lv :
                    teacherLists) {
                lv.setLvBook(MessageMarkEnum.IS_READ.getTypeName());
                lvMessageDao.updateById(lv);
            }
        } else if (typeName.equals(MessageMarkEnum.IS_ALL_DELETE.getTypeName())) {
            for (LvMessageEntity lv :
                    teacherLists) {
                lv.setLvBook(MessageMarkEnum.IS_DELETE.getTypeName());
                lvMessageDao.updateById(lv);
            }
        }
        return JSONResult.ok();
    }

    private JSONResult markTeacherStatus(String lvMessageId, Integer typeName) {
        LvMessageEntity messageEntity = lvMessageDao.selectById(lvMessageId);
        if (judgeObjectParamIsBlank(messageEntity)) {
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        if (typeName.equals(MessageMarkEnum.IS_READ.getTypeName())) {
            messageEntity.setLvBook(MessageMarkEnum.IS_READ.getTypeName());
        } else {
            messageEntity.setLvBook(MessageMarkEnum.IS_DELETE.getTypeName());
        }
        int i = lvMessageDao.updateById(messageEntity);
        if (i > 0) {
            return JSONResult.ok();
        }
        return JSONResult.errorMsg("修改失败");
    }

    private List<WebLeaveListVO> findStudentList(String account) {
        List<WebLeaveListVO> list = new ArrayList<>();
        IPage<LvLeaveEntity> lvLeaveEntities =
                lvLeaveDao.selectPage(new Page<>(0, 30),
                        new QueryWrapper<LvLeaveEntity>()
                                .eq("lv_user_num", account)
                                .ne("lv_status", "ing")
                                .ne("lv_book", -1)
                                .orderByDesc("lv_id"));
        if (CollectionUtils.isEmpty(lvLeaveEntities.getRecords())) {
            return list;
        }
        for (LvLeaveEntity lv :
                lvLeaveEntities.getRecords()) {
            String time = historyProcess(lv.getLvProcessInstanceId());
            list.add(new WebLeaveListVO(
                    null, lv.getLvStatus(), time, lv.getLvId(), lv.getLvBook(), null, null
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
        if (!CollectionUtils.isEmpty(taskList)) {
            for (Task task :
                    taskList) {
                LvLeaveEntity leaveInfo = teacherCheckServiceImpl.findTask(task.getProcessInstanceId());
                LvUserEntity userEntity = lvUserDao.selectOne(new QueryWrapper<LvUserEntity>().eq("lv_user_num", leaveInfo.getLvUserNum()));
                LvMessageEntity lvTask = lvMessageDao.selectOne(new QueryWrapper<LvMessageEntity>().eq("lv_task_id", task.getId()));
                if (lvTask == null) {
                    lvMessageDao.insert(new LvMessageEntity(
                            new TimeUtil().getLongTime(), task.getId(), leaveInfo.getLvId(), 0, account, userEntity.getLvUserName(), leaveInfo.getLvCreateTime()
                    ));
                }
            }
        }
        IPage<LvMessageEntity> lvMessage =
                lvMessageDao.selectPage(new Page<>(0, 50),
                        new QueryWrapper<LvMessageEntity>()
                                .eq("lv_teacher_num", account)
                                .ne("lv_book", -1)
                                .orderByDesc("lv_message_id"));
        if (CollectionUtils.isEmpty(lvMessage.getRecords())) {
            return null;
        }
        for (LvMessageEntity listVO :
                lvMessage.getRecords()) {
            LvLeaveEntity lvLeaveEntity = lvLeaveDao.selectOne(new QueryWrapper<LvLeaveEntity>().eq("lv_id", listVO.getLvId()));
            list.add(new WebLeaveListVO(
                    listVO.getLvMessageId(), lvLeaveEntity.getLvStatus(), lvLeaveEntity.getLvCreateTime(), lvLeaveEntity.getLvId(), listVO.getLvBook(), listVO.getLvUserName(), listVO.getLvTaskId()
            ));
        }
        return list;
    }
}