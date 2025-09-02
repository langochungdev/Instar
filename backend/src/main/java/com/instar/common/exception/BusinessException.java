package com.instar.common.exception;

public class BusinessException extends RuntimeException {
    private final BaseErrorCode errorCode;

    public BusinessException(BaseErrorCode errorCode, Object... args) {
        super(errorCode.formatMessage(args));
        this.errorCode = errorCode;
    }

    public BaseErrorCode getErrorCode() {
        return errorCode;
    }
}
