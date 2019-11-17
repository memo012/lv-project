package com.lv.adminsys.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.common.utils.TimeUtil;
import com.lv.adminsys.modules.act.LeaveService;
import com.lv.adminsys.common.constant.JudgeLeaveLeader;
import com.lv.adminsys.common.constant.LvException;
import com.lv.adminsys.modules.dao.LvLeaveDao;
import com.lv.adminsys.modules.dao.LvTeacherDao;
import com.lv.adminsys.modules.dao.LvUserDao;
import com.lv.adminsys.modules.entity.LvLeaveEntity;
import com.lv.adminsys.modules.entity.LvTeacherEntity;
import com.lv.adminsys.modules.entity.LvUserEntity;
import com.lv.adminsys.modules.service.IUserLeaveService;
import com.lv.adminsys.modules.vo.leave.UserProcessResponse;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Resource
    private LvTeacherDao lvTeacherDao;

    @Resource
    private JudgeLeaveLeader judgeLeaveLeader;


    @Override
    public boolean queryIsExist(String userNum) {
        LvUserEntity userEntity = lvUserDao.selectOne(new QueryWrapper<LvUserEntity>().eq("lv_user_num", userNum));
        if(userEntity == null){
            return false;
        }
        return true;
    }

    @Override
    public boolean applyLeave(LvLeaveEntity lvLeaveEntity) {
        String id = new TimeUtil().getLongTime();
        lvLeaveEntity.setLvId(id);
        lvLeaveDao.insert(lvLeaveEntity);
        ProcessInstance processInstance = null;
        // 启动流程时 要和业务进行关联
        if(lvLeaveEntity.getLvDay() > 1){
            processInstance = leaveService.startProcess(id);
        }
        else{
            processInstance = leaveService.startSmallProcess(id);
        }
        return processInstance != null;
    }

    @Override
    public LvLeaveEntity queryUserIsPass(String lvId) {
        LvLeaveEntity result = lvLeaveDao.selectOne(new QueryWrapper<LvLeaveEntity>().eq("lv_id", lvId));
        if(result == null){
            return null;
        }
        return result;
    }

    @Override
    public List<LvLeaveEntity> queryUserApplyList(String userNum) {
        QueryWrapper queryWrapper = new QueryWrapper<LvLeaveEntity>()
                .eq("lv_user_num", userNum)
                .orderByDesc("lv_id");
        List<LvLeaveEntity> list = lvLeaveDao.selectList(queryWrapper);
        return list;
    }

    @Override
    public JSONResult queryUserProcess(String lvId) {
        LvLeaveEntity leaveEntity = lvLeaveDao.selectOne(new QueryWrapper<LvLeaveEntity>().eq("lv_id", lvId));
        if(leaveEntity == null){
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        List<UserProcessResponse> lists = new LinkedList<>();

        // 3. 判断学生是否处于审核中
        if(!leaveEntity.getLvStatus().equals("ing")){
            lists = judgeLeaveLeader.queryHistoryProcess(leaveEntity);
            return JSONResult.ok(lists);
        }
        lists = judgeLeaveLeader.queryHistoryProcess(leaveEntity);

        // 班主任未审核
        if(lists.size() == 0){
            LvUserEntity userEntity = lvUserDao.selectOne(new QueryWrapper<LvUserEntity>().eq("lv_user_num", leaveEntity.getLvUserNum()));
            LvTeacherEntity lv_teacher_num = lvTeacherDao.selectOne(new QueryWrapper<LvTeacherEntity>().eq("lv_teacher_num", userEntity.getLvTeacherNum()));
            lists.add(new UserProcessResponse(
                    lv_teacher_num.getLvTeacherName(), "",
                    lv_teacher_num.getLvTeacherLocation(), lv_teacher_num.getLvTeacherWorkTime(),
                    null
            ));
        }

        // 请假少于一天,只需班主任审批即可
        if(leaveEntity.getLvDay() < 1){
            return JSONResult.ok(lists);
        }

        int status = 0; // 0 - 教学期间    1 - 非教学期间

        if (new TimeUtil().dateToWeek(leaveEntity.getLvBeginTime()).contains("六") ||
                new TimeUtil().dateToWeek(leaveEntity.getLvBeginTime()).contains("日")) {
            lists.add(aduitLeaderMessage(status+1));
        }else{
            lists.add(aduitLeaderMessage(status));
        }
        return JSONResult.ok(lists);
    }




    public UserProcessResponse aduitLeaderMessage(int status){
        String leaderName = "";
        String leaderWorkTime = "";
        String leaderLocation = "";
        List<LvTeacherEntity> lv_teacher_role = new ArrayList<>();
        if(status == 0){
            lv_teacher_role = lvTeacherDao.selectList(new QueryWrapper<LvTeacherEntity>().eq("lv_role", 4));
        }else{
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
                null
        );
    }
}
