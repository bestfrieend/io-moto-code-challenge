package io.iomoto.challenge.application.enums;

public enum ResultCodeEnum {
    /**
     * SUCCESS STATUS
     */
    SUCCESS("0", 200, "SUCCESS"),
    STATUS_CREATED("CREATED-201", 201, "Created"),
    STATUS_ACCEPTED("ACCEPTED-202", 202, "Accepted"),
    STATUS_NO_CONTENT("NO-CONTENT-204", 204, "No Content Found!"),

    STATUS_MOVED_PERMANENTLY("MOVED-PERMANENTLY-301", 301, "Resource Moved Permanentaly"),
    STATUS_FOUND("RESOURCE-FOUND-302", 302, "Resource Found"),
    STATUS_SEE_OTHER("SEE-OTHER-303", 303, "See Other Resource"),
    STATUS_NOT_MODIFIED("NOT-MODIFIED-304", 304, "Resource Not Modified"),
    STATUS_TEMPORARY_REDIRECT("TEMPORARY-REDIRECT-307", 307, "Temporary Redirect"),


    STATUS_BAD_REQUEST("BAD-REQUEST-400", 400, "Bad Request"),
    STATUS_UN_AUTHORIZED("UN-AUTHORIZED-401", 401, "Un-Authorized Access"),
    STATUS_FORBIDDEN("FORBIDDEN-403", 403, "Action is Forbidden"),
    STATUS_NOT_FOUND("NOT-FOUND-404", 404, "Requested Resource Not Found"),
    STATUS_METHOD_NOT_ALLOWED("NOT-ALLOWED-405", 405, "Method Not Allowed"),
    STATUS_NOT_ACCEPTABLE("NOT-ACCEPTABLE-406", 406, "Request is Not Acceptable"),
    STATUS_PRECONDITION_FAILED("PRECONDITION-FAILED-412", 412, "Pre/condition failed, params not correct"),

    INTERAL_SERVER_ERROR("INTERNAL-ERROR-500", 500, "Internal Server Error"),
    CREATE_FAILED("CREATE-ERROR-501", 501, "Internal Server Error"),
    DELETE_FAILED("DELETE-ERROR-502", 502, "Internal Server Error"),
    UPDATE_FAILED("UPDATE-ERROR-503", 503, "Internal Server Error");


    private String code;
    private Integer status;
    private String message;

    ResultCodeEnum(String code, Integer status, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatus() {
        return status;
    }
}
