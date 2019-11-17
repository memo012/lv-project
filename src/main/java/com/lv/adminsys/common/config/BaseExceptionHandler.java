package com.lv.adminsys.common.config;


import com.lv.adminsys.common.utils.JSONResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: qiang
 * @ClassName: BaseExceptionHandler
 * @Description: 异常处理类
 * @Date: 2019/11/6 下午10:26
 * @Version: 1.0
 **/
@ControllerAdvice
public class BaseExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JSONResult error(Exception e){
        return JSONResult.errorRolesMsg(e.getMessage());
    }

}
