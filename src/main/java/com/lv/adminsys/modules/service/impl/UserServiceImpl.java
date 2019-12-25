package com.lv.adminsys.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lv.adminsys.common.constant.Constant;
import com.lv.adminsys.common.constant.LvException;
import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.common.utils.JWTUtil;
import com.lv.adminsys.common.utils.RedisOperator;
import com.lv.adminsys.common.utils.TimeUtil;
import com.lv.adminsys.modules.dao.LvTeacherDao;
import com.lv.adminsys.modules.dao.LvUserDao;
import com.lv.adminsys.modules.entity.LvTeacherEntity;
import com.lv.adminsys.modules.entity.LvUserEntity;
import com.lv.adminsys.modules.service.IUserService;
import com.lv.adminsys.modules.shiro.ShiroEncrypt;
import com.lv.adminsys.modules.vo.login.UserLoginRequest;
import com.lv.adminsys.modules.vo.login.UserLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: qiang
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @Date: 2019/11/16 下午7:12
 * @Version: 1.0
 **/
@Service
@Transactional
public class UserServiceImpl implements IUserService {
    /**
     *  老师工号前缀
     */
    @Value("${teacher.start}")
    private String teacherNumStart;

    @Resource
    private LvUserDao lvUserDao;

    @Resource
    private LvTeacherDao lvTeacherDao;

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private HttpServletRequest request;

    @Override
    public int deleteByUserName(String userName) {
        String token = (String) request.getAttribute("claims_admin");
        if(token == null || "".equals(token)){
            throw new RuntimeException("权限不足!");
        }
        QueryWrapper queryWrapper = new QueryWrapper<LvUserEntity>()
                .eq("username", userName);
        lvUserDao.delete(queryWrapper);
        return 0;
    }


    @Override
    public boolean registerUser(LvUserEntity lvUserEntity) {
        lvUserEntity.setLvUserId(new TimeUtil().getLongTime());
        lvUserEntity.setLvUserCreateTime(new TimeUtil().getFormatDateForThree());
        lvUserEntity.setLvRole(1);
        lvUserDao.insert(lvUserEntity);
        return false;
    }

    @Override
    public LvUserEntity findUserMsgByUserNum(String userNum) {
        LvUserEntity userEntity = null;
        if(redisOperator.hasHkey(Constant.Redis.USER_MESSAGE, userNum)){
            userEntity = (LvUserEntity)redisOperator.hget(Constant.Redis.USER_MESSAGE, userNum);
        }else{
            userEntity = lvUserDao.findUseMsgByUserNum(userNum);
            redisOperator.hset(Constant.Redis.USER_MESSAGE, userNum, userEntity);
        }
        return userEntity;
    }

    @Override
    public JSONResult userLogin(UserLoginRequest loginRequest) {
        if(!loginRequest.createValidate()){
            return JSONResult.build(402, LvException.ErrorMsg.REQUEST_PARAM_ERROR, null);
        }
        String password = ShiroEncrypt.encrypt(loginRequest.getLvUserNum(), loginRequest.getLvUserPassword());
        if(loginRequest.getLvUserNum().startsWith(teacherNumStart)){
            LvTeacherEntity userEntity = lvTeacherDao.findUseMsgByTeacherNum(loginRequest.getLvUserNum());
            if(userEntity == null){
                return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
            }
            if(!password.equals(userEntity.getLvTeacherPassword())){
                return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
            }
            return JSONResult.ok(
                    new UserLoginResponse(
                            userEntity.getLvTeacherNum(), userEntity.getLvTeacherName(),
                            userEntity.getLvTeacherPhone(), JWTUtil.sign(userEntity.getLvTeacherNum(), userEntity.getLvTeacherPassword()),
                            userEntity.getLvRole(), userEntity.getLvCollage()
                    )
            );
        }else{
            LvUserEntity userEntity = lvUserDao.findUseMsgByUserNum(loginRequest.getLvUserNum());
            if(userEntity == null){
                return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
            }
            if(!password.equals(userEntity.getLvUserPassword())){
                return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
            }
            return JSONResult.ok(
                    new UserLoginResponse(
                            userEntity.getLvUserNum(), userEntity.getLvUserName(),
                            userEntity.getLvUserPhone(), JWTUtil.sign(userEntity.getLvUserNum(), userEntity.getLvUserPassword()),
                            userEntity.getLvRole(), userEntity.getLvCollage()
                    )
            );
        }
    }

    @Override
    public LvUserEntity userIsExit(UserLoginRequest request) {
        LvUserEntity userEntity = lvUserDao.selectOne(new QueryWrapper<LvUserEntity>().eq("lv_user_num", request.getLvUserNum()));
        return userEntity;
    }

}
