package com.lv.adminsys.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lv.adminsys.common.constant.JudgeLeaveLeader;
import com.lv.adminsys.common.constant.LvException;
import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.common.utils.TimeUtil;
import com.lv.adminsys.modules.act.LeaveService;
import com.lv.adminsys.modules.dao.LvLeaveDao;
import com.lv.adminsys.modules.dao.LvTeacherDao;
import com.lv.adminsys.modules.dao.LvUserDao;
import com.lv.adminsys.modules.entity.LvLeaveEntity;
import com.lv.adminsys.modules.entity.LvTeacherEntity;
import com.lv.adminsys.modules.entity.LvUserEntity;
import com.lv.adminsys.modules.service.IUserLeaveService;
import com.lv.adminsys.modules.vo.leave.ApplyDetailResponse;
import com.lv.adminsys.modules.vo.leave.LeaveDetailResponse;
import com.lv.adminsys.modules.vo.leave.LeaveItemResponse;
import com.lv.adminsys.modules.vo.leave.UserProcessResponse;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: qiang
 * @ClassName: UserLeaveImpl
 * @Description: 学生请假相关操作业务逻辑层
 * @Date: 2019/11/9 下午2:41
 * @Version: 1.0
 **/
@Service
@Transactional
public class UserLeaveServiceImpl implements IUserLeaveService {


    @Autowired
    private LvLeaveDao lvLeaveDao;

    @Autowired
    private LvUserDao lvUserDao;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private LvTeacherDao lvTeacherDao;

    @Autowired
    private JudgeLeaveLeader judgeLeaveLeader;


    @Override
    public boolean queryIsExist(String userNum) {
        LvUserEntity userEntity = lvUserDao.selectOne(new QueryWrapper<LvUserEntity>().eq("lv_user_num", userNum));
        if (userEntity == null) {
            return false;
        }
        return true;
    }

    @Override
    public JSONResult applyLeave(LvLeaveEntity lvLeaveEntity) {
        if (!lvLeaveEntity.createValidate()) {
            return JSONResult.build(402, LvException.ErrorMsg.REQUEST_PARAM_ERROR, null);
        }
        boolean b = queryIsExist(lvLeaveEntity.getLvUserNum());
        if (!b) {
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        String id = new TimeUtil().getLongTime();
        lvLeaveEntity.setLvId(id);
        try {
            lvLeaveEntity.setLvEndTimeLong(new TimeUtil().StringToLongTime(lvLeaveEntity.getLvEndTime()));
            lvLeaveEntity.setLvCreateTime(new TimeUtil().getFormatDateForThree());
        } catch (ParseException e) {
            throw new RuntimeException(LvException.ErrorMsg.DATE_ERROR);
        }
        lvLeaveDao.insert(lvLeaveEntity);
        ProcessInstance processInstance;
        // 启动流程时 要和业务进行关联
        if (!lvLeaveEntity.getLvLength().equals("一天以内")) {
            processInstance = leaveService.startProcess(id);
        } else {
            processInstance = leaveService.startSmallProcess(id);
        }
        if (processInstance != null) {
            return JSONResult.ok();
        }
        return JSONResult.errorMsg("新增失败");
    }

    @Override
    public JSONResult queryUserIsPass(String lvId) {
        LvLeaveEntity result = lvLeaveDao.selectOne(new QueryWrapper<LvLeaveEntity>().eq("lv_id", lvId));
        if (result == null) {
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        return JSONResult.ok(result);
    }

    @Override
    public JSONResult queryApplyListByUserNum(String uerNum) {
        if(StringUtils.isEmpty(uerNum)){
            return JSONResult.build(402, LvException.ErrorMsg.REQUEST_PARAM_ERROR, null);
        }
        List<LvLeaveEntity> lvLeaveEntities = lvLeaveDao.selectList(
                new QueryWrapper<LvLeaveEntity>()
                        .eq("lv_status", "pass")
                        .eq("lv_user_num", uerNum)
        );
        if(lvLeaveEntities == null){
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        List<LeaveItemResponse> responses = new LinkedList<>();
        for (LvLeaveEntity list :
                lvLeaveEntities) {
            try {
                responses.add(
                        new LeaveItemResponse(
                                new TimeUtil().StringFourTime(list.getLvBeginTime()),
                                new TimeUtil().StringFourTime(list.getLvEndTime()), list.getLvReason(),
                                list.getLvCreateTime(), list.getLvId()
                        )
                );
            } catch (ParseException e) {
                return JSONResult.build(500, LvException.ErrorMsg.DATE_ERROR, null);
            }
        }
        return JSONResult.ok(responses);
    }

    @Override
    public JSONResult queryApplyDetailByUserNum(String lvId) {
        if(StringUtils.isEmpty(lvId)){
            return JSONResult.build(402, LvException.ErrorMsg.REQUEST_PARAM_ERROR, null);
        }
        LvLeaveEntity applyDetail = lvLeaveDao.selectOne(
                new QueryWrapper<LvLeaveEntity>()
                        .eq("lv_id", lvId)
        );
        if(applyDetail == null){
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        try {
            List<String> strings = judgeLeaveLeader.queryLeaderCompleteMess(applyDetail.getLvProcessInstanceId());
            return JSONResult.ok(new ApplyDetailResponse(
                    applyDetail.getLvReason(), new TimeUtil().StringFourTime(applyDetail.getLvBeginTime()),
                    new TimeUtil().StringFourTime(applyDetail.getLvEndTime()), applyDetail.getLvRelativePhone(),
                    applyDetail.getLvLength(), strings
            ));
        } catch (ParseException e) {
            return JSONResult.build(500, LvException.ErrorMsg.DATE_ERROR, null);
        }
    }

    @Override
    public JSONResult queryUserApplyList(String userNum, String status) {
        if (StringUtils.isBlank(userNum) || StringUtils.isBlank(status)) {
            return JSONResult.build(402, LvException.ErrorMsg.REQUEST_PARAM_ERROR, null);
        }
        boolean b = queryIsExist(userNum);
        if (!b) {
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        QueryWrapper queryWrapper;
        if (status.equals("now")) {
            String nowLong = new TimeUtil().getLongTime();
            queryWrapper = new QueryWrapper<LvLeaveEntity>()
                    .eq("lv_user_num", userNum)
                    .ge("lv_end_time_long", nowLong)
                    .orderByDesc("lv_id");
        } else {
            queryWrapper = new QueryWrapper<LvLeaveEntity>()
                    .eq("lv_user_num", userNum)
                    .orderByDesc("lv_id");
        }
        List<LvLeaveEntity> lists = lvLeaveDao.selectList(queryWrapper);
        if (lists == null) {
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        List<LeaveDetailResponse> responses = new LinkedList<>();
        for (LvLeaveEntity list :
                lists) {
            try {
                responses.add(
                        new LeaveDetailResponse(
                                list.getLvId(), new TimeUtil().StringFourTime(list.getLvBeginTime()),
                                new TimeUtil().StringFourTime(list.getLvEndTime()), list.getLvReason(),
                                list.getLvStatus()
                        )
                );
            } catch (ParseException e) {
                return JSONResult.build(500, LvException.ErrorMsg.DATE_ERROR, null);
            }
        }
        return JSONResult.build(200, "成功", responses);
    }

    @Override
    public JSONResult queryUserProcess(String lvId, String state) {
        if (StringUtils.isBlank(lvId) || StringUtils.isBlank(state)) {
            return JSONResult.build(402, LvException.ErrorMsg.REQUEST_PARAM_ERROR, null);
        }
        LvLeaveEntity leaveEntity = lvLeaveDao.selectOne(new QueryWrapper<LvLeaveEntity>().eq("lv_id", lvId));
        if (leaveEntity == null) {
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }

        // 查看历史请假详情
        if (state.equals("history")) {
            List<LvLeaveEntity> historyList = lvLeaveDao.selectList(new QueryWrapper<LvLeaveEntity>().eq("lv_id", lvId));
            if (historyList == null) {
                return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
            }
            return JSONResult.ok(historyList);
        }

        List<UserProcessResponse> lists;

        // 3. 判断学生是否处于审核中
        if (!leaveEntity.getLvStatus().equals("ing")) {
            lists = judgeLeaveLeader.queryHistoryProcess(leaveEntity);
            return JSONResult.ok(lists);
        }
        lists = judgeLeaveLeader.queryHistoryProcess(leaveEntity);

        // 班主任未审核
        if (lists.size() == 0) {
            LvUserEntity userEntity = lvUserDao.selectOne(new QueryWrapper<LvUserEntity>().eq("lv_user_num", leaveEntity.getLvUserNum()));
            LvTeacherEntity lv_teacher_num = lvTeacherDao.selectOne(new QueryWrapper<LvTeacherEntity>().eq("lv_teacher_num", userEntity.getLvTeacherNum()));
            lists.add(new UserProcessResponse(
                    lv_teacher_num.getLvTeacherName(), "",
                    lv_teacher_num.getLvTeacherLocation(), lv_teacher_num.getLvTeacherWorkTime(),
                    null, ""
            ));
        }

        // 请假少于一天,只需班主任审批即可
        if (leaveEntity.getLvLength().equals("一天以内")) {
            return JSONResult.ok(lists);
        }

        // 0 - 教学期间    1 - 非教学期间
        int status = 0;
        if (new TimeUtil().dateToWeek(leaveEntity.getLvBeginTime()).contains("六") ||
                new TimeUtil().dateToWeek(leaveEntity.getLvBeginTime()).contains("日")) {
            lists.add(aduitLeaderMessage(status + 1));
        } else {
            lists.add(aduitLeaderMessage(status));
        }
        return JSONResult.ok(lists);
    }


    public UserProcessResponse aduitLeaderMessage(int status) {
        String leaderName = "";
        String leaderWorkTime = "";
        String leaderLocation = "";
        List<LvTeacherEntity> lv_teacher_role;
        if (status == 0) {
            lv_teacher_role = lvTeacherDao.selectList(new QueryWrapper<LvTeacherEntity>().eq("lv_role", 4));
        } else {
            lv_teacher_role = lvTeacherDao.selectList(new QueryWrapper<LvTeacherEntity>().eq("lv_role", 3));
        }
        for (LvTeacherEntity lv :
                lv_teacher_role) {
            leaderName += lv.getLvTeacherName() + "/";
            leaderWorkTime += lv.getLvTeacherWorkTime() + "/";
            leaderLocation += lv.getLvTeacherLocation() + "/";
        }
        leaderName = leaderName.substring(0, leaderName.lastIndexOf("/"));
        leaderWorkTime = leaderWorkTime.substring(0, leaderWorkTime.lastIndexOf("/"));
        leaderLocation = leaderLocation.substring(0, leaderLocation.lastIndexOf("/"));
        return new UserProcessResponse(
                leaderName, null,
                leaderLocation, leaderWorkTime,
                null, ""
        );
    }
}