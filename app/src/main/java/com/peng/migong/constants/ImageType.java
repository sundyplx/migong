package com.peng.migong.constants;

/**
 * Created by Penglingxiao on 2017/3/31.
 */

public enum ImageType {

    ID_SOURCE("1"), STRING_SOURCE("2");

    private String type;

    ImageType(String _type) {
        this.type = _type;
    }

    public String getType() {
        return type;
    }
}
