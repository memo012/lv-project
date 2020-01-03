package com.lv.adminsys.common.enums;

/**
 * @author memo012
 *  账号类型
 */
public enum AccountTypeEnum {
    /**
     *  学生账号类型
     */
    STUDENT_TYPE(0),
    /**
     *  老师账号类型
     */
    TEACHER_TYPE(1);

    AccountTypeEnum(Integer typeName){
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
