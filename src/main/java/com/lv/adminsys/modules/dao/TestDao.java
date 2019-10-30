package com.lv.adminsys.modules.dao;

import com.lv.adminsys.modules.entity.LvTeacherEntity;
import com.lv.adminsys.modules.entity.LvUserEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: qiang
 * @ClassName: TestDao
 * @Description: TODO
 * @Date: 2019/10/30 下午4:52
 * @Version: 1.0
 **/
@Repository
public interface TestDao {

    /**
     * 测试注解使用
     * @return
     */
    @Select("select * from lv_user")
    List<LvUserEntity> testAnnotation();



    /**
     * 测试xml使用
     * @return
     */
    List<LvTeacherEntity> testXml();

    int insUser(LvUserEntity userEntity);

}
