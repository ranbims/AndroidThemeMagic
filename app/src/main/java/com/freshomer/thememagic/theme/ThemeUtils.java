package com.freshomer.thememagic.theme;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;

public class ThemeUtils {

    private static ThemeResourceCache sResourceCache = new ThemeResourceCache();

    public static int getColor(Resources res, int id) throws Resources.NotFoundException {
        int resId = getResourceIdByTheme(res, id, ThemeManager.getInstance().getCurrentTheme());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return res.getColor(resId, null);
        } else {
            return res.getColor(resId);
        }
    }

    public static ColorStateList getColorStateList(Resources res, int id) throws Resources.NotFoundException {
        int resId = getResourceIdByTheme(res, id, ThemeManager.getInstance().getCurrentTheme());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return res.getColorStateList(resId, null);
        } else {
            return res.getColorStateList(resId);
        }
    }

    public static Drawable getDrawable(Resources res, int id) throws Resources.NotFoundException {
        int resId = getResourceIdByTheme(res, id, ThemeManager.getInstance().getCurrentTheme());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return res.getDrawable(resId, null);
        } else {
            return res.getDrawable(resId);
        }
    }

    public static int getResourceId(Resources res, int defaultId) throws Resources.NotFoundException {
        return getResourceIdByTheme(res, defaultId, ThemeManager.getInstance().getCurrentTheme());
    }

    public static int getResourceIdByTheme(Resources resources, int resourceId, Theme theme) {
        String themeSuffix = theme.getSuffix();
        int targetId = resourceId;

        if (themeSuffix != null) {
            int resId = sResourceCache.get(theme, resources, resourceId);
            if (resId != 0) {
                return resId;
            }

            String resourceName = resources.getResourceName(resourceId);
            String targetResourceName = resourceName + theme.getSuffix();
            resId = resources.getIdentifier(targetResourceName, null, null);

            // Support this theme.
            if (resId != 0) {
                sResourceCache.add(theme, resources, resourceId, resId);
                targetId = resId;
            } else {
                sResourceCache.add(theme, resources, resourceId, targetId);
            }
        }

        return targetId;
    }

    public static int getStyle(Resources resources, int styleId) {
        return getStyleByTheme(resources, styleId, ThemeManager.getInstance().getCurrentTheme());
    }

    public static int getStyleByTheme(Resources resources, int styleId, Theme theme) {
        int targetId = styleId;

        if (!Theme.Default.equals(theme)) {
            int resId = sResourceCache.get(theme, resources, styleId);
            if (resId != 0) {
                return resId;
            }

            String resourceName = resources.getResourceName(styleId);
            String targetResourceName = resourceName + theme.name();
            resId = resources.getIdentifier(targetResourceName, null, null);

            // Support this theme.
            if (resId != 0) {
                sResourceCache.add(theme, resources, styleId, resId);
                targetId = resId;
            }
        }
        return targetId;
    }
}
