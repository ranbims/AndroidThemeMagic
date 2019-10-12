package com.freshomer.thememagic.theme.attr;

import android.util.TypedValue;

import com.freshomer.thememagic.R;


public class ThemeAttrFactory {

    // Called by inflater. Filter the attr by type.
    public static ThemeAttr createThemeAttr(int attrType, TypedValue typedValue) {

        switch (attrType) {
            case android.R.attr.textColor:
                return TextColorThemeAttr.createThemeAttr(typedValue);
            case android.R.attr.textColorHint:
                return TextColorHintThemeAttr.createThemeAttr(typedValue);
            case android.R.attr.background:
                return BackgroundThemeAttr.createThemeAttr(typedValue);
            case android.R.attr.src:
                return SrcThemeAttr.createThemeAttr(typedValue);
            case android.R.attr.tint:
                return TintThemeAttr.createThemeAttr(typedValue);
            case android.R.attr.buttonTint:
                return ButtonTintThemeAttr.createThemeAttr(typedValue);
            case android.R.attr.backgroundTint:
                return BackgroundTintThemeAttr.createThemeAttr(typedValue);
            default:
                if (R.attr.tint == attrType) {
                    return SupportTintThemeAttr.createThemeAttr(typedValue);
                } else if (R.attr.backgroundTint == attrType) {
                    return SupportBackgroundTintThemeAttr.createThemeAttr(typedValue);
                }
                return null;
        }
    }

    // Created by code excipitly. Ensure the id as we need.
    public static ThemeAttr createThemeAttr(int attrType, int resId) {
        switch (attrType) {
            case android.R.attr.textColor:
                return new TextColorThemeAttr(resId);
            case android.R.attr.textColorHint:
                return new TextColorHintThemeAttr(resId);
            case android.R.attr.background:
                return new BackgroundThemeAttr(resId);
            case android.R.attr.src:
                return new SrcThemeAttr(resId);
            case android.R.attr.tint:
                return new TintThemeAttr(resId);
            case android.R.attr.buttonTint:
                return new ButtonTintThemeAttr(resId);
            case android.R.attr.backgroundTint:
                return new BackgroundTintThemeAttr(resId);
            default:
                if (R.attr.tint == attrType) {
                    return new SupportTintThemeAttr(resId);
                } else if (R.attr.backgroundTint == attrType) {
                    return new SupportBackgroundTintThemeAttr(resId);
                }
                return null;
        }
    }
}
