package com.lv.adminsys.common.utils;

/**
 * @author Qiang
 * @description 自定义响应数据结构
 * 				这个类是提供给门户，ios，安卓，微信商城用的
 * 				门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式(类或者list)
 * 				其他自行处理
 * 				200：表示成功
 * 			    400: 请求错误
 * 			    401: 查询为空
 * 			    402: 参数错误
 * 			    403: 请求超时
 * 			    404: 表示页面找不到
 * 			    405： 用户未登录
 * 				500：表示错误，错误信息在msg字段中
 * 				501：bean验证错误，不管多少个错误都以map形式返回
 * 				502：拦截器拦截到用户token出错
 * 			    503: 不具备角色功能
 * 			    504: 不具备某种权限
 * 				555：异常抛出信息
 */
public class JSONResult {

    /**
     *  响应业务状态
     */
    private Integer status;

    /**
     *  响应消息
     */
    private String msg;

    /**
     *  响应中的数据
     */
    private Object data;

    /**
     *
     */
    private String ok;

    public static JSONResult build(Integer status, String msg, Object data) {
        return new JSONResult(status, msg, data);
    }

    public static JSONResult ok(Object data) {
        return new JSONResult(data);
    }

    public static JSONResult ok() {
        return new JSONResult(null);
    }

    public static JSONResult errorMsg(String msg) {
        return new JSONResult(500, msg, null);
    }

    public static JSONResult errorMap(Object data) {
        return new JSONResult(501, "error", data);
    }

    public static JSONResult errorTokenMsg(String msg) {
        return new JSONResult(502, msg, null);
    }

    public static JSONResult errorRolesMsg(String msg) {
        return new JSONResult(503, msg, null);
    }

    public static JSONResult errorPermissionMsg(String msg) {
        return new JSONResult(504, msg, null);
    }

    public static JSONResult errorNoLoginMsg(String msg) {
        return new JSONResult(505, msg, null);
    }


    public static JSONResult errorException(String msg) {
        return new JSONResult(555, msg, null);
    }

    public JSONResult() {

    }

    public JSONResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public JSONResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

}
