package io.iomoto.challenge.application.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.iomoto.challenge.application.enums.ResultCodeEnum;

/**
 * @param <T>
 * @author Wasif
 */
public class CommonResponse<T> {

    protected Integer status;
    protected String code;
    protected String message;
    protected T data;

    private static ObjectMapper mapper = new ObjectMapper();

    public CommonResponse() {
    }

    public CommonResponse(int status, String code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
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
