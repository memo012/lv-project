package com.lv.adminsys.modules.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lv.adminsys.modules.entity.LvTeacherEntity;
import com.lv.adminsys.modules.entity.LvUserEntity;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @Author: qiang
 * @ClassName: LvTeacherDao
 * @Description: 老师表操作数据层
 * @Date: 2019/11/16 下午2:38
 * @Version: 1.0
 **/
@Repository
public interface LvTeacherDao extends BaseMapper<LvTeacherEntity> {

    /**
     *  通过学号查询
     * @param teacherNum 学号
     * @return
     */
    LvTeacherEntity findUseMsgByTeacherNum(String teacherNum);


}
