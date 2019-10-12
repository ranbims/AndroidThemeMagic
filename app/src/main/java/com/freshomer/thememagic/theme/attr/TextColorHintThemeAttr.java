package com.freshomer.thememagic.theme.attr;

import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.freshomer.thememagic.theme.Theme;


public class TextColorHintThemeAttr extends TextColorThemeAttr {
    @Override
    public void apply(View view, Theme theme) {
        if (view instanceof TextView) {
            ((TextView)view).setHintTextColor(getResourceColorStateListById(view, mResourceId, theme));
        }
    }

    TextColorHintThemeAttr(int resourceId) {
        super(resourceId);
    }

    public static TextColorHintThemeAttr createThemeAttr(TypedValue typedValue) {
        if(typedValue.resourceId != 0 &&
                ((typedValue.type >= TypedValue.TYPE_INT_COLOR_ARGB8 &&
                        typedValue.type <= TypedValue.TYPE_INT_COLOR_RGB4) ||
                        typedValue.type == TypedValue.TYPE_STRING)) {
            return new TextColorHintThemeAttr(typedValue.resourceId);
        }

        return null;
    }
}
