package com.freshomer.thememagic.theme.entity;

import android.view.View;

import com.freshomer.thememagic.theme.Theme;
import com.freshomer.thememagic.theme.attr.ThemeAttr;

import java.util.List;

/**
 * Created by ranbi on 18-5-22.
 */

public class AndroidThemeView extends BaseThemeItemReference<View> {

    private List<ThemeAttr> mAttrs;

    public AndroidThemeView(View view, List<ThemeAttr> attrs) {
        super(view);
        this.mAttrs = attrs;
    }

    // Before apply, check isRecycled first;
    @Override
    public void applyTheme(Theme theme) {
        View view = mReference.get();
        if (view == null) {
            return;
        }

        for (ThemeAttr attr : mAttrs) {
            attr.apply(view, theme);
        }
    }

    @Override
    public boolean isItemFinalized() {
        if (mReference.get() != null) {
            return false;
        }

        try {
            if (mAttrs != null) {
                mAttrs.clear();
            }
        } catch (UnsupportedOperationException e) {
            mAttrs = null;
        }
        return true;
    }
}
