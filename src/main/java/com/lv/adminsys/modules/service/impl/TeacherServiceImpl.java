package com.lv.adminsys.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lv.adminsys.common.constant.Constant;
import com.lv.adminsys.common.constant.LvException;
import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.common.utils.RedisOperator;
import com.lv.adminsys.common.utils.TimeUtil;
import com.lv.adminsys.modules.dao.LvCheckDao;
import com.lv.adminsys.modules.dao.LvLeaveDao;
import com.lv.adminsys.modules.dao.LvTeacherDao;
import com.lv.adminsys.modules.dao.LvUserDao;
import com.lv.adminsys.modules.entity.LvCheckEntity;
import com.lv.adminsys.modules.entity.LvLeaveEntity;
import com.lv.adminsys.modules.entity.LvTeacherEntity;
import com.lv.adminsys.modules.entity.LvUserEntity;
import com.lv.adminsys.modules.service.ITeacherService;
import com.lv.adminsys.modules.vo.leave.ApplyItemResponse;
import com.lv.adminsys.modules.vo.leave.TeacherHisItems;
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
 * @author Qiang
 * @version 1.0
 * @description TODO
 * @date 2019/12/7 下午10:15
 **/
@Service
@Transactional
public class TeacherServiceImpl implements ITeacherService {

    @Autowired
    private LvTeacherDao lvTeacherDao;

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private LvLeaveDao lvLeaveDao;

    @Autowired
    private LvUserDao lvUserDao;

    @Autowired
    private LvCheckDao lvCheckDao;

    @Resource
    private TeacherCheckServiceImpl teacherCheckService;

    @Override
    public LvTeacherEntity findUserMsgByTeacherNum(String userNum) {
        LvTeacherEntity userEntity;
        if (redisOperator.hasHkey(Constant.Redis.TEACHER_MESSAGE, userNum)) {
            userEntity = (LvTeacherEntity) redisOperator.hget(Constant.Redis.TEACHER_MESSAGE, userNum);
        } else {
            userEntity = lvTeacherDao.findUseMsgByTeacherNum(userNum);
            redisOperator.hset(Constant.Redis.TEACHER_MESSAGE, userNum, userEntity);
        }
        return userEntity;
    }

    @Override
    public JSONResult findApplyDetail(String lvId) {
        if (StringUtils.isEmpty(lvId)) {
            return JSONResult.build(402, LvException.ErrorMsg.REQUEST_PARAM_ERROR, null);
        }
        LvLeaveEntity lvLeaveEntity = lvLeaveDao.selectOne(new QueryWrapper<LvLeaveEntity>().eq("lv_id", lvId));
        if (lvLeaveEntity == null) {
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        LvUserEntity userEntity = lvUserDao.selectOne(new QueryWrapper<LvUserEntity>().eq("lv_user_num", lvLeaveEntity.getLvUserNum()));
        if (userEntity == null) {
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        return new JSONResult(new ApplyItemResponse(
                userEntity.getLvUserNum(), userEntity.getLvUserName(), userEntity.getLvUserPhone(),
                lvLeaveEntity.getLvRelativeName(), lvLeaveEntity.getLvRelativePhone(), lvLeaveEntity.getLvBeginTime(),
                lvLeaveEntity.getLvEndTime(), lvLeaveEntity.getLvReason(), lvLeaveEntity.getLvLength()
        ));
    }

    @Override
    public JSONResult findCompleteApplyItem(String teacherNum) {
        if (StringUtils.isEmpty(teacherNum)) {
            return JSONResult.build(402, LvException.ErrorMsg.REQUEST_PARAM_ERROR, null);
        }
        List<LvUserEntity> lvUserEntities =
                lvUserDao.selectList(new QueryWrapper<LvUserEntity>().eq("lv_teacher_num", teacherNum));
        if (CollectionUtils.isEmpty(lvUserEntities)) {
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        List<TeacherHisItems> list = new ArrayList<>();
        int leaveNum = 0;

        // 查出未审核的
        List<Task> tasks = teacherCheckService.checkTaskByTeacherNum(teacherNum);
        for (Task task :
                tasks) {
            LvLeaveEntity leave = teacherCheckService.findTask(task.getProcessInstanceId());
            if (leave != null) {
                LvUserEntity user = lvUserDao.findUseMsgByUserNum(leave.getLvUserNum());
                leaveNum =
                        lvLeaveDao.selectCount(new QueryWrapper<LvLeaveEntity>().eq("lv_user_num", leave.getLvUserNum()));
                try {
                    list.add(new TeacherHisItems(
                            user.getLvUserName(), user.getLvUserNum(),
                            new TimeUtil().StringFourTime(leave.getLvBeginTime()),
                            new TimeUtil().StringFourTime(leave.getLvEndTime()),
                            leaveNum, leave.getLvId(), leave.getLvStatus()
                    ));
                } catch (ParseException e) {
                    return JSONResult.build(500, LvException.ErrorMsg.DATE_ERROR, null);
                }
            }
        }

        // 查出审核过的
        List<LvCheckEntity> lvCheckEntities = lvCheckDao.selectList(new QueryWrapper<LvCheckEntity>()
                .eq("lv_teacher_num", teacherNum)
                .orderByDesc("lv_check_id"));
        for (LvCheckEntity lc :
                lvCheckEntities) {
            LvLeaveEntity leave =
                    lvLeaveDao.selectOne(new QueryWrapper<LvLeaveEntity>().eq("lv_id", lc.getLvId()));
            LvUserEntity user =
                    lvUserDao.selectOne(new QueryWrapper<LvUserEntity>().eq("lv_user_num", leave.getLvUserNum()));
            leaveNum =
                    lvLeaveDao.selectCount(new QueryWrapper<LvLeaveEntity>().eq("lv_user_num", leave.getLvUserNum()));
            try {
                list.add(new TeacherHisItems(
                        user.getLvUserName(), user.getLvUserNum(),
                        new TimeUtil().StringFourTime(leave.getLvBeginTime()),
                        new TimeUtil().StringFourTime(leave.getLvEndTime()),
                        leaveNum, leave.getLvId(), leave.getLvStatus()
                ));
            } catch (ParseException e) {
                return JSONResult.build(500, LvException.ErrorMsg.DATE_ERROR, null);
            }
        }
        return JSONResult.ok(list);
    }
}