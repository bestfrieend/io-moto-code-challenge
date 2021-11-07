package io.iomoto.challenge.application.controllers;

import io.iomoto.challenge.application.enums.ResultCodeEnum;
import io.iomoto.challenge.application.response.CommonResponse;
import io.iomoto.challenge.application.response.ResultBuilderUtil;
import io.iomoto.challenge.platform.exceptions.InvalidPropertiesException;
import io.iomoto.challenge.platform.exceptions.MissingResourceException;
import io.iomoto.challenge.platform.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class RestExceptionController {

    @ExceptionHandler
    public CommonResponse<?> handle(Throwable e) {
        log.error("Error: ", e);
        return ResultBuilderUtil.buildFailure(ResultCodeEnum.INTERAL_SERVER_ERROR);
    }

    public CommonResponse<?> handle(MissingServletRequestParameterException e) {
        log.debug(" Error:", e);
        return ResultBuilderUtil.buildFailure(ResultCodeEnum.STATUS_BAD_REQUEST, Utility.getStackTrace(e));
    }

    @ExceptionHandler
    public CommonResponse<?> handle(DuplicateKeyException e) {
        log.debug(" DuplicateKeyException {}", e);
        return ResultBuilderUtil.buildFailure(ResultCodeEnum.STATUS_BAD_REQUEST, Utility.getStackTrace(e));
    }

    @ExceptionHandler
    public CommonResponse<?> handle(IllegalArgumentException e) {
        log.debug(" Validation {}", e.getMessage());
        return ResultBuilderUtil.buildFailure(ResultCodeEnum.STATUS_BAD_REQUEST, Utility.getStackTrace(e));
    }

    @ExceptionHandler
    public CommonResponse<?> handle(MissingResourceException e) {
        log.debug(" MissingResourceException:{}", e);
        return ResultBuilderUtil.buildFailure(ResultCodeEnum.STATUS_NOT_FOUND, Utility.getStackTrace(e));
    }

    @ExceptionHandler
    public CommonResponse<?> handle(DataIntegrityViolationException e) {
        log.debug(" DataIntegrityViolationException: {} ", e);
        return ResultBuilderUtil.buildFailure(ResultCodeEnum.STATUS_BAD_REQUEST, Utility.getStackTrace(e));
    }

    @ExceptionHandler
    public CommonResponse<?> handle(InvalidPropertiesException e) {
        log.debug(" InvalidPropertiesException: {} ", e);
        return ResultBuilderUtil.buildFailure(ResultCodeEnum.STATUS_BAD_REQUEST, Utility.getStackTrace(e));
    }

}
