package com.jimmysun.guide;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页管理类
 *
 * @author SunQiang
 * @since 2018/3/12
 */

public class GuideManager {
    private List<HighlightView> mHighlightViews;
    private List<Component> mComponents;

    private GuideView mGuideView;
    private Dialog mDialog;

    private boolean mIsClickDismiss;
    private View.OnClickListener mOnClickListener;

    private int mStatusHeight;

    public GuideManager(Context context) {
        mHighlightViews = new ArrayList<>();
        mComponents = new ArrayList<>();
        mDialog = new Dialog(context, R.style.guide_dialog);
        mGuideView = new GuideView(context);
        mIsClickDismiss = true;
        mStatusHeight = ScreenUtils.getStatusHeight(context);
    }

    public GuideManager addRectHighlightView(View view) {
        mHighlightViews.add(new RectHighlightView(view));
        return this;
    }

    public GuideManager addRectHighlightView(View view, int paddingLeft, int paddingTop, int
            paddingRight, int paddingBottom) {
        mHighlightViews.add(new RectHighlightView(view, paddingLeft, paddingTop, paddingRight,
                paddingBottom));
        return this;
    }

    public GuideManager addOvalHighlightView(View view) {
        mHighlightViews.add(new OvalHighlightView(view));
        return this;
    }

    public GuideManager addOvalHighlightView(View view, int paddingLeft, int paddingTop, int
            paddingRight, int paddingBottom) {
        mHighlightViews.add(new OvalHighlightView(view, paddingLeft, paddingTop, paddingRight,
                paddingBottom));
        return this;
    }

    public GuideManager addCircleHighlightView(View view) {
        mHighlightViews.add(new CircleHighlightView(view));
        return this;
    }

    public GuideManager addCircleHighlightView(View view, int paddingRadius) {
        mHighlightViews.add(new CircleHighlightView(view, paddingRadius));
        return this;
    }

    public GuideManager addViewRelativeTo(View view, View relativeTo, int xOffset, int yOffset) {
        mComponents.add(new Component(view, relativeTo, xOffset, yOffset));
        return this;
    }

    public GuideManager addView(View view, ViewGroup.LayoutParams params) {
        mGuideView.addView(view, params);
        return this;
    }

    public GuideManager setClickDismiss(boolean clickDismiss) {
        mIsClickDismiss = clickDismiss;
        return this;
    }

    public GuideManager setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
        return this;
    }

    public GuideManager setCancelable(boolean cancelable) {
        mDialog.setCancelable(cancelable);
        return this;
    }

    public void show() {
        updateView();
        mDialog.setContentView(mGuideView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (mDialog.getWindow() != null) {
            WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mDialog.getWindow().setAttributes(layoutParams);
        }
        mDialog.show();
    }

    public void updateView() {
        mGuideView.setHighlightViews(mHighlightViews);
        int[] location = new int[2];
        for (Component component : mComponents) {
            component.getDest().getLocationOnScreen(location);
            int left = location[0] + component.getXOffset();
            int top = location[1] + component.getYOffset() - mStatusHeight;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = left;
            layoutParams.topMargin = top;
            mGuideView.addView(component.getSrc(), layoutParams);
        }
        mGuideView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener == null) {
                    if (mIsClickDismiss) {
                        dismiss();
                    }
                } else {
                    mOnClickListener.onClick(v);
                }
            }
        });
    }

    public void clear() {
        mHighlightViews = new ArrayList<>();
        mComponents = new ArrayList<>();
        mGuideView.removeAllViewsInLayout();
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            try {
                mDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
