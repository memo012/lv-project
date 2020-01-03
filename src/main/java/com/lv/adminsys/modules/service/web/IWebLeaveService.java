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
    JSONResult findMessageList(String account);

    /**
     *  标记消息已读(单条)
     * @param lvMessageId
     * @param accountType
     * @return
     */
    JSONResult markMsgIsRead(String lvMessageId, Integer accountType);

    /**
     *  消息删除 (单条)
     * @param lvMessageId
     * @param accountType
     * @return
     */
    JSONResult markMsgDelete(String lvMessageId, Integer accountType);

    /**
     *  标记消息已读 (全部)
     * @param account
     * @param accountType
     * @return
     */
    JSONResult markAllMsgIsRead(String account, Integer accountType);

    /**
     *  消息删除(全部)
     * @param account
     * @param accountType
     * @return
     */
    JSONResult markAllMsgDelete(String account, Integer accountType);

}
