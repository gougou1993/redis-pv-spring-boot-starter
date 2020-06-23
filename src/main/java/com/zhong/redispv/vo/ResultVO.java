package com.zhong.redispv.vo;

import java.io.Serializable;

/**
 * 结果封装
 */
public class ResultVO  implements Serializable {

    private static final String SUCCESS_CODE = "200";
    private static final String SUCCESS_MSG = "成功";
    private static final String ERROR_CODE = "400";
    private static final String ERROR_MSG= "失败";


    private String code;
    private String msg;
    private int version = 1;
    private Object data;

    public ResultVO(){

    }

    public ResultVO(String code, String msg, int version, Object data) {
        this.code = code;
        this.msg = msg;
        this.version = version;
        this.data = data;
    }

    public static ResultVO success(Object data){
        return new ResultVO(ResultVO.SUCCESS_CODE,ResultVO.SUCCESS_MSG,1,data);
    }
    public static ResultVO error(Object data){
        return new ResultVO(ResultVO.ERROR_CODE,ResultVO.ERROR_MSG,1,data);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultVO{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", version=" + version +
                ", data=" + data +
                '}';
    }
}
