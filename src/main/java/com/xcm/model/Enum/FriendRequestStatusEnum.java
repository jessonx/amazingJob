package com.xcm.model.Enum;

/**
 * Created by 薛岑明 on 2017/3/18.
 */
public enum FriendRequestStatusEnum {
    PENDING(0), PASS(1), IGNORE(2);
    private int value;

    private FriendRequestStatusEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
