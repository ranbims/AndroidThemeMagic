package com.freshomer.thememagic.theme.attr;

import android.util.TypedValue;
import android.view.View;

import androidx.appcompat.widget.AppCompatRadioButton;

import com.freshomer.thememagic.theme.Theme;

public class ButtonTintThemeAttr extends TintThemeAttr {
    @Override
    public void apply(View view, Theme theme) {
        if (view instanceof AppCompatRadioButton) {
            ((AppCompatRadioButton)view).setSupportButtonTintList(getResourceColorStateListById(view, mResourceId, theme));
        }
    }

    ButtonTintThemeAttr(int resourceId) {
        super(resourceId);
    }

    public static ButtonTintThemeAttr createThemeAttr(TypedValue typedValue) {
        if(typedValue.resourceId != 0 &&
                ((typedValue.type >= TypedValue.TYPE_INT_COLOR_ARGB8 &&
                        typedValue.type <= TypedValue.TYPE_INT_COLOR_RGB4) ||
                        typedValue.type == TypedValue.TYPE_STRING)) {
            return new ButtonTintThemeAttr(typedValue.resourceId);
        }

        return null;
    }
}
