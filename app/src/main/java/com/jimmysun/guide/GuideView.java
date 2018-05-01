package com.jimmysun.guide;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

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

    public GuideView(Context context) {
        super(context);
        init();
    }

    public GuideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        setClickable(true);
        setWillNotDraw(false);
        // 禁用硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    public void setHighlightViews(List<HighlightView> highlightViews) {
        mHighlightViews = highlightViews;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0xb2000000);
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
