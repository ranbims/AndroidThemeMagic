package com.freshomer.thememagic.theme.entity;

import com.freshomer.thememagic.theme.Theme;

import java.lang.ref.WeakReference;

public abstract class BaseThemeItemReference<T> {

    protected WeakReference<T> mReference;

    public BaseThemeItemReference(T obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Can't initialize ThemeItemReference with null");
        }
        mReference = new WeakReference<>(obj);
    }

    public boolean isItemFinalized() {
        return mReference.get() == null;
    }

    public abstract void applyTheme(Theme theme);

    @Override
    public int hashCode() {
        Object item = mReference.get();
        return item == null ? 0 : item.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof BaseThemeItemReference)) {
            return false;
        }

        BaseThemeItemReference otherRef = (BaseThemeItemReference)obj;
        Object self = mReference.get();
        Object other = otherRef.mReference.get();
        return self == null ? other == null : self.equals(other);
    }
}
