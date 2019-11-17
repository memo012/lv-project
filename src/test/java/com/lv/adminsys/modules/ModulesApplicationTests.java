package com.lv.adminsys.modules;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lv.adminsys.common.utils.TimeUtil;
import com.lv.adminsys.modules.dao.LvTeacherDao;
import com.lv.adminsys.modules.dao.TestDao;
import com.lv.adminsys.modules.entity.LvTeacherEntity;
import com.lv.adminsys.modules.entity.LvUserEntity;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModulesApplicationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestDao testDao;

    @Resource
    private HistoryService historyService;

    @Autowired
    private LvTeacherDao lvTeacherDao;

    @Resource
    private TaskService taskService;

    /**
     * 测试注解使用
     */
    @Test
    public void testAnnotation() {
        String processInstanceId = "82501";
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
                System.out.println("审批意见"+c.getFullMessage());
            }

            System.out.println("------------------------------------------------------");


        }
    }

    @Test
    public void test(){
        List<LvTeacherEntity> lv_teacher_role = lvTeacherDao.selectList(new QueryWrapper<LvTeacherEntity>().eq("lv_teacher_role", 1));
        lv_teacher_role.forEach(System.out::println);
    }

}
