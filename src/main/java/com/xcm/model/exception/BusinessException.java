package com.xcm.model.exception;


import com.xcm.util.ErrorMessageUtil;

import java.util.Locale;
import java.util.Map;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1962625772749762564L;

    private int errorCode;

    private Map<String, String> errorParameters;

    private Locale locale;
    private Object[] params;

    public BusinessException(int errorCode) {
        this(errorCode, Locale.CHINA);
    }

    public BusinessException(int errorCode, Locale locale) {
        this(errorCode, null, locale);
    }

    public BusinessException(int errorCode, Object[] params, Locale locale) {
        super();
        this.errorCode = errorCode;
        this.params = params;
        this.locale = locale;
    }

    public BusinessException(int errorCode, String errorMessage) {
        this(errorCode, errorMessage, null);
    }

    public BusinessException(int errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
        this.locale = Locale.CHINA;
    }

    public BusinessException(int errorCode, String errorMessage,
                             Map<String, String> parameters) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorParameters = parameters;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public Map<String, String> getErrorParameters() {
        return errorParameters;
    }

    @Override
    public String getMessage() {
        String message = "";
        if (params != null) {
            message = ErrorMessageUtil.getMessage(String.valueOf(errorCode),
                    params, "", locale);
        } else {
            message = ErrorMessageUtil.getMessageByErrCode(errorCode, locale);
        }
        return message;
    }

    @Override
    public String getLocalizedMessage() {
        return ErrorMessageUtil.getMessageByErrCode(errorCode, locale);
    }
}
