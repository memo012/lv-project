package com.lv.adminsys.modules;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ModulesApplicationTests {
//    @Test
//    public void testAnnotation() {
//    }

//
//
//
//    /**
//     * 测试注解使用
//     */
//    @Test
//    public void testAnnotation() {
//        String processInstanceId = "130009";
//        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
//                .processInstanceId(processInstanceId)
//                .orderByHistoricTaskInstanceStartTime().asc()
//                .list();
//        System.out.println("                   "+list.size());
//        for (HistoricTaskInstance his:
//                list) {
//            List<Comment> taskComments = taskService.getTaskComments(his.getId());
//            System.out.println(taskComments.size());
//            for (Comment c:
//                 taskComments) {
//                System.out.println("assignee:" + his.getAssignee());
//                System.out.println("startTime:" + his.getStartTime());
//                System.out.println("endTime:" + his.getEndTime());
//                System.out.println("name:" + his.getName());
//                System.out.println("duration:" + his.getDurationInMillis());
//                System.out.println(new TimeUtil().getParseDateForSix(his.getEndTime()));
//                System.out.println("审批意见："+c.getFullMessage());
//            }
//            System.out.println("------------------------------------------------------");
//        }
//    }


//}
