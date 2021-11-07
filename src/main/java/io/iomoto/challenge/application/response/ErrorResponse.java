package io.iomoto.challenge.application.response;

public class ErrorResponse<T> extends CommonResponse {

    public ErrorResponse(int status, String code, String message, T data) {
        super.status = status;
        super.code = code;
        super.message = message;
        super.data = data;
    }
}
