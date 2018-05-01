package com.jimmysun.guide.demo;

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
import com.jimmysun.guide.R;

public class MainActivity extends AppCompatActivity {
    private TextView mHelloTextView;
    private Button mGuideButton, mListButton, mFragmentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelloTextView = (TextView) findViewById(R.id.tv_hello);
        mGuideButton = (Button) findViewById(R.id.btn_guide);
        mListButton = (Button) findViewById(R.id.btn_list);
        mFragmentButton = (Button) findViewById(R.id.btn_fragment);
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
                mHelloTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
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
        guideManager.addRectHighlightView(mHelloTextView, 80, 40, 80, 40);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.right_arrow);
        guideManager.addViewRelativeTo(imageView, mHelloTextView, -20, 140);
        TextView textView = new TextView(this);
        textView.setText("这是一个TextView");
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(16);
        guideManager.addViewRelativeTo(textView, mHelloTextView, -100, 360);
        guideManager.setCancelable(false);
        guideManager.show();
        guideManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guideManager.clear();
                guideManager.addOvalHighlightView(mGuideButton, 20, 20, 20, 20);
                LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout
                        .layout_info, null);
                guideManager.addViewRelativeTo(linearLayout, mGuideButton, -140, 200);
                guideManager.updateView();
                guideManager.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        guideManager.clear();
                        guideManager.addCircleHighlightView(mListButton, 5);

                        TextView textView = new TextView(MainActivity.this);
                        textView.setText("我知道了");
                        textView.setTextColor(Color.WHITE);
                        textView.setTextSize(16);
                        textView.setPadding(20, 10, 20, 10);
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
                        layoutParams.bottomMargin = 100;
                        guideManager.addView(textView, layoutParams);

                        guideManager.setClickDismiss(false);
                        guideManager.updateView();
                    }
                });
            }
        });
    }
}
