package com.jimmysun.guide;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * 引导页管理类
 *
 * @author SunQiang
 * @since 2018/3/12
 */

public class GuideManager {
    /**
     * 该视图的右边与另一个视图的左边对齐
     */
    public static final int LEFT_OF = 1;
    /**
     * 该视图的左边与另一个视图的右边对齐
     */
    public static final int RIGHT_OF = 1 << 1;
    /**
     * 该视图的底边与另一个视图的顶边对齐
     */
    public static final int ABOVE = 1 << 2;
    /**
     * 该视图的顶边与另一个视图的底边对齐
     */
    public static final int BELOW = 1 << 3;
    /**
     * 该视图的左边与另一个视图的左边对齐（默认）
     */
    public static final int ALIGN_LEFT = 1 << 4;
    /**
     * 该视图的顶边与另一个视图的顶边对齐（默认）
     */
    public static final int ALIGN_TOP = 1 << 5;
    /**
     * 该视图的右边与另一个视图的右边对齐
     */
    public static final int ALIGN_RIGHT = 1 << 6;
    /**
     * 该视图的底边与另一个视图的底边对齐
     */
    public static final int ALIGN_BOTTOM = 1 << 7;

    private GuideView mGuideView;
    private Dialog mDialog;

    public GuideManager(Context context) {
        mGuideView = new GuideView(context);
        mDialog = new Dialog(context, R.style.guide_dialog);
        mDialog.setContentView(mGuideView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (mDialog.getWindow() != null) {
            WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mDialog.getWindow().setAttributes(layoutParams);
        }
    }

    /**
     * 以矩形的方式高亮显示视图
     *
     * @param view 要高亮显示的视图
     * @return GuideManager
     */
    public GuideManager highlightViewInRect(View view) {
        if (view != null) {
            mGuideView.addHighlightView(new RectHighlightView(view));
        }
        return this;
    }

    /**
     * 以矩形的方式高亮显示视图
     *
     * @param view          要高亮显示的视图
     * @param paddingLeft   左间距
     * @param paddingTop    上间距
     * @param paddingRight  右间距
     * @param paddingBottom 下间距
     * @return GuideManager
     */
    public GuideManager highlightViewInRect(View view, int paddingLeft, int paddingTop, int
            paddingRight, int paddingBottom) {
        if (view != null) {
            mGuideView.addHighlightView(new RectHighlightView(view, paddingLeft, paddingTop,
                    paddingRight, paddingBottom));
        }
        return this;
    }

    /**
     * 以椭圆形的方式高亮显示视图
     *
     * @param view 要高亮显示的视图
     * @return GuideManager
     */
    public GuideManager highlightViewInOval(View view) {
        if (view != null) {
            mGuideView.addHighlightView(new OvalHighlightView(view));
        }
        return this;
    }

    /**
     * 以椭圆形的方式高亮显示视图
     *
     * @param view          要高亮显示的视图
     * @param paddingLeft   左间距
     * @param paddingTop    上间距
     * @param paddingRight  右间距
     * @param paddingBottom 下间距
     * @return GuideManager
     */
    public GuideManager highlightViewInOval(View view, int paddingLeft, int paddingTop, int
            paddingRight, int paddingBottom) {
        if (view != null) {
            mGuideView.addHighlightView(new OvalHighlightView(view, paddingLeft, paddingTop,
                    paddingRight, paddingBottom));
        }
        return this;
    }

    /**
     * 以圆形的方式高亮显示视图
     *
     * @param view 要高亮显示的视图
     * @return GuideManager
     */
    public GuideManager highlightViewInCircle(View view) {
        if (view != null) {
            mGuideView.addHighlightView(new CircleHighlightView(view));
        }
        return this;
    }

    /**
     * 以圆形的方式高亮显示视图
     *
     * @param view          要高亮显示的视图
     * @param paddingRadius 间距半径
     * @return GuideManager
     */
    public GuideManager highlightViewInCircle(View view, int paddingRadius) {
        if (view != null) {
            mGuideView.addHighlightView(new CircleHighlightView(view, paddingRadius));
        }
        return this;
    }

    /**
     * 相对于某个视图添加一个视图
     *
     * @param view       要添加的视图
     * @param relativeTo 相对的视图
     * @param alignType  对齐方式
     * @param xMargin    横向距离（px）
     * @param yMargin    纵向距离（px）
     * @return GuideManager
     */
    public GuideManager addViewRelativeTo(View view, View relativeTo, int alignType, int xMargin,
                                          int yMargin) {
        if (view != null && relativeTo != null) {
            int measureSpec = View.MeasureSpec.makeMeasureSpec((1 << 30) - 1,
                    View.MeasureSpec.AT_MOST);
            view.measure(measureSpec, measureSpec);
            int[] location = new int[2];
            relativeTo.getLocationOnScreen(location);
            int left = location[0];
            int top = location[1] - ScreenUtils.getStatusBarHeight(view.getContext());
            if ((alignType & LEFT_OF) > 0) {
                left -= view.getMeasuredWidth();
                xMargin = -xMargin;
            } else if ((alignType & RIGHT_OF) > 0) {
                left += relativeTo.getMeasuredWidth();
            } else if ((alignType & ALIGN_RIGHT) > 0) {
                left += relativeTo.getMeasuredWidth() - view.getMeasuredWidth();
                xMargin = -xMargin;
            }
            if ((alignType & ABOVE) > 0) {
                top -= view.getMeasuredHeight();
                yMargin = -yMargin;
            } else if ((alignType & BELOW) > 0) {
                top += relativeTo.getMeasuredHeight();
            } else if ((alignType & ALIGN_BOTTOM) > 0) {
                top += relativeTo.getMeasuredHeight() - view.getMeasuredHeight();
                yMargin = -yMargin;
            }
            ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = left + xMargin;
            layoutParams.topMargin = top + yMargin;
            mGuideView.addView(view, layoutParams);
        }
        return this;
    }

    /**
     * 以指定的布局参数添加一个视图
     *
     * @param view   要添加的视图
     * @param params 设置给视图的布局参数
     * @return GuideManager
     */
    public GuideManager addView(View view, ViewGroup.LayoutParams params) {
        if (view != null && params != null) {
            mGuideView.addView(view, params);
        }
        return this;
    }

    /**
     * 更新视图的布局参数
     *
     * @param view   要更新的视图
     * @param params 布局参数
     * @return GuideManager
     */
    public GuideManager updateViewLayout(View view, ViewGroup.LayoutParams params) {
        if (view != null && params != null) {
            mGuideView.updateViewLayout(view, params);
        }
        return this;
    }

    /**
     * 删除一个视图
     *
     * @param view 要删除的视图
     * @return GuideManager
     */
    public GuideManager removeView(View view) {
        if (view != null) {
            mGuideView.removeView(view);
        }
        return this;
    }

    /**
     * 清除所有高亮的区域和添加的视图
     *
     * @return GuideManager
     */
    public GuideManager clear() {
        mGuideView.clearHighlightViews();
        mGuideView.removeAllViews();
        return this;
    }

    /**
     * 设置是否可以按返回键消失
     *
     * @param cancelable 是否可以按返回键消失
     * @return GuideManager
     */
    public GuideManager setCancelable(boolean cancelable) {
        mDialog.setCancelable(cancelable);
        return this;
    }

    /**
     * 设置是否可以点击消失
     *
     * @param cancel 是否可以点击消失
     * @return GuideManager
     */
    public GuideManager setCanceledOnTouch(boolean cancel) {
        if (cancel) {
            mGuideView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        return this;
    }

    /**
     * 设置点击事件监听器
     *
     * @param onClickListener 点击事件监听器
     * @return GuideManager
     */
    public GuideManager setOnClickListener(View.OnClickListener onClickListener) {
        mGuideView.setOnClickListener(onClickListener);
        return this;
    }

    /**
     * 更新布局
     *
     * @return GuideManager
     */
    public GuideManager requestLayout() {
        mGuideView.requestLayout();
        return this;
    }

    /**
     * 设置透明度，0~1，默认0.7
     *
     * @param alpha 透明度，0~1
     * @return GuideManager
     */
    public GuideManager setAlpha(float alpha) {
        mGuideView.setAlpha(alpha);
        return this;
    }

    /**
     * 是否正在显示
     *
     * @return 是否正在显示
     */
    public boolean isShowing() {
        return mDialog.isShowing();
    }

    /**
     * 显示
     */
    public void show() {
        if (!isShowing()) {
            mDialog.show();
        }
    }

    /**
     * 消失
     */
    public void dismiss() {
        if (mDialog.isShowing()) {
            try {
                mDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
