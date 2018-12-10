package com.xcm.util;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class ErrorMessageUtil {

    private static MessageSource messageSource;

    public static void setMessageSource(MessageSource messageSource) {
        ErrorMessageUtil.messageSource = messageSource;
    }

    public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        String msg = messageSource.getMessage(code, args, defaultMessage,
                locale);
        return msg != null ? msg.trim() : defaultMessage;
    }

    /**
     * 根据错误码返回错误消息
     *
     * @param errCode 错误码
     * @param locale  本地信息
     * @return 错误消息，没有时返回null
     */
    public static String getMessageByErrCode(int errCode, Locale locale) {
        return getMessage(String.valueOf(errCode), new Object[]{}, "default message", locale);
    }
}
