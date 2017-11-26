package com.example.harry.appmsg.MyCoordinatorLayout;

import android.app.TabActivity;
import android.os.Bundle;

import com.example.harry.appmsg.R;
//AppCompatActivity
public class SimpleActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
    }
}
