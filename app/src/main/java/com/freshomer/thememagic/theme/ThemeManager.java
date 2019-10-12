package com.freshomer.thememagic.theme;

import android.app.Activity;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;

import com.freshomer.thememagic.theme.attr.ThemeAttr;
import com.freshomer.thememagic.theme.attr.ThemeAttrFactory;
import com.freshomer.thememagic.theme.entity.AndroidThemeView;
import com.freshomer.thememagic.theme.entity.BaseThemeItemReference;
import com.freshomer.thememagic.theme.entity.ThemeItem;
import com.freshomer.thememagic.theme.entity.ThemeItemReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;


public class ThemeManager {

    private static int DEFAULT_CHECK_THROTTLE = 200;
    private static int CHECK_THROTTLE_INCREASE_STEP = 100;

    private Theme mCurrentTheme;
    private ThemeControlDelegate mThemeControlDelegate = null;

    private int mCheckThrottle;

    private HashSet<BaseThemeItemReference> mThemeItemReferences;

    private static ThemeManager sInstance = new ThemeManager();

    public static ThemeManager getInstance() {
        return sInstance;
    }

    public Theme getCurrentTheme() {
        if (mThemeControlDelegate != null) {
            return mThemeControlDelegate.getCurrentTheme();
        }

        return mCurrentTheme;
    }

    public void addThemeItem(ThemeItem item) {
        addThemeItemReference(new ThemeItemReference(item));
    }

    public void addThemeItemReference(BaseThemeItemReference reference) {
        boolean added = mThemeItemReferences.add(reference);
        if (added) {
            reference.applyTheme(mCurrentTheme);
        }

        cleanRecycledItemIfNecessary();
    }

    // Todo(ranbi): refine this interface to suport multiple attrType.
    public void addAndroidThemeView(View view, int attrType, int resourceId) {
        ThemeAttr attr = ThemeAttrFactory.createThemeAttr(attrType, resourceId);
        AndroidThemeView themeView = new AndroidThemeView(view, new ArrayList<ThemeAttr>(Arrays.asList(attr)));
        mThemeItemReferences.add(themeView);
        if (mCurrentTheme != Theme.Default) {
            themeView.applyTheme(mCurrentTheme);
        }
    }

    public void applyTheme() {
        if (mThemeControlDelegate != null) {
            Theme newTheme = mThemeControlDelegate.getCurrentTheme();
            applyTheme(newTheme);
        }
    }

    public void applyTheme(Theme newTheme) {
        if (newTheme == mCurrentTheme) {
            return;
        }

        mCurrentTheme = newTheme;

        BaseThemeItemReference[] items = new BaseThemeItemReference[mThemeItemReferences.size()];
        mThemeItemReferences.toArray(items);
        for (BaseThemeItemReference item : items) {
            if (item.isItemFinalized()) {
                mThemeItemReferences.remove(item);
            } else {
                item.applyTheme(mCurrentTheme);
            }
        }
    }

    public void applyTheme(Theme newTheme, Activity activity) {
        Window window = activity.getWindow();
        if (newTheme != Theme.Default) {
            TypedArray array = window.getWindowStyle();
            TypedValue typedValue = new TypedValue();
            for (int i = 0; i < array.getIndexCount(); i++) {
                array.getValue(i, typedValue);
                if (typedValue.type == android.R.attr.windowBackground) {
                    int id = typedValue.resourceId;
                    int newId = ThemeUtils.getResourceIdByTheme(activity.getResources(), id, mCurrentTheme);
                    window.setBackgroundDrawableResource(newId);
                }
            }
        }

        applyTheme(newTheme);
    }

    public void setThemeControlDelegate(ThemeControlDelegate controller) {
        mThemeControlDelegate = controller;
        applyTheme(mThemeControlDelegate.getCurrentTheme());
    }

    private ThemeManager() {
        // Todo: Read theme from sharedpreference;
        mCurrentTheme = Theme.Default;
        mThemeItemReferences = new HashSet<>();
        mCheckThrottle = DEFAULT_CHECK_THROTTLE;
    }

    private void cleanRecycledItemIfNecessary() {
        if (mThemeItemReferences.size() > mCheckThrottle) {
            for (Iterator<BaseThemeItemReference> iterator = mThemeItemReferences.iterator(); iterator.hasNext();) {
                BaseThemeItemReference item = iterator.next();
                if (item.isItemFinalized()) {
                    iterator.remove();
                }
            }

            mCheckThrottle += CHECK_THROTTLE_INCREASE_STEP;
        }
    }
}
