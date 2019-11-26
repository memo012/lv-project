package com.lv.adminsys.modules.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @Author: qiang
 * @ClassName: ShiroEncrypt
 * @Description: 加密
 * @Date: 2019/11/20 下午10:31
 * @Version: 1.0
 **/
public class ShiroEncrypt {

    /**
     * 加密方法
     * @param userName
     * @param password
     * @return
     */
    public static String encrypt(String userName, String password){
        int index = userName.trim().length();
        String salt = userName.trim().charAt(index > 5 ? index - 4 : 0) + "";
        return new Md5Hash(password.trim(), salt).toString();

    }

    public static void main(String[] args) {
        System.out.println(encrypt("1713011332", "12345"));
    }

}
