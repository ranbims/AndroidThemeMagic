package com.freshomer.thememagic.theme.attr;

import android.view.View;

import com.freshomer.thememagic.theme.Theme;

public abstract class ThemeAttr {

    private int mAttrType;
    protected int mResourceId;

    public ThemeAttr(int attrType, int resourceId) {
        this.mAttrType = attrType;
        this.mResourceId = resourceId;
    }

    public int getAttrType() {
        return mAttrType;
    }

    public int getResourceId() {
        return mResourceId;
    }

    public abstract void apply(View view, Theme theme);
}
