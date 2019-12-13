package com.freshomer.thememagic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.freshomer.thememagic.theme.Theme;
import com.freshomer.thememagic.theme.ThemeManager;
import com.freshomer.thememagic.theme.entity.ThemeCompatDelegate;

public class MainActivity extends AppCompatActivity {

    private ThemeCompatDelegate mThemeCompatDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mThemeCompatDelegate = new ThemeCompatDelegate(this);
        mThemeCompatDelegate.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        findViewById(R.id.theme_switch_btn).setOnClickListener((v) -> switchTheme());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mThemeCompatDelegate.onResume();
    }

    private void switchTheme() {
        ThemeManager themeManager = ThemeManager.getInstance();
        Theme theme = ThemeManager.getInstance().getCurrentTheme();
        if (theme == Theme.Default) {
            themeManager.applyTheme(Theme.Dark);
        } else {
            themeManager.applyTheme(Theme.Default);
        }
    }
}
