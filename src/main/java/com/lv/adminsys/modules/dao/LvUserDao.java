package com.lv.adminsys.modules.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lv.adminsys.modules.entity.LvUserEntity;
import org.springframework.stereotype.Repository;

/**
 * @Author: qiang
 * @ClassName: LvUserDao
 * @Description: 学生信息表
 * @Date: 2019/11/9 下午3:53
 * @Version: 1.0
 **/
@Repository
public interface LvUserDao extends BaseMapper<LvUserEntity> {

    /**
     *  通过学号查询
     * @param userNum 学号
     * @return
     */
    LvUserEntity findUseMsgByUserNum(String userNum);

}
