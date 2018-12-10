package com.xcm.model.Enum;


public enum UserJobPostStatusEnum {
    CHU_SHEN(1),
    TONG_ZHI_MIAN_SHI(2),
    BU_HE_SHI(3),
    MIAN_SHI_TONG_GUO(4),
    YI_GUO_QI(5),
    DELETE(6);
    private int value;

    private UserJobPostStatusEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
