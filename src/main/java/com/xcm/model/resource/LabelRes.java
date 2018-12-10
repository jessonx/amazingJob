package com.xcm.model.resource;

/**
 * Created by 薛岑明 on 2017/3/15.
 */
public class LabelRes {
    private int labelNum;
    private String labelName;

    public LabelRes() {

    }

    public LabelRes(int labelNum, String labelName) {
        this.labelNum = labelNum;
        this.labelName = labelName;
    }

    public int getLabelNum() {
        return labelNum;
    }

    public void setLabelNum(int labelNum) {
        this.labelNum = labelNum;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
