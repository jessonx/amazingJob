package com.xcm.model.Enum;

/**
 * Created by 薛岑明 on 2017/3/16.
 */
public enum JobStatusEnum {
    NORMAL(0), DEL(1);
    private int value;

    private JobStatusEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
