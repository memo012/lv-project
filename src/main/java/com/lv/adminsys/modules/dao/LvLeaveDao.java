package com.lv.adminsys.modules.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lv.adminsys.modules.entity.LvLeaveEntity;
import org.springframework.stereotype.Repository;

/**
 * @Author: qiang
 * @ClassName: LvLeaveDao
 * @Description: 学生申请请假数据层
 * @Date: 2019/11/9 下午2:39
 * @Version: 1.0
 **/
@Repository
public interface LvLeaveDao extends BaseMapper<LvLeaveEntity> {
}
