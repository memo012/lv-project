package com.lv.adminsys.modules.shiro;

import com.lv.adminsys.common.utils.JWTUtil;
import com.lv.adminsys.modules.entity.LvPermissionEntity;
import com.lv.adminsys.modules.entity.LvRolesEntity;
import com.lv.adminsys.modules.entity.LvUserEntity;
import com.lv.adminsys.modules.service.IUserService;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author: qiang
 * @ClassName: AutoRealm
 * @Description: TODO
 * @Date: 2019/11/20 下午4:42
 * @Version: 1.0
 **/
public class AuthRealm extends AuthorizingRealm {

    @Resource
    private IUserService iUserService;


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
        LvUserEntity user = iUserService.findUserMsgByUserNum(username);
        List<String> list = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
        Set<LvRolesEntity> roles = user.getRoles();
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
        String token = (String) authenticationToken.getCredentials();
//        if(StringUtils.isEmpty(token)){
//            throw new AuthenticationException("token认证失败");
//        }
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token认证失败");
        }
        LvUserEntity byUsername = iUserService.findUserMsgByUserNum(username);
        if (byUsername == null) {
            throw new AuthenticationException("用户名不存在");
        }
        if (!JWTUtil.verify(token, username, byUsername.getLvUserPassword())) {
            throw new AuthenticationException("用户名或密码错误");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(token, token, getName());
        return info;
    }
}
