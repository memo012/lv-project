package com.lv.adminsys.modules.service.web;

import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.modules.vo.web.WebTeacherCheckDTO;

/**
 * @author Qiang
 * @version 1.0
 * @description web端请假处理
 * @date 2019/12/25 下午2:41
 **/
public interface IWebTeacherService {
    /**
     *  web端查办代理
     * @param teacherCheckDTO
     * @return
     */
    JSONResult queryTaskByUserId(WebTeacherCheckDTO teacherCheckDTO);
}
