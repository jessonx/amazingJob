package com.xcm.model.resource;

/**
 * Created by 薛岑明 on 2017/4/11.
 */
public class UserPostRecordOptionRes {
    private int option;
    private String optionName;

    public UserPostRecordOptionRes() {
    }

    public UserPostRecordOptionRes(int option, String optionName) {
        this.option = option;
        this.optionName = optionName;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }
}
