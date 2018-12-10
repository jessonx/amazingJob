package com.xcm.model.Enum;

/**
 * Created by 薛岑明 on 2017/2/15.
 */
public enum TokenTypeEnum {
    MAIL(1);
    private int value;

    private TokenTypeEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
