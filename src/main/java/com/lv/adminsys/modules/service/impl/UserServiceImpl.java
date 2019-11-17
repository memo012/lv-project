package com.lv.adminsys.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lv.adminsys.common.utils.TimeUtil;
import com.lv.adminsys.common.security.JwtUtil;
import com.lv.adminsys.modules.dao.LvRoleDao;
import com.lv.adminsys.modules.dao.LvUserDao;
import com.lv.adminsys.modules.entity.LvRolesEntity;
import com.lv.adminsys.modules.entity.LvUserEntity;
import com.lv.adminsys.modules.service.IUserService;
import com.lv.adminsys.modules.vo.login.UserLoginRequest;
import com.lv.adminsys.modules.vo.login.UserLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Resource
    private LvUserDao lvUserDao;

    @Autowired
    private BCryptPasswordEncoder encoder;

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

    @Resource
    private JwtUtil jwtUtil;

    @Autowired
    private LvRoleDao lvRoleDao;

    @Override
    public boolean registerUser(LvUserEntity lvUserEntity) {
        lvUserEntity.setLvUserId(new TimeUtil().getLongTime());
        lvUserEntity.setLvUserCreateTime(new TimeUtil().getFormatDateForThree());
        lvUserEntity.setLvRole(1);
        lvUserEntity.setLvUserPassword(encoder.encode(lvUserEntity.getLvUserPassword()));
        lvUserDao.insert(lvUserEntity);
        return false;
    }

    @Override
    public UserLoginResponse userLogin(UserLoginRequest loginRequest) {
        LvUserEntity result = lvUserDao.selectOne(new QueryWrapper<LvUserEntity>().eq("lv_user_num", loginRequest.getLvUserNum()));
        if(result != null && encoder.matches(loginRequest.getLvUserPassword(), result.getLvUserPassword())){
            LvRolesEntity lv_role = lvRoleDao.selectOne(new QueryWrapper<LvRolesEntity>().eq("rid", result.getLvRole()));
            String token = jwtUtil.createJwt(result.getLvUserId(), result.getLvUserName(), lv_role.getLvRname());
            return new UserLoginResponse(
                    result.getLvUserNum(), result.getLvUserName(),
                    result.getLvUserPhone(), token,
                    lv_role.getLvRname()
            );
        }
        return null;
    }

    @Override
    public LvUserEntity userIsExit(UserLoginRequest request) {
        LvUserEntity userEntity = lvUserDao.selectOne(new QueryWrapper<LvUserEntity>().eq("lv_user_num", request.getLvUserNum()));
        return userEntity;
    }

}
