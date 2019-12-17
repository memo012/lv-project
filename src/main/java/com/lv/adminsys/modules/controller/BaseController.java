package com.lv.adminsys.modules.controller;

import com.lv.adminsys.common.constant.LvException;
import com.lv.adminsys.common.utils.JSONResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: qiang
 * @ClassName: BaseController
 * @Description: TODO
 * @Date: 2019/11/22 下午1:51
 * @Version: 1.0
 **/
@RestController
public class BaseController {

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public JSONResult unauthorized() {
        return JSONResult.errorTokenMsg(LvException.ErrorMsg.TOKEN_OVERTIME);
    }


    @GetMapping("/article")
    public JSONResult article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return JSONResult.ok("你已登录");
        } else {
            return JSONResult.ok("你未登录");
        }
    }


    @GetMapping("/require_auth")
    @RequiresAuthentication
    public JSONResult requireAuth() {
        return JSONResult.ok("你已认证");
    }

    @GetMapping("/require_role")
    @RequiresRoles("teacher")
    public JSONResult requireRole(HttpServletRequest request) {
        if(request.getHeader("Token") == null){
            return JSONResult.errorNoLoginMsg(LvException.ErrorMsg.USER_NO_LOGIN);
        }
        return JSONResult.ok("你是该角色");
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"lv:student"})
    public JSONResult requirePermission() {
        return JSONResult.ok("你具有该权限");
    }


}
