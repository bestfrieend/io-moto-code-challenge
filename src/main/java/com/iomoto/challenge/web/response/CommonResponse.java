package com.iomoto.challenge.web.response;

import com.alibaba.fastjson.JSONObject;
import com.iomoto.challenge.enums.ResultCodeEnum;
import com.iomoto.challenge.exceptions.BizException;

/**
 * @param <T>
 * @author Wasif
 */
public class CommonResponse<T> {

    protected Integer status;
    protected String code;
    protected String message;
    protected T data;

    public CommonResponse() {
    }

    public CommonResponse(int status, String code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Object getData(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        CommonResponse commonResponse = JSONObject.parseObject(s, CommonResponse.class);
        if (commonResponse.getCode() == null || !commonResponse.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
            throw new BizException(commonResponse.getMessage());
        }
        Object data = commonResponse.getData();
        return data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return status != null && status == 200 && ResultCodeEnum.SUCCESS.getCode().equals(code);
    }

}
