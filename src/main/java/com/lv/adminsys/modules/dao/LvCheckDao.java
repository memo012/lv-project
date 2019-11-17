package com.lv.adminsys.modules.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lv.adminsys.modules.entity.LvCheckEntity;
import org.springframework.stereotype.Repository;

/**
 * @Author: qiang
 * @ClassName: LvCheckDao
 * @Description: 申请审核数据层
 * @Date: 2019/11/15 下午1:26
 * @Version: 1.0
 **/
@Repository
public interface LvCheckDao extends BaseMapper<LvCheckEntity> {
}
