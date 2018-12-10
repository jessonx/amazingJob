package com.xcm.model.Enum;

/**
 * Created by 薛岑明 on 2017/1/5.
 */
public enum UserTypeEnum {
    PERSONAL(1), ENTERPRISE(2); // 调用构造函数来构造枚举项

    private int value;

    private UserTypeEnum(int value) { // 必须是private的，否则编译错误
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
