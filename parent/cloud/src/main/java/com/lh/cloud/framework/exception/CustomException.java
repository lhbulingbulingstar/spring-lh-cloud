package com.lh.cloud.framework.exception;

/**
 * 自定义异常
 *
 * @author lh
 */
public class CustomException extends RuntimeException {

    private String code;

    private String message;

    public CustomException(String message) {
        this.message = message;
    }

    public CustomException(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public CustomException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
