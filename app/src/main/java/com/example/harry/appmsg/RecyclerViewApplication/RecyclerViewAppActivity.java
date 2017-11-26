package com.example.harry.appmsg.RecyclerViewApplication;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.harry.appmsg.R;
import com.example.harry.appmsg.Tool;

import java.util.ArrayList;

public class RecyclerViewAppActivity extends Activity implements View.OnClickListener{

    RecyclerView recyclerViewApp;
    Button add_button,remove_button;
    RecyclerViewAppAdapter adapter;

    final int changeCount = 8;
    ArrayList<String> allData = new ArrayList<String>();
    ArrayList<String> changeCacheData = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_app);
        init();
    }

    private void init(){
        add_button = (Button)findViewById(R.id.add_button);
        add_button.setOnClickListener(this);
        remove_button = (Button)findViewById(R.id.remove_button);
        remove_button.setOnClickListener(this);

        adapter = new RecyclerViewAppAdapter(RecyclerViewAppActivity.this,initData());

        recyclerViewApp = (RecyclerView)findViewById(R.id.recyclerViewApp);
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(RecyclerViewAppActivity.this);
        final ScrollSpeedLinearLayoutManger manager = new ScrollSpeedLinearLayoutManger(RecyclerViewAppActivity.this);
        recyclerViewApp.setLayoutManager(manager);
        recyclerViewApp.setAdapter(adapter);
        recyclerViewApp.addItemDecoration(new RecyclerViewAdapterDecoration(RecyclerViewAppActivity.this,R.color.gray66, LinearLayoutManager.VERTICAL));


        recyclerViewApp.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isIDLE = true;
            boolean isHalf = false;
            boolean isTop = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Tool.log("onScrollStateChanged"," newState " + newState);
                Tool.log("onScrollStateChanged"," pos " + manager.findFirstVisibleItemPosition() + " isIDLE " +isIDLE + " isHalf "+isHalf + " isTop "+isTop);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //停止滾動狀態
                    isIDLE = true;
                    if(manager.findFirstVisibleItemPosition() == 0 && isIDLE){
                        RecyclerView.ViewHolder viewHolder = adapter.getViewHolder(manager.findFirstVisibleItemPosition());
                        isHalf = checkHalfVisible(((RecyclerViewAppAdapter.MySampleLayoutViewHolder)viewHolder).sample);
                        if(isHalf && !isTop){
                            recyclerViewApp.smoothScrollToPosition(0);
                            Tool.log("onScrollStateChanged","超過一半");
                        }else{
//                            Tool.log("onScrollStateChanged","沒有超過一半");
//                            recyclerViewApp.smoothScrollToPosition(20);
//                            recyclerViewApp.smoothScrollBy(0,getMustScrollY(((RecyclerViewAppAdapter.MySampleLayoutViewHolder)viewHolder).sample));
                        }

                        if(!isHalf && !(recyclerView.getScrollY() == getMustScrollY(((RecyclerViewAppAdapter.MySampleLayoutViewHolder)viewHolder).sample))){
                            Tool.log("onScrollStateChanged","沒有超過一半");
                            recyclerViewApp.smoothScrollBy(0,getMustScrollY(((RecyclerViewAppAdapter.MySampleLayoutViewHolder)viewHolder).sample));
                        }
                    }
                }else{
                    isIDLE = false;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isTop = isScrollToTop();
//                if(manager.findFirstVisibleItemPosition() == 0 && isIDLE){
//                    Tool.log("onScrolled"," pos " + manager.findFirstVisibleItemPosition() + "  " +isIDLE);
//                    RecyclerView.ViewHolder viewHolder = adapter.getViewHolder(manager.findFirstVisibleItemPosition());
//                    checkHalfVisible(((RecyclerViewAppAdapter.MySampleLayoutViewHolder)viewHolder).sample);
//                }
            }
            public boolean isScrollToTop(){
                if(manager.findFirstCompletelyVisibleItemPosition() == 0){
                    return true;
                }else{
                    return false;
                }
//                if(manager.findViewByPosition(0).getTop() == 0 && manager.findFirstVisibleItemPosition() == 0){
//                    return true;
//                }else{
//                    return false;
//                }
            }
        });
    }



    private ArrayList<String> initData(){
        allData = new ArrayList<String>();
        allData.add(RecyclerViewAppAdapter.SAMPLELAYOUT);
        for(int i = 0 ; i < 80 ; i ++ ){
            if(i> 0 && i < changeCount){
                changeCacheData.add(""+i);
            }
            allData.add(""+i);
        }
        return allData;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_button:
                if(adapter !=null) {
                    adapter.addItem(changeCacheData);
                }
                break;
            case R.id.remove_button:
                if(adapter !=null){
                    adapter.removeItem(changeCount);
                }
                break;
        }
    }


    Rect localRect = new Rect();
    /*判斷有無顯示超過一半*/
    private boolean checkHalfVisible(LinearLayout sampleLayout){
        sampleLayout.getLocalVisibleRect(localRect);

        if(sampleLayout.getLocalVisibleRect(localRect)) {
//            Tool.log(TAG,"checkHalfVisible is visible localRect.top  "+localRect.top +" topPanel.getHeight() "+topPanel.getHeight());
            if (localRect.top <= (sampleLayout.getHeight() / 2)) {
                /*顯示區塊 超過一半*/
//                Toast.makeText(RecyclerViewAppActivity.this,"顯示超過一半",Toast.LENGTH_SHORT).show();
                /*顯示超過一半 滑動至顯示全部 的距離, 滑動沒顯示的高度*/
                return true;
            } else {
                /*顯示區塊 沒有超過一半*/
                Toast.makeText(RecyclerViewAppActivity.this,"顯示沒超過一半",Toast.LENGTH_SHORT).show();
                /*顯示沒超過一半 滑動至不顯示 的距離, 滑動顯示的高度*/
                return false;
            }
        }else{
//            Tool.log(TAG,"checkHalfVisible not visible localRect.top  "+localRect.top +" topPanel.getHeight() "+topPanel.getHeight());
            return false;
        }
    }

    private int getMustScrollY(LinearLayout sampleLayout){
        Rect localRect = new Rect();
        sampleLayout.getLocalVisibleRect(localRect);
        return (sampleLayout.getHeight()-localRect.top);
    }
}
