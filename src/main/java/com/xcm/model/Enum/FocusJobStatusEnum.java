package com.xcm.model.Enum;

/**
 * Created by 薛岑明 on 2017/3/18.
 */
public enum FocusJobStatusEnum {
    CAN_POST(1), OVER_TIME(2),HAS_POST(3);
    private int value;

    private FocusJobStatusEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
