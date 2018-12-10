package com.xcm.model.Enum;

/**
 * Created by 薛岑明 on 2017/1/5.
 */
public enum OffMsgTypeEnum {
    DEFAULT(1);//默认为1
    private int value;

    private OffMsgTypeEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
