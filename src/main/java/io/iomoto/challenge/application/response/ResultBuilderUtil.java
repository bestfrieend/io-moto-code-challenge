package io.iomoto.challenge.application.response;

import io.iomoto.challenge.application.enums.ResultCodeEnum;

/**
 * @author Wasif
 */
public class ResultBuilderUtil {
    public static CommonResponse buildSuccess() {
        return buildSuccess(null);
    }

    public static CommonResponse buildSuccess(Object o) {
        CommonResponse commonRespone = new CommonResponse(ResultCodeEnum.SUCCESS.getStatus(), ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), o);
        return commonRespone;
    }

    public static CommonResponse buildSuccess(String message) {
        CommonResponse commonResponse = new CommonResponse(ResultCodeEnum.SUCCESS.getStatus(), ResultCodeEnum.SUCCESS.getCode(), message, null);
        return commonResponse;
    }

    public static ErrorResponse buildFailure(int status, String code, String message) {
        ErrorResponse errorRespone = new ErrorResponse(status, code, message, null);
        return errorRespone;
    }

    public static ErrorResponse buildFailure(ResultCodeEnum resultCodeEnum) {
        return buildFailure(resultCodeEnum.getStatus(), resultCodeEnum.getCode(), resultCodeEnum.getMessage());
    }

    public static ErrorResponse buildFailure(ResultCodeEnum resultCodeEnum, String msg) {
        return buildFailure(resultCodeEnum.getStatus(), resultCodeEnum.getCode(), msg);
    }
}

