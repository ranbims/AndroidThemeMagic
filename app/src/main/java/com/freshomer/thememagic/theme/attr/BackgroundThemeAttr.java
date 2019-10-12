package com.freshomer.thememagic.theme.attr;

import android.content.res.Resources;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;

import com.freshomer.thememagic.theme.Theme;
import com.freshomer.thememagic.theme.ThemeUtils;

public class BackgroundThemeAttr extends ThemeAttr{

    @Override
    public void apply(View view, Theme theme) {
        int resourceId = getResourceById(view, mResourceId, theme);
        Resources resources = view.getResources() != null ?
                view.getResources() : view.getResources();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            view.setBackground(resources.getDrawable(resourceId));
        } else {
            view.setBackground(resources.getDrawable(resourceId, view.getContext().getTheme()));
        }
    }

    public static BackgroundThemeAttr createThemeAttr(TypedValue typedValue) {
        if(typedValue.resourceId != 0 &&
                ((typedValue.type >= TypedValue.TYPE_INT_COLOR_ARGB8 &&
                        typedValue.type <= TypedValue.TYPE_INT_COLOR_RGB4) || typedValue.type == TypedValue.TYPE_STRING)) {
            return new BackgroundThemeAttr(typedValue.resourceId);
        }

        return null;
    }

    BackgroundThemeAttr(int resourceId) {
        super(android.R.attr.background, resourceId);
    }

    protected int getResourceById(View view, int resourceId, Theme theme) {
        Resources resources = view.getResources() != null ?
                view.getResources() : view.getResources();
        int targetId = ThemeUtils.getResourceIdByTheme(resources, resourceId, theme);
        return targetId;
    }
}
