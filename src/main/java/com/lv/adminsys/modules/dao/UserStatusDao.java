package com.lv.adminsys.modules.dao;

import com.lv.adminsys.modules.entity.VO.UserStatusEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author: qiang
 * @ClassName: UserStatusDao
 * @Description: 查看请假状态数据层
 * @Date: 2019/11/9 下午4:03
 * @Version: 1.0
 **/
@Repository
public interface UserStatusDao {

    @Select("select * from lv_leave where lv_user_num = #{arg0}")
    UserStatusEntity queryUserStatusIsPass(String lvUserName);

}
