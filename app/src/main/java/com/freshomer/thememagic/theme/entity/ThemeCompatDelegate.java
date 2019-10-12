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
 * Created by ranbi on 18-8-6.
 */

public class ThemeCompatDelegate implements ThemeItem {

    // Todo(ranbi): Support Activity here.
    public final AppCompatActivity mActivity;

    public ThemeCompatDelegate(AppCompatActivity activity) {
        mActivity = activity;
    }

    @Override
    public void applyTheme(Theme theme) {
        if (!mActivity.isDestroyed() && isThemeSupported()) {
            int windowBackgroundId = getDefaultWindowBackgroundId();
            if (windowBackgroundId != 0) {
                windowBackgroundId = ThemeUtils.getResourceIdByTheme(
                        mActivity.getResources(),
                        windowBackgroundId,
                        theme);

                mActivity.getWindow().setBackgroundDrawableResource(windowBackgroundId);
            }

            int styleId = getThemedStyleId(theme);
            if (styleId != 0) {
                mActivity.getTheme().applyStyle(styleId, true);
                mActivity.getWindow().getDecorView().getContext().getTheme().applyStyle(styleId, true);
            }
        }
    }

    public int getDefaultWindowBackgroundId() {
        return R.color.defaultBackground;
    }

    public void onCreate(Bundle savedInstanceState) {
        if (isThemeSupported()) {
            LayoutInflaterCompat.setFactory(LayoutInflater.from(mActivity), new ThemeLayoutInflaterFactory(mActivity.getDelegate()));
            ThemeManager.getInstance().addThemeItem(this);

            Theme currentTheme = ThemeManager.getInstance().getCurrentTheme();
            int styleId = getThemedStyleId(currentTheme);
            if (styleId != 0) {
                Resources.Theme theme = mActivity.getTheme();
                theme.applyStyle(styleId, true);
            }
        }
    }

    public void onResume() {
        this.applyTheme(ThemeManager.getInstance().getCurrentTheme());
    }

    protected boolean isThemeSupported() {
        return true;
    }

    protected int getThemedStyleId(Theme theme) {
        switch (theme) {
//            case Dark:
//                return org.chromium.chrome.R.style.DarkThemeStyle;
//            case Default:
//                return org.chromium.chrome.R.style.LightThemeStyle;
            default:
                return 0;
        }
    }
}
