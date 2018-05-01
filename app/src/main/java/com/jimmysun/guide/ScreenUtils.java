package com.jimmysun.guide;

import android.content.Context;

/**
 * 获取屏幕参数工具
 *
 * @author SunQiang
 * @since 2018/4/4
 */

public class ScreenUtils {
    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return 状态栏高度
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
}
