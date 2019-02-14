package com.jimmysun.guide;

import android.view.View;

/**
 * 圆形高亮区域
 *
 * @author SunQiang
 * @since 2018/4/8
 */

public class CircleHighlightView extends HighlightView {
    private int paddingRadius;

    public CircleHighlightView(View view) {
        super(view);
    }

    public CircleHighlightView(View view, int paddingRadius) {
        super(view);
        this.paddingRadius = paddingRadius;
    }

    public float centerX() {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location[0] + view.getWidth() / 2f;
    }

    public float centerY() {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location[1] + view.getHeight() / 2f - ScreenUtils.getStatusBarHeight(view.getContext());
    }

    public float getRadius() {
        return (float) (Math.sqrt(Math.pow(view.getWidth() / 2.0, 2) + Math.pow(view.getHeight() / 2.0, 2)) + paddingRadius);
    }
}
