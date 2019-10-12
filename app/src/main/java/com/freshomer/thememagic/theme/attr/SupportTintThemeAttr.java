package com.freshomer.thememagic.theme.attr;

import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import androidx.core.widget.ImageViewCompat;

import com.freshomer.thememagic.R;
import com.freshomer.thememagic.theme.Theme;


public class SupportTintThemeAttr extends TintThemeAttr {
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

    SupportTintThemeAttr(int resourceId) {
        super(R.attr.tint, resourceId);
    }
}
