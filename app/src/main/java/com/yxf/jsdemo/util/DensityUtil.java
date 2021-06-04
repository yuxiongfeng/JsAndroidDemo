package com.yxf.jsdemo.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @Description:
 * @Author: yxf
 * @CreateDate: 2021/5/31 17:16
 * @UpdateUser: yxf
 * @UpdateDate: 2021/5/31 17:16
 */
public class DensityUtil {
    public static final int sp2px(Context context, int sp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float scaledDensity = displayMetrics.scaledDensity;
        return (int) (sp * scaledDensity + 0.5);
    }

    public static final int dp2px(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float density = displayMetrics.density;
        return (int) (density * dp + 0.5);
    }
}
