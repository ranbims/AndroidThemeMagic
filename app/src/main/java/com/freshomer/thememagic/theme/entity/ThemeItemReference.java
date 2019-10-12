package com.freshomer.thememagic.theme.entity;

import com.freshomer.thememagic.theme.Theme;


public class ThemeItemReference extends BaseThemeItemReference<ThemeItem> {

    public ThemeItemReference(ThemeItem item) {
        super(item);
    }

    @Override
    public void applyTheme(Theme theme) {
        ThemeItem item = mReference.get();
        if (item != null) {
            item.applyTheme(theme);
        }
    }
}
