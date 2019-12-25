package com.lv.adminsys.modules.service.web;

import com.lv.adminsys.common.utils.JSONResult;

/**
 * @author Qiang
 * @version 1.0
 * @description TODO
 * @date 2019/12/25 下午5:16
 **/
public interface IWebLeaveService {

    /**
     *  查询
     * @param account
     * @return
     */
    public JSONResult findMessageList(String account);

}
