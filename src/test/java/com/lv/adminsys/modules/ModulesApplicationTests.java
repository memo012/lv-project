package com.lv.adminsys.modules;

import com.lv.adminsys.common.utils.TimeUtil;
import com.lv.adminsys.modules.dao.TestDao;
import com.lv.adminsys.modules.entity.LvUserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModulesApplicationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestDao testDao;

    /**
     * 测试注解使用
     */
    @Test
    public void testAnnotation() {
        LvUserEntity userEntity = new LvUserEntity();
        userEntity.setLvUserId(new TimeUtil().getLongTime());
        userEntity.setLvUserName("5555");
        userEntity.setLvUserNum("1223");
        userEntity.setLvUserPassword("123");
        userEntity.setLvUserPhone("4444");
        userEntity.setLvTeacherName("好");
        userEntity.setLvUserCreateTime(new TimeUtil().getParseDateForSix());
        testDao.insUser(userEntity);
        List<LvUserEntity> lvUserEntities = testDao.testAnnotation();
        lvUserEntities.forEach(System.out::println);
        logger.warn("测试成功");
        System.out.println("测试成功");
    }

}
