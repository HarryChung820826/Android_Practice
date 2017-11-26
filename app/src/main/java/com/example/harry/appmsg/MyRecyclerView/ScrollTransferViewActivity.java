package com.example.harry.appmsg.MyRecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.example.harry.appmsg.MyNestedScrollView.MyListenerNestedScrollView;
import com.example.harry.appmsg.R;
import com.example.harry.appmsg.Tool;

public class ScrollTransferViewActivity extends AppCompatActivity {

    LinearLayout topLayout;
    MyListenerNestedScrollView scrollView;
    Rect localRect = new Rect();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_transfer_view);
        init();
    }

    private void init(){
        topLayout = (LinearLayout)findViewById(R.id.topLayout);
        scrollView = (MyListenerNestedScrollView)findViewById(R.id.scrollView);

        scrollView.setOnScrollListener(new MyListenerNestedScrollView.OnScrollListener() {
            @Override
            public void onBottomArrived() {

            }

            @Override
            public void onScrollStateChanged(MyListenerNestedScrollView view, int scrollState) {

            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                scrollProcess();
            }
        });
    }

    private void scrollProcess(){
        topLayout.getLocalVisibleRect(localRect);
        Tool.log("scrollView","getY "+scrollView.getY());
        Tool.log("scrollView","getHeight "+scrollView.getHeight());
        Tool.log("scrollView","getScrollY "+scrollView.getScrollY());
    }

    private void transferTopLayout(float y){
        topLayout.setTranslationY(y);
    }
}
