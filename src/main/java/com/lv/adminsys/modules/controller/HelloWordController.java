package com.lv.adminsys.modules.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: qiang
 * @ClassName: HelloWordController
 * @Description: 测试类
 * @Date: 2019/10/30 下午3:57
 * @Version: 1.0
 **/
@RestController
public class HelloWordController {

    @RequestMapping("/helloWord")
    public String helloWord(){
        return "hello";
    }

}
