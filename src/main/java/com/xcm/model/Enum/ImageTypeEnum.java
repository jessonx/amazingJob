package com.xcm.model.Enum;

/**
 * Created by 薛岑明 on 2017/3/17.
 */
public enum ImageTypeEnum {
    USER_HEADER_THUMB(1), ENTERPRISE_IMAGE(2),;
    private int value;

    private ImageTypeEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
