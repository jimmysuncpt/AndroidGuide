package com.jimmysun.guide;

import android.graphics.RectF;
import android.view.View;

/**
 * 矩形高亮区域
 *
 * @author SunQiang
 * @since 2018/4/8
 */

public class RectHighlightView extends HighlightView {
    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;

    public RectHighlightView(View view) {
        super(view);
    }

    public RectHighlightView(View view, int paddingLeft, int paddingTop, int paddingRight, int
            paddingBottom) {
        super(view);
        this.paddingLeft = paddingLeft;
        this.paddingTop = paddingTop;
        this.paddingRight = paddingRight;
        this.paddingBottom = paddingBottom;
    }

    public RectF getRectF() {
        RectF rectF = new RectF();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        rectF.left = location[0] - paddingLeft;
        rectF.top = location[1] - paddingTop - ScreenUtils.getStatusBarHeight(view.getContext());
        rectF.right = location[0] + view.getWidth() + paddingRight;
        rectF.bottom = location[1] + view.getHeight() + paddingBottom - ScreenUtils
                .getStatusBarHeight(view.getContext());
        return rectF;
    }
}
