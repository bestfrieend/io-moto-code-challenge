package com.iomoto.challenge.exceptions;


import com.iomoto.challenge.enums.ResultCodeEnum;

public class BizException extends RuntimeException {
    private static final long serialVersionUID = 7795219532426490323L;
    private ResultCodeEnum resultCodeEnum;

    private String arg;

    public BizException(String msg) {
        super(msg);
    }

    public BizException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
    }

    public BizException(ResultCodeEnum resultCodeEnum, String arg) {
        this.resultCodeEnum = resultCodeEnum;
        this.arg = arg;
    }

    public ResultCodeEnum getResultCodeEnum() {
        return resultCodeEnum;
    }

    public void setResultCodeEnum(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }
}
