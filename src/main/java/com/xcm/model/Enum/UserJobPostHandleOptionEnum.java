package com.xcm.model.Enum;


public enum UserJobPostHandleOptionEnum {
    TONG_ZHI_MIAN_SHI(1),
    BU_HE_SHI(2),
    MIAN_SHI_TONG_GUO(3),
    ENTERPRISE_DEl(4);
    private int value;

    private UserJobPostHandleOptionEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
