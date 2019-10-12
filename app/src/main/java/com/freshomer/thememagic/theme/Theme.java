package com.freshomer.thememagic.theme;


public enum Theme {
    Default(),
    Dark("_dark");

    private String mSuffix;

    Theme() {
    }

    Theme(String suffix) {
        mSuffix = suffix;
    }

    String getSuffix() {
        return mSuffix;
    }
}
