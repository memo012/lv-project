package com.lv.adminsys.common.enums;

/**
 * @author memo012
 *  账号类型
 */
public enum TaskTypeEnum {
    /**
     *  未审核类型
     */
    NO_AUDIT_TYPE(0),
    /**
     *  历史类型
     */
    HISTORY_TYPE(1);

    TaskTypeEnum(Integer typeName){
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
