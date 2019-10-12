package com.freshomer.thememagic.theme.attr;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.freshomer.thememagic.theme.Theme;
import com.freshomer.thememagic.theme.ThemeUtils;


public class TextColorThemeAttr extends ThemeAttr{

    @Override
    public void apply(View view, Theme theme) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setTextColor(getResourceColorStateListById(view, mResourceId, theme));
        }
    }

    public static TextColorThemeAttr createThemeAttr(TypedValue typedValue) {
        if(typedValue.resourceId != 0 &&
                ((typedValue.type >= TypedValue.TYPE_INT_COLOR_ARGB8 &&
                typedValue.type <= TypedValue.TYPE_INT_COLOR_RGB4) ||
                typedValue.type == TypedValue.TYPE_STRING)) {
            return new TextColorThemeAttr(typedValue.resourceId);
        }

        return null;
    }

    TextColorThemeAttr(int resourceId) {
        super(android.R.attr.textColor, resourceId);
    }

    protected ColorStateList getResourceColorStateListById(View view, int resourceId, Theme theme) {
        Resources resources = view.getResources();
        int targetId = ThemeUtils.getResourceIdByTheme(resources, resourceId, theme);
        return resources.getColorStateList(targetId);
    }
}
