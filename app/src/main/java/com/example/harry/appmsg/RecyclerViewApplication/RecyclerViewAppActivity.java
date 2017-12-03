package com.example.harry.appmsg.RecyclerViewApplication;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.harry.appmsg.R;
import com.example.harry.appmsg.Tool;

import java.util.ArrayList;

public class RecyclerViewAppActivity extends Activity implements View.OnClickListener{

    LinearLayout outer_layout;
    int outerHeight = 0 ;
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
    int offsetY = 0 ;  //紀錄 滑動的 Y
    int fetchHeight = 0 ;//紀錄補空白的高度
    private void init(){
        outer_layout = (LinearLayout) findViewById(R.id.outer_layout);
        outer_layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                outerHeight = outer_layout.getMeasuredHeight();
            }
        });
        add_button = (Button)findViewById(R.id.add_button);
        add_button.setOnClickListener(this);
        remove_button = (Button)findViewById(R.id.remove_button);
        remove_button.setOnClickListener(this);

        adapter = new RecyclerViewAppAdapter(RecyclerViewAppActivity.this,initData());

        recyclerViewApp = (RecyclerView)findViewById(R.id.recyclerViewApp);
        final ScrollSpeedLinearLayoutManger manager = new ScrollSpeedLinearLayoutManger(RecyclerViewAppActivity.this);
        recyclerViewApp.setLayoutManager(manager);
        recyclerViewApp.setAdapter(adapter);
        recyclerViewApp.addItemDecoration(new RecyclerViewAdapterDecoration(RecyclerViewAppActivity.this,R.color.gray66, LinearLayoutManager.VERTICAL));


        recyclerViewApp.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
//                adapter.getRecyclerViewContentHeight();
                Tool.log("onGlobalLayout","onGlobalLayout  addOnGlobalLayoutListener " );
                UnExpand();
            }
        });

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
                offsetY += dy;
                isTop = isScrollToTop();
                if(manager.findLastCompletelyVisibleItemPosition() == allData.size() - 1) {
                    measureProcess();
                }
            }
            public boolean isScrollToTop(){
                if(manager.findFirstCompletelyVisibleItemPosition() == 0){
                    return true;
                }else{
                    return false;
                }
            }
        });
    }



    private ArrayList<String> initData(){
        allData = new ArrayList<String>();
        allData.add(RecyclerViewAppAdapter.SAMPLELAYOUT);
        for(int i = 0 ; i < 5 ; i ++ ){
            if(i> 0 && i < changeCount){
                changeCacheData.add(""+i);
            }
            allData.add(""+i);
        }
        allData.add(RecyclerViewAppAdapter.SAMPLELAYOUT);
        allData.add(RecyclerViewAppAdapter.SAMPLELAYOUT);
        return allData;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_button:
                if(adapter !=null) {
//                    adapter.addItem(changeCacheData);
                    UnExpand();
                }
                break;
            case R.id.remove_button:
                if(adapter !=null){
//                    adapter.removeItem(changeCount);
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

    private void UnExpand(){
        RecyclerView.ViewHolder viewHolder = adapter.getViewHolder(0);
        Tool.log("onGlobalLayout", "UnExpand " + getMustScrollY(((RecyclerViewAppAdapter.MySampleLayoutViewHolder) viewHolder).sample) + "");
//        recyclerViewApp.smoothScrollBy(0, getMustScrollY(((RecyclerViewAppAdapter.MySampleLayoutViewHolder) viewHolder).sample));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 * 滾動到定位 要等GlobalLayoutListener View畫完 約略抓50ms 後 執行 滾動定位
                 * */
                recyclerViewApp.smoothScrollBy(0,600);
            }
        },50);

    }

    /**
     * *利用 onScroll 的 dy 累計 滑動的Y軸 距離  offsetY
     *
     *<判斷至底能不能滾動上去>
     *判斷滾動到底 offsetY < adapter.getViewHolder(0).view.getMeasuredHeight()
     *補最後一項 parames 高度 （adapter.getViewHolder(0).view.getMeasuredHeight() - offsetY）
     *
     *<onGlobalLayout 執行 RecyclerView 內容有變動時>
     *RecyclerView.onGlobalLayout -> 也要判斷
     *(內容高度 - fetchHeight) 小於 (adapter.getViewHolder(0).view.getMeasuredHeight() + outerLayoutHeight)
     *--> 重新計算fetchHeight 的高度
     *
     * */
    boolean isFetchCheck = false;
    public void measureProcess(){

        int overAddContentHeight =  outerHeight+offsetY;
        Tool.log("onGlobalLayout"," onScroll FirstItemHeight " + adapter.getViewHolder(0).itemView.getMeasuredHeight());
        Tool.log("onGlobalLayout"," onScroll outerHeight " + outerHeight);
        Tool.log("onGlobalLayout"," onScroll offsetY " + offsetY);
//        Tool.log("onGlobalLayout"," onScroll offset " + recyclerViewApp.computeVerticalScrollOffset());
//        Tool.log("onGlobalLayout"," onScroll Range " + recyclerViewApp.computeVerticalScrollRange());
        Tool.log("onGlobalLayout"," onScroll Range " + overAddContentHeight);
//        Tool.log("onGlobalLayout", "overHeight" + (recyclerViewApp.computeVerticalScrollRange() - outerHeight) );

//        if ((overAddContentHeight - outerHeight) < adapter.getViewHolder(0).itemView.getMeasuredHeight()) {
//            int differenceDistance = adapter.getViewHolder(0).itemView.getMeasuredHeight() - (overAddContentHeight - outerHeight);
//            Tool.log("onGlobalLayout", "fetchHeight " + differenceDistance);
//            adapter.changeBottomLinearLayoutHeight(differenceDistance+10);
//        }
        if(offsetY < adapter.getViewHolder(0).itemView.getMeasuredHeight()){
            fetchHeight = (adapter.getViewHolder(0).itemView.getMeasuredHeight() - offsetY)+5;
            adapter.changeBottomLinearLayoutHeight(fetchHeight);
            isFetchCheck = true;
        }

    }

}
