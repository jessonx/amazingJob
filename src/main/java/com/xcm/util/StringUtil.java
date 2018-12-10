package com.xcm.util;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.security.MessageDigest;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static final String EMAIL_REGEX = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    private static final String PHONE_REGEX = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
    private static final String TEL_REGEX = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";
    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static boolean isNullOrEmpty(String value) {
        return isNullOrEmpty(value, true);
    }

    public static boolean isNullOrEmpty(String value, boolean isTrim) {
        if (null == value) {
            return true;
        }
        if (value.length() == 0) {
            return true;
        }
        if (isTrim) {
            if (value.trim().length() == 0) {
                return true;
            }
        }
        return false;
    }

    public static String md5Hex(String data) {
        return getMD5(data);
    }

    public static String md5Hex(String data, boolean upper) {
        return getMD5(data, upper);
    }

    private static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }

    private static String getMD5(String message, boolean upper) {
        String md5str = "";
        try {
            // 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 2 将消息变成byte数组
            byte[] input = message.getBytes();
            // 3 计算后获得字节数组,这就是那128位了
            byte[] buff = md.digest(input);
            // 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
            if (upper) {
                md5str = new String(encodeHex(buff, DIGITS_UPPER));
            } else {
                md5str = new String(encodeHex(buff, DIGITS_LOWER));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5str;
    }

    private static String getMD5(String message) {
        return getMD5(message, false);
    }

    public static String voiceValueFormat(String content,
                                          int friendRequestCount, int anonymousMatchCount, int recordSession) {
        if (isNullOrEmpty(content)) {
            return "";
        }
        if (content.contains("{fr}")) {
            content = content.replace("{fr}",
                    String.valueOf(friendRequestCount));
        }
        if (content.contains("{am}")) {
            content = content.replace("{am}",
                    String.valueOf(anonymousMatchCount));
        }
        if (content.contains("{rs}")) {
            content = content.replace("{rs}", String.valueOf(recordSession));
        }
        return content;

    }

    public static <T> String listToString(List<T> a) {
        if (a == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (T t : a) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(t);
        }
        return result.toString();
    }

    public static boolean isMailStr(String email) {
        //正则表达式的模式
        Pattern p = Pattern.compile(EMAIL_REGEX);
        //正则表达式的匹配器
        Matcher m = p.matcher(email);
        //进行正则匹配
        return m.matches();
    }

    public static boolean isTelStr(String telNum) {
        Pattern p1 = Pattern.compile(PHONE_REGEX);
        Pattern p2 = Pattern.compile(TEL_REGEX);
        Matcher m1 = p1.matcher(telNum);
        Matcher m2 = p2.matcher(telNum);
        return m1.matches() || m2.matches();
    }
}
