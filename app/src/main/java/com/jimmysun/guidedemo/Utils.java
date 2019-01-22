package com.jimmysun.guidedemo;

import android.content.Context;

/**
 * 工具类
 *
 * @author SunQiang
 * @since 2019/1/22
 */
public class Utils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context  context
     * @param dipValue dip或dp大小
     * @return 像素值
     */
    public static int dip2px(Context context, float dipValue) {
        try {
            return (int) (dipValue * context.getResources().getDisplayMetrics().density + 0.5f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
