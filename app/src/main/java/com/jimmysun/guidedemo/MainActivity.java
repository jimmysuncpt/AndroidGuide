package com.jimmysun.guidedemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jimmysun.guide.GuideManager;

public class MainActivity extends AppCompatActivity {
    private TextView mHelloTextView;
    private Button mGuideButton, mListButton, mFragmentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelloTextView = findViewById(R.id.tv_hello);
        mGuideButton = findViewById(R.id.btn_guide);
        mListButton = findViewById(R.id.btn_list);
        mFragmentButton = findViewById(R.id.btn_fragment);
        mGuideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGuide();
            }
        });
        mHelloTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHelloTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                showGuide();
            }
        });
        mListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RecyclerActivity.class));
            }
        });
        mFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MyFragmentActivity.class));
            }
        });
    }

    private void showGuide() {
        final GuideManager guideManager = new GuideManager(this);
        guideManager.highlightViewInRect(mHelloTextView, Utils.dip2px(this, 20),
                Utils.dip2px(this, 10), Utils.dip2px(this, 20), Utils.dip2px(this, 10));

        ImageView imageView = new ImageView(this);
        imageView.setId(View.generateViewId());
        imageView.setImageResource(R.drawable.right_arrow);
        guideManager.addViewRelativeTo(imageView, mHelloTextView, GuideManager.BELOW,
                Utils.dip2px(this, -10), Utils.dip2px(this, 20));
        TextView textView = new TextView(this);
        textView.setText("这是一个TextView");
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(16);
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.BELOW, imageView.getId());
        layoutParams.addRule(RelativeLayout.ALIGN_LEFT, imageView.getId());
        layoutParams.topMargin = Utils.dip2px(this, 10);
        layoutParams.leftMargin = Utils.dip2px(this, -20);
        guideManager.addView(textView, layoutParams);
        guideManager.setCancelable(false);
        guideManager.show();
        guideManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guideManager.clear();
                guideManager.highlightViewInOval(mGuideButton, Utils.dip2px(MainActivity.this,
                        10), Utils.dip2px(MainActivity.this, 10), Utils.dip2px(MainActivity.this,
                        10), Utils.dip2px(MainActivity.this, 10));
                LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout
                        .layout_info, null);
                guideManager.addViewRelativeTo(linearLayout, mGuideButton,
                        GuideManager.BELOW | GuideManager.ALIGN_RIGHT,
                        Utils.dip2px(MainActivity.this, 20), Utils.dip2px(MainActivity.this, 20));
                guideManager.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        guideManager.clear();
                        guideManager.highlightViewInCircle(mListButton,
                                Utils.dip2px(MainActivity.this, 2.5f));
                        TextView textView = new TextView(MainActivity.this);
                        textView.setText("我知道了");
                        textView.setTextColor(Color.WHITE);
                        textView.setTextSize(16);
                        textView.setPadding(Utils.dip2px(MainActivity.this, 10),
                                Utils.dip2px(MainActivity.this, 5),
                                Utils.dip2px(MainActivity.this, 10),
                                Utils.dip2px(MainActivity.this, 5));
                        textView.setBackgroundResource(R.drawable.solid_white_bg);
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                guideManager.dismiss();
                            }
                        });
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout
                                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                                .LayoutParams.WRAP_CONTENT);
                        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                        layoutParams.bottomMargin = Utils.dip2px(MainActivity.this, 50);
                        guideManager.addView(textView, layoutParams);

                        guideManager.setCanceledOnTouch(false);
                    }
                });
            }
        });
    }
}
