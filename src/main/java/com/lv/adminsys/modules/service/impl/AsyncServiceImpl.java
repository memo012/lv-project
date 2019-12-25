package com.lv.adminsys.modules.service.impl;

import com.lv.adminsys.common.utils.TimeUtil;
import com.lv.adminsys.modules.dao.LvCheckDao;
import com.lv.adminsys.modules.entity.LvCheckEntity;
import com.lv.adminsys.modules.service.AsyncService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.service.impl
 * @Description: 异步任务(数据库和redis保持一致)
 * @Date: 2019/8/9 0009 15:24
 **/
@Service
public class AsyncServiceImpl implements AsyncService {

    @Autowired
    private LvCheckDao lvCheckDao;

    @Override
    public void addCheckMess(String teacherNum, String lvId) {
        lvCheckDao.insert(new LvCheckEntity(
                new TimeUtil().getLongTime(), teacherNum, lvId, 0
        ));
    }

    @Override
    public void addCheckMessage(String account, List<Task> taskList) {


    }


}
