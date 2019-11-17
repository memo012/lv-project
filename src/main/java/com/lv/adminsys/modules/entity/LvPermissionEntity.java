package com.lv.adminsys.modules.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: qiang
 * @ClassName: LvPermission
 * @Description: 用户权限
 * @Date: 2019/11/15 下午12:38
 * @Version: 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("lv_permission")
public class LvPermissionEntity implements Serializable {
    private static final long serialVersionUID = -7951213112594357240L;

    /**
     * 权限id
     */
    @TableId("pid")
    private Integer pid;


    /**
     *  权限名称
     */
    private String lvPname;

    /**
     * 权限地址
     */
    private String lvUrl;

}
