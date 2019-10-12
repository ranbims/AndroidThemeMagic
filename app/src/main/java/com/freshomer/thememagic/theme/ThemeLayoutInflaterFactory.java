package com.freshomer.thememagic.theme;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterFactory;

import com.freshomer.thememagic.R;
import com.freshomer.thememagic.theme.attr.ThemeAttr;
import com.freshomer.thememagic.theme.attr.ThemeAttrFactory;
import com.freshomer.thememagic.theme.entity.AndroidThemeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ThemeLayoutInflaterFactory implements LayoutInflaterFactory {

    final String TAG = "ThemeInflaterFactory";

    final int[] attrsArray = new int[] {
            android.R.attr.textColor,   // 16842904
            // Todo(ranbi): This attr is special compared with others. Both view defined in layout
            // and style use define this attr. Better add this attr in MainThemeBase and other base
            // styles.
            android.R.attr.textColorHint, // 16842906
            android.R.attr.background, // 16842964
            android.R.attr.src,     // 16843033;
            android.R.attr.tint,    // 16843041;
            android.R.attr.buttonTint,
            android.R.attr.backgroundTint,
            R.attr.supportTheme,
            R.attr.tint,
            R.attr.backgroundTint,
    };

    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.webkit.",
            "android.app.",
            // LayoutInflater default behavior, mainly for View and ViewGroup
            "android.view."
    };

    public AppCompatDelegate mAppCompatDelegate;

    public ThemeLayoutInflaterFactory(AppCompatDelegate appCompatDelegate) {
        mAppCompatDelegate = appCompatDelegate;
        Arrays.sort(attrsArray);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = mAppCompatDelegate.createView(parent, name, context, attrs);

        LayoutInflater baseInflater = LayoutInflater.from(context);

        if (view == null) {
            if (-1 == name.indexOf('.')) {
                for (String prefix : sClassPrefixList) {
                    try {
                        view = baseInflater.createView(name, prefix, attrs);
                    } catch (Exception ex) {
                        // do nothing.
                    }

                    if (view != null) {
                        break;
                    }
                }
            } else {
                try {
                    view = baseInflater.createView(name, null, attrs);
                } catch (Exception e) {
                    // do nothing.
                }
            }
        }

        if (view != null) {
            if (view instanceof ViewStub) {
                ((ViewStub) view).setLayoutInflater(baseInflater);
            }

            final TypedArray a = context.obtainStyledAttributes(attrs, attrsArray);

            int count = a.getIndexCount();
            if (count > 0) {
                List<ThemeAttr> themeAttrs = new ArrayList<ThemeAttr>();
                boolean supportTheme = true;
                for (int i = 0; i < count; i++) {
                    int index = a.getIndex(i);
                    if (R.attr.supportTheme == attrsArray[index] && !a.getBoolean(index, true)) {
                        supportTheme = false;
                        break;
                    }

                    TypedValue typedValue = new TypedValue();
                    boolean success = a.getValue(index, typedValue);
                    if (success) {
                        ThemeAttr themeAttr =
                                ThemeAttrFactory.createThemeAttr(attrsArray[index], typedValue);
                        if (themeAttr != null) {
                            themeAttrs.add(themeAttr);
                        }
                    }
                }

                if (supportTheme && themeAttrs.size() > 0) {
                    ThemeManager.getInstance().addThemeItemReference(new AndroidThemeView(view, themeAttrs));
                }
            }
            a.recycle();
        }

        return view;
    }
}
