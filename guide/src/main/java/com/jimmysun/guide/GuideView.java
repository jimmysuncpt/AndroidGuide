package com.jimmysun.guide;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页视图
 *
 * @author SunQiang
 * @since 2018/3/9
 */

public class GuideView extends RelativeLayout {
    private List<HighlightView> mHighlightViews;

    private Paint mPaint;
    private PorterDuffXfermode mPorterDuffXfermode;
    private int mColor = 0xb2000000;

    public GuideView(Context context) {
        super(context);
        init();
    }

    public GuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GuideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mHighlightViews = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        setClickable(true);
        setWillNotDraw(false);
        // 禁用硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    /**
     * 添加高亮区域
     *
     * @param highlightView 高亮区域
     */
    public void addHighlightView(HighlightView highlightView) {
        mHighlightViews.add(highlightView);
    }

    /**
     * 清除所有高亮区域
     */
    public void clearHighlightViews() {
        mHighlightViews.clear();
    }

    /**
     * 设置透明度，默认0.7
     *
     * @param alpha 透明度
     */
    public void setAlpha(float alpha) {
        if (alpha < 0) {
            alpha = 0;
        }
        if (alpha > 1) {
            alpha = 1;
        }
        int colorAlpha = (int) (0xff * alpha);
        mColor = colorAlpha << 24;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(mColor);
        // 扣除高亮显示部分
        if (mHighlightViews != null) {
            mPaint.setXfermode(mPorterDuffXfermode);
            for (HighlightView highlightView : mHighlightViews) {
                if (highlightView instanceof RectHighlightView) {
                    canvas.drawRect(((RectHighlightView) highlightView).getRectF(), mPaint);
                } else if (highlightView instanceof OvalHighlightView) {
                    canvas.drawOval(((OvalHighlightView) highlightView).getRectF(), mPaint);
                } else if (highlightView instanceof CircleHighlightView) {
                    CircleHighlightView circleHighlightView = (CircleHighlightView) highlightView;
                    canvas.drawCircle(circleHighlightView.centerX(), circleHighlightView.centerY
                            (), circleHighlightView.getRadius(), mPaint);
                }
            }
            mPaint.setXfermode(null);
        }
    }
}
