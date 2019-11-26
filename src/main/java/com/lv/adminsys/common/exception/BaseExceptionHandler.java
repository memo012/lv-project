package com.lv.adminsys.common.exception;


import com.lv.adminsys.common.utils.JSONResult;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: qiang
 * @ClassName: BaseExceptionHandler
 * @Description: 异常处理类
 * @Date: 2019/11/6 下午10:26
 * @Version: 1.0
 **/
@RestControllerAdvice
public class BaseExceptionHandler {

    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public JSONResult handle401(ShiroException e) {
        return JSONResult.errorTokenMsg("token不存在");
    }

    // 捕捉AuthorizationException
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(AuthorizationException.class)
    public JSONResult handle401(AuthorizationException e) {
        return JSONResult.errorPermissionMsg("权限不足");
    }


    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JSONResult globalException(HttpServletRequest request, Throwable ex) {
        return JSONResult.errorMsg("异常");
    }


}
