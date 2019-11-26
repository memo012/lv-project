package com.lv.adminsys.common.constant;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lv.adminsys.common.utils.TimeUtil;
import com.lv.adminsys.modules.dao.LvTeacherDao;
import com.lv.adminsys.modules.entity.LvLeaveEntity;
import com.lv.adminsys.modules.entity.LvTeacherEntity;
import com.lv.adminsys.modules.vo.leave.UserProcessResponse;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Comment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: qiang
 * @ClassName: judgeLeaveLeader
 * @Description: TODO
 * @Date: 2019/11/17 下午1:48
 * @Version: 1.0
 **/
@Service
public class JudgeLeaveLeader {

    @Resource
    private HistoryService historyService;

    @Resource
    private LvTeacherDao lvTeacherDao;

    @Resource
    private TaskService taskService;


    public List<UserProcessResponse> queryHistoryProcess(LvLeaveEntity leaveEntity){
        List<UserProcessResponse> lists = new LinkedList<>();
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(leaveEntity.getLvProcessInstanceId())
                .orderByHistoricTaskInstanceStartTime().asc()
                .list();
        for (HistoricTaskInstance his:
                list) {
            LvTeacherEntity teacherEntity = lvTeacherDao.selectOne(new QueryWrapper<LvTeacherEntity>().eq("lv_teacher_num", his.getAssignee()));
            List<Comment> taskComments = taskService.getTaskComments(his.getId());
            if(taskComments.size() <= 0){
                return lists;
            }
            for (Comment c:
                    taskComments) {
                String status = "pass";
                if (c.getFullMessage().contains("拒绝")) {
                    status = "reject";
                }
                lists.add(new UserProcessResponse(
                        teacherEntity.getLvTeacherName(), new TimeUtil().getParseDateForSix(his.getEndTime()),
                        teacherEntity.getLvTeacherLocation(), teacherEntity.getLvTeacherWorkTime(),
                        c.getFullMessage(), status
                ));
            }
        }
        return lists;
    }

}
