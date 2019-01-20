package com.jimmysun.guide;

import android.view.View;

/**
 * 相对于目标View绘制的组件
 *
 * @author SunQiang
 * @since 2018/3/12
 */

public class Component {
    private View src;
    private View dest;
    private int xOffset;
    private int yOffset;

    public Component(View src, View dest, int xOffset, int yOffset) {
        this.src = src;
        this.dest = dest;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public View getSrc() {
        return src;
    }

    public View getDest() {
        return dest;
    }

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }
}
