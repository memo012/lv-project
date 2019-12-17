package com.lv.adminsys.modules.service;

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

}
