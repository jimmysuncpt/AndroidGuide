package com.jimmysun.guidedemo;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jimmysun.guide.GuideManager;

/**
 *
 *
 * @author SunQiang
 * @since 2018/3/13
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Activity mActivity;

    public MyAdapter(Activity activity) {
        mActivity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.recycler_item,
                parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.mTextView.setText("位置：" + position);
        if (position == 5) {
            holder.mLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                    .OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    holder.mLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    showGuide(holder);
                }
            });
        }
        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGuide(holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    private void showGuide(MyViewHolder holder) {
        GuideManager guideManager = new GuideManager(mActivity);
        guideManager.addRectHighlightView(holder.mLayout);
        TextView textView = new TextView(mActivity);
        textView.setText("↓这是高亮区域");
        textView.setTextColor(Color.WHITE);
        guideManager.addViewRelativeTo(textView, holder.mLayout, 5, -60);
        guideManager.show();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        Button mButton;
        LinearLayout mLayout;

        MyViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text_view);
            mButton = itemView.findViewById(R.id.button);
            mLayout = itemView.findViewById(R.id.layout_item);
        }
    }
}
