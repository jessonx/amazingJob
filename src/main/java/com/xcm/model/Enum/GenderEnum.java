package com.xcm.model.Enum;

/**
 * Created by 薛岑明 on 2017/2/15.
 */
public enum GenderEnum {
    MALE(1), FEMALE(2);
    private int value;

    private GenderEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
