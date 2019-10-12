package com.freshomer.thememagic.theme.attr;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import androidx.core.widget.ImageViewCompat;

import com.freshomer.thememagic.theme.Theme;
import com.freshomer.thememagic.theme.ThemeUtils;

/**
 * Created by ranbi on 18-7-19.
 */

public class TintThemeAttr extends ThemeAttr {
    @Override
    public void apply(View view, Theme theme) {
        if (view instanceof ImageView) {
            ImageViewCompat.setImageTintList((ImageView) view, getResourceColorStateListById(view, mResourceId, theme));
        }
    }

    public static TintThemeAttr createThemeAttr(TypedValue typedValue) {
        if(typedValue.resourceId != 0 &&
                ((typedValue.type >= TypedValue.TYPE_INT_COLOR_ARGB8 &&
                        typedValue.type <= TypedValue.TYPE_INT_COLOR_RGB4) ||
                        typedValue.type == TypedValue.TYPE_STRING)) {
            return new TintThemeAttr(typedValue.resourceId);
        }

        return null;
    }

    TintThemeAttr(int resourceId) {
        this(android.R.attr.tint, resourceId);
    }

    TintThemeAttr(int attrType, int resourceId) {
        super(attrType, resourceId);
    }

    protected ColorStateList getResourceColorStateListById(View view, int resourceId, Theme theme) {
        Resources resources = view.getResources();
        int targetId = ThemeUtils.getResourceIdByTheme(resources, resourceId, theme);
        return resources.getColorStateList(targetId);
    }
}
