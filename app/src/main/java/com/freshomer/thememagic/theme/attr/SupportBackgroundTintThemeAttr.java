package com.freshomer.thememagic.theme.attr;

import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;

import androidx.core.view.TintableBackgroundView;

import com.freshomer.thememagic.R;
import com.freshomer.thememagic.theme.Theme;
import com.freshomer.thememagic.theme.ThemeUtils;

public class SupportBackgroundTintThemeAttr extends ThemeAttr{

    @Override
    public void apply(View view, Theme theme) {
        int resourceId = getResourceById(view, mResourceId, theme);
        Resources resources = view.getResources() != null ?
                view.getResources() : view.getResources();
        if (view instanceof TintableBackgroundView) {
            ((TintableBackgroundView)view).setSupportBackgroundTintList(resources.getColorStateList(resourceId));
        }
    }

    public static SupportBackgroundTintThemeAttr createThemeAttr(TypedValue typedValue) {
        if(typedValue.resourceId != 0 &&
                ((typedValue.type >= TypedValue.TYPE_INT_COLOR_ARGB8 &&
                        typedValue.type <= TypedValue.TYPE_INT_COLOR_RGB4) || typedValue.type == TypedValue.TYPE_STRING)) {
            return new SupportBackgroundTintThemeAttr(typedValue.resourceId);
        }

        return null;
    }

    SupportBackgroundTintThemeAttr(int resourceId) {
        super(R.attr.backgroundTint, resourceId);
    }

    protected int getResourceById(View view, int resourceId, Theme theme) {
        Resources resources = view.getResources() != null ?
                view.getResources() : view.getResources();
        int targetId = ThemeUtils.getResourceIdByTheme(resources, resourceId, theme);
        return targetId;
    }
}
