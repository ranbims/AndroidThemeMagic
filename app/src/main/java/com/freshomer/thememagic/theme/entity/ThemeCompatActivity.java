package com.freshomer.thememagic.theme.entity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.LayoutInflaterCompat;

import com.freshomer.thememagic.R;
import com.freshomer.thememagic.theme.Theme;
import com.freshomer.thememagic.theme.ThemeLayoutInflaterFactory;
import com.freshomer.thememagic.theme.ThemeManager;
import com.freshomer.thememagic.theme.ThemeUtils;

/**
 * Created by ranbi on 18-7-6.
 */

public class ThemeCompatActivity extends AppCompatActivity implements ThemeWindow, ThemeItem {

    @Override
    public void applyTheme(Theme theme) {
        if (!isDestroyed() && isThemeSupported()) {
            int windowBackgroundId = getDefaultWindowBackgroundId();
            if (windowBackgroundId != 0) {
                windowBackgroundId = ThemeUtils.getResourceIdByTheme(
                        getResources(),
                        windowBackgroundId,
                        theme);

                getWindow().setBackgroundDrawableResource(windowBackgroundId);
            }

            int styleId = getThemedStyleId(theme);
            if (styleId != 0) {
                this.getTheme().applyStyle(styleId, true);
                this.getWindow().getDecorView().getContext().getTheme().applyStyle(styleId, true);
            }
        }
    }

    @Override
    public int getDefaultWindowBackgroundId() {
        return R.color.defaultBackground;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (isThemeSupported()) {
            LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new ThemeLayoutInflaterFactory(getDelegate()));
            ThemeManager.getInstance().addThemeItem(this);

            Theme currentTheme = ThemeManager.getInstance().getCurrentTheme();
            int styleId = getThemedStyleId(currentTheme);
            if (styleId != 0) {
                Resources.Theme theme = this.getTheme();
                theme.applyStyle(styleId, true);
            }
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isThemeSupported()) {
            this.applyTheme(ThemeManager.getInstance().getCurrentTheme());
        }
    }

    protected boolean isThemeSupported() {
        return true;
    }

    protected int getThemedStyleId(Theme theme) {
        switch (theme) {
//            case Dark:
//                return R.style.DarkThemeStyle;
//            case Default:
//                return R.style.LightThemeStyle;
            default:
                return 0;
        }
    }
}
