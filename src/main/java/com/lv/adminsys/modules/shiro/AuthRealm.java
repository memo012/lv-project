package com.lv.adminsys.modules.shiro;

import com.lv.adminsys.common.utils.JWTUtil;
import com.lv.adminsys.modules.entity.LvPermissionEntity;
import com.lv.adminsys.modules.entity.LvRolesEntity;
import com.lv.adminsys.modules.entity.LvTeacherEntity;
import com.lv.adminsys.modules.entity.LvUserEntity;
import com.lv.adminsys.modules.service.ITeacherService;
import com.lv.adminsys.modules.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author qiang
 * @description TODO
 * @date 2019/11/20 下午4:42
 * @version 1.0
 **/
public class AuthRealm extends AuthorizingRealm {

    /**
     *  老师工号前缀
     */
    @Value("${teacher.start}")
    private String teacherNumStart;

    @Resource
    private IUserService iUserService;

    @Resource
    private ITeacherService iTeacherService;


    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }


    /**
     * 权限校验
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("权限校验");
        String username = JWTUtil.getUsername(principalCollection.toString());
        LvUserEntity user;
        LvTeacherEntity teacher;
        Set<LvRolesEntity> roles;
        if(username.startsWith(teacherNumStart)){
            teacher = iTeacherService.findUserMsgByTeacherNum(username);
            roles = teacher.getRoles();
        }else{
            user = iUserService.findUserMsgByUserNum(username);
            roles = user.getRoles();
        }
        List<String> list = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            for (LvRolesEntity role :
                    roles) {
                roleNameList.add(role.getLvRname());
                Set<LvPermissionEntity> permissionSet = role.getPermissionSet();
                if (!CollectionUtils.isEmpty(permissionSet)) {
                    for (LvPermissionEntity permission :
                            permissionSet) {
                        list.add(permission.getLvUrl());
                    }
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roleNameList);
        info.addStringPermissions(list);
        return info;
    }


    /**
     * 登录校验
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("登录校验");
        LvUserEntity byUsername;
        LvTeacherEntity lvTeacherEntity;
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token认证失败");
        }
        if(username.startsWith(teacherNumStart)){
            lvTeacherEntity = iTeacherService.findUserMsgByTeacherNum(username);
            if (lvTeacherEntity == null) {
                throw new AuthenticationException("用户名不存在");
            }
            if (!JWTUtil.verify(token, username, lvTeacherEntity.getLvTeacherPassword())) {
                throw new AuthenticationException("用户名或密码错误");
            }
        }else {
            byUsername = iUserService.findUserMsgByUserNum(username);
            if (byUsername == null) {
                throw new AuthenticationException("用户名不存在");
            }
            if (!JWTUtil.verify(token, username, byUsername.getLvUserPassword())) {
                throw new AuthenticationException("用户名或密码错误");
            }
        }
        return new SimpleAuthenticationInfo(token, token, getName());
    }
}
