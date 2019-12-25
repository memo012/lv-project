package com.lv.adminsys.modules.controller.web;

import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.modules.service.web.IWebTeacherService;
import com.lv.adminsys.modules.vo.web.WebTeacherCheckDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Qiang
 * @version 1.0
 * @description web端老师审核controller
 * @date 2019/12/25 下午2:08
 **/
@RestController
@RequestMapping("api/v1/web/")
public class WebTeachCheckController {

    @Autowired
    private IWebTeacherService iWebTeacherService;

    /**
     *  审核人的审核列表
     * @param
     * @return
     */
    @PostMapping("/checkMessage")
    public JSONResult checkMessage(@RequestBody WebTeacherCheckDTO webTeacherCheckDTO){
        return iWebTeacherService.queryTaskByUserId(webTeacherCheckDTO);
    }




}
