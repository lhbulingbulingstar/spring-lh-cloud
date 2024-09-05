package com.lh.cloud.framework.exception;

import com.lh.cloud.common.param.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: lh
 * @Date: 2021/12/30 14:45
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 业务异常
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public Result customExceptionHandler(CustomException e) {
      return StringUtils.isBlank(e.getCode())? Result.error(e.getMessage()) : Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public Result bindExceptionHandler(BindException e) {
        return Result.error(e.getAllErrors().get(0).getDefaultMessage());
    }
    /**
     * 自定义验证异常
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        return Result.error("服务异常");
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return Result.error( e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
