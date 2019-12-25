package com.lv.adminsys.modules.service;

import org.activiti.engine.task.Task;

import java.util.List;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.service
 * @Description: 异步任务(数据库和redis保持一致)
 * @Date: 2019/8/9 0009 15:21
 **/
public interface AsyncService {

    /**
     *  审核人审核的任务
     * @param teacherNum 审核人工号
     * @param lvId 请假id
     */
    public void addCheckMess(String teacherNum, String lvId);

    /**
     *  添加标记信息
     * @param account
     * @param taskList
     */
    public void addCheckMessage(String account, List<Task> taskList);


}
