package com.lv.adminsys.modules.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: qiang
 * @ClassName: LvRolesEntity
 * @Description: 用户角色
 * @Date: 2019/11/15 下午12:25
 * @Version: 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("lv_roles")
public class LvRolesEntity implements Serializable {

    private static final long serialVersionUID = -4165923056329207161L;
    /**
     * 角色id
     */
    private Integer rid;

    /**
     * 角色名称
     */
    private String lvRname;

    /**
     * 一个角色对应多个权限
     */
    @TableField(exist = false)
    private LvPermissionEntity permissionSet;


}
