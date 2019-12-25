package com.lv.adminsys.modules;

import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.common.utils.TimeUtil;
import com.lv.adminsys.modules.service.ITeacherCheckService;
import com.lv.adminsys.modules.service.impl.TeacherCheckServiceImpl;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModulesApplicationTests {

//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//

    @Resource
    private HistoryService historyService;

    @Resource
    private TaskService taskService;

//
//
//
//    /**
//     * 测试注解使用
//     */
    @Test
    public void testAnnotation() {
        String processInstanceId = "130009";
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricTaskInstanceStartTime().asc()
                .list();
        System.out.println("                   "+list.size());
        for (HistoricTaskInstance his:
                list) {
            List<Comment> taskComments = taskService.getTaskComments(his.getId());
            System.out.println(taskComments.size());
            for (Comment c:
                 taskComments) {
                System.out.println("assignee:" + his.getAssignee());
                System.out.println("startTime:" + his.getStartTime());
                System.out.println("endTime:" + his.getEndTime());
                System.out.println("name:" + his.getName());
                System.out.println("duration:" + his.getDurationInMillis());
                System.out.println(new TimeUtil().getParseDateForSix(his.getEndTime()));
                System.out.println("审批意见："+c.getFullMessage());
            }
            System.out.println("------------------------------------------------------");
        }
    }
//
//    @Test
//    public void test(){
//        List<LvTeacherEntity> lv_teacher_role = lvTeacherDao.selectList(new QueryWrapper<LvTeacherEntity>().eq("lv_teacher_role", 1));
//        lv_teacher_role.forEach(System.out::println);
//    }
//
//    /**
//     * 测试注解使用
//     */
//    @Test
//    public void testMessage() {
//        LvUserEntity useMsgByUserNum = lvUserDao.findUseMsgByUserNum("1713011332");
//        System.out.println(useMsgByUserNum.toString());
//    }
//
//
//    @Test
//    public void test123(){
//        String uerNum = "1713011332";
//        List<LvLeaveEntity> lvLeaveEntities = lvLeaveDao.selectList(
//                new QueryWrapper<LvLeaveEntity>()
//                        .eq("lv_status", "pass")
//                        .eq("lv_user_num", uerNum)
//        );
//
//    }
//
//    @Test
//    public void test3(){
//        List<String> strings = judgeLeaveLeader.queryLeaderCompleteMess("130001");
//        strings.forEach(System.out::println);
//    }
//

    @Test
    public void test4(){

    }


}
