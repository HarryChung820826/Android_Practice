package com.example.harry.appmsg;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.harry.appmsg.MyCoordinatorLayout.SimpleActivity;
import com.example.harry.appmsg.MyNestedScrollView.MyNestedScrollViewActivity;
import com.example.harry.appmsg.MyRecyclerView.MyRecyclerViewActivity;
import com.example.harry.appmsg.MyRecyclerView.ScrollTransferViewActivity;
import com.example.harry.appmsg.MyView.EventTextView;
import com.example.harry.appmsg.RecyclerViewApplication.RecyclerViewAppActivity;

public class MainActivity extends BaseActivity implements View.OnTouchListener , View.OnClickListener{

    private final String TAG = "MainActivity";

    LinearLayout outerLayout,eventTextView_Layout;
    EventTextView eventTextView;

    Button btn,btn_intent,btn_viewpager,btn_listview,btn_gridview,btn_recyclerView
            ,btn_NestedScrollView,btn_ScrollTransferView,btn_recyclerViewApp
            ,btn_simpleApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Touch Event Process*/
        outerLayout = (LinearLayout)findViewById(R.id.outerLayout);
        eventTextView_Layout = (LinearLayout)findViewById(R.id.eventTextView_Layout);
        eventTextView = (EventTextView)findViewById(R.id.eventTextView);
        eventTest();

        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showAppMsg();
//                showWindow();
//                showNotification();
//                showNotification();
                /**/
                showNotification_Compat();
            }
        });


        btn_intent = (Button)findViewById(R.id.btn_intent);
        btn_intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TwoPageActivity.class);
                startActivity(intent);
            }
        });

        btn_viewpager = (Button)findViewById(R.id.btn_viewpager);
        btn_viewpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MyViewPagerActivity.class);
                startActivity(intent);
            }
        });

        btn_listview = (Button)findViewById(R.id.btn_listview);
        btn_listview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MyListView.class);
                startActivity(intent);
            }
        });


        btn_gridview = (Button)findViewById(R.id.btn_gridview);
        btn_gridview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MyGrideView.class);
                startActivity(intent);
            }
        });


        btn_recyclerView = (Button)findViewById(R.id.btn_recyclerView);
        btn_recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MyRecyclerViewActivity.class);
                startActivity(intent);
            }
        });


        btn_NestedScrollView = (Button)findViewById(R.id.btn_NestedScrollView);
        btn_NestedScrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MyNestedScrollViewActivity.class);
                startActivity(intent);
            }
        });

        btn_ScrollTransferView = (Button)findViewById(R.id.btn_ScrollTransferView);
        btn_ScrollTransferView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ScrollTransferViewActivity.class);
                startActivity(intent);
            }
        });

        btn_recyclerViewApp = (Button)findViewById(R.id.btn_recyclerViewApp);
        btn_recyclerViewApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RecyclerViewAppActivity.class);
                startActivity(intent);
            }
        });

        btn_simpleApp = (Button)findViewById(R.id.btn_simpleApp);
        btn_simpleApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SimpleActivity.class);
                startActivity(intent);
            }
        });
    }

    private void eventTest(){
        outerLayout.setOnTouchListener(this);
        eventTextView_Layout.setOnTouchListener(this);
        eventTextView.setOnClickListener(this);
        eventTextView.setOnTouchListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Tool.showEventLog(TAG,"dispatchTouchEvent",ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Tool.showEventLog(TAG,"onTouchEvent",event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Tool.showEventLog(TAG,"onTouch",motionEvent);
        return false;
    }

    @Override
    public void onClick(View view) {
        Tool.log("showEventLog_"+TAG,"EventTextView_onClick");
    }
}
