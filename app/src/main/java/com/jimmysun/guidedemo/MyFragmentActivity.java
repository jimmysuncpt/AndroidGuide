package com.jimmysun.guidedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MyFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fragment);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout, new MyFragment()).commit();
    }
}
