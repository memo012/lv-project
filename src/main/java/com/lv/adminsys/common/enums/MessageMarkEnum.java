package com.lv.adminsys.common.enums;

/**
 * @author memo012
 *  消息状态类型
 */

public enum MessageMarkEnum {


    /**
     *  消息未读
     */
    NO_READ(0),

    /**
     *  单条消息已读
     */
    IS_READ(1),

    /**
     *  消息全部已读
     */
    IS_ALL_READ(2),
    /**
     *  单条消息删除
     */
    IS_DELETE(-1),

    /**
     *  消息全部删除
     */
    IS_ALL_DELETE(-2);

    MessageMarkEnum(Integer typeName){
        this.typeName = typeName;
    }


    public Integer getTypeName() {
        return typeName;
    }

    public void setTypeName(Integer typeName) {
        this.typeName = typeName;
    }

    private Integer typeName;

}
