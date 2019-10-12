package com.freshomer.thememagic.theme.attr;

import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.freshomer.thememagic.theme.Theme;
import com.freshomer.thememagic.theme.ThemeUtils;

public class SrcThemeAttr extends ThemeAttr{

    @Override
    public void apply(View view, Theme theme) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            imageView.setImageResource(getImageResourceId(view, mResourceId, theme));
        }
    }

    public static SrcThemeAttr createThemeAttr(TypedValue typedValue) {
        if(typedValue.resourceId != 0 &&
                ((typedValue.type >= TypedValue.TYPE_INT_COLOR_ARGB8 &&
                        typedValue.type <= TypedValue.TYPE_INT_COLOR_RGB4) ||
                        typedValue.type == TypedValue.TYPE_STRING)) {
            return new SrcThemeAttr(typedValue.resourceId);
        }

        return null;
    }

    SrcThemeAttr(int resourceId) {
        super(android.R.attr.src, resourceId);
    }

    private int getImageResourceId(View view, int resourceId, Theme theme) {
        Resources resources = view.getResources();
        int targetId = ThemeUtils.getResourceIdByTheme(resources, resourceId, theme);
        return targetId;
    }
}
