package com.jimmysun.guidedemo;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.jimmysun.guide.GuideManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {
    private TextView mFragmentTextView;

    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        mFragmentTextView = view.findViewById(R.id.tv_fragment);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mFragmentTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                GuideManager guideManager = new GuideManager(getActivity());
                guideManager.addRectHighlightView(mFragmentTextView);
                TextView textView = new TextView(getContext());
                textView.setText("百度理财百度理财百度理财百度理财百度理财百度理财");
                textView.setTextColor(Color.WHITE);
                textView.setWidth(500);
                guideManager.addViewRelativeTo(textView, mFragmentTextView, 0, 120);
                guideManager.show();
            }
        });
    }
}
