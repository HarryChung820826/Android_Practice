package com.example.harry.appmsg.MyNestedScrollView;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.harry.appmsg.R;
import com.example.harry.appmsg.Tool;

public class MyNestedScrollViewActivity extends Activity {

    private final String TAG = MyNestedScrollViewActivity.this.getClass().getName();
    private MyListenerNestedScrollView outerScrollView,innerScrollView;
    private RelativeLayout header;
    private LinearLayout header_cover;
    private LinearLayout topPanel;
    private TextView header_tx;

    private VelocityTracker mVelocityTracker;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();//获得VelocityTracker类实例
        }
        mVelocityTracker.addMovement(event);
        //判断当ev事件是MotionEvent.ACTION_UP时：计算速率
        final VelocityTracker velocityTracker = mVelocityTracker;
        // 1000 provides pixels per second
        velocityTracker.computeCurrentVelocity(1, (float)0.01);//设置maxVelocity值为0.1时，速率大于0.01时，显示的速率都是0.01,速率小于0.01时，显示正常
        Log.i("test","velocityTraker"+velocityTracker.getXVelocity());
        velocityTracker.computeCurrentVelocity(1000); //设置units的值为1000，意思为一秒时间内运动了多少个像素
        Log.i("test","velocityTraker"+velocityTracker.getXVelocity());
        return super.onTouchEvent(event);
    }
    @Override
    public void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_nested_scroll_view);

        init();
    }

    private void init(){
        header = (RelativeLayout)findViewById(R.id.header);
        header_cover = (LinearLayout)findViewById(R.id.header_cover);
        topPanel = (LinearLayout)findViewById(R.id.topPanel);

        header_tx = (TextView)findViewById(R.id.header_tx);

        outerScrollView = (MyListenerNestedScrollView)findViewById(R.id.outerScrollView);
        outerScrollView.setTag("outerScrollView");
        innerScrollView = (MyListenerNestedScrollView)findViewById(R.id.innerScrollView);
        innerScrollView.setTag("innerScrollView");
        setListener(outerScrollView);
        setListener(innerScrollView);
    }

    Rect localRect = new Rect();

    /*判斷有無顯示超過一半*/
    private boolean checkHalfVisible(){
        topPanel.getLocalVisibleRect(localRect);

        if(topPanel.getLocalVisibleRect(localRect)) {
            Tool.log(TAG,"checkHalfVisible is visible localRect.top  "+localRect.top +" topPanel.getHeight() "+topPanel.getHeight());
            if (localRect.top <= (topPanel.getHeight() / 2)) {
                /*顯示區塊 超過一半*/
                header_tx.setText("顯示超過一半");
                /*顯示超過一半 滑動至顯示全部 的距離, 滑動沒顯示的高度*/
                scrollPosition(-localRect.top);
                return true;
            } else {
                /*顯示區塊 沒有超過一半*/
                header_tx.setText("顯示沒超過一半");
                /*顯示沒超過一半 滑動至不顯示 的距離, 滑動顯示的高度*/
                scrollPosition((topPanel.getHeight()-localRect.top));
                return false;
            }
        }else{
            Tool.log(TAG,"checkHalfVisible not visible localRect.top  "+localRect.top +" topPanel.getHeight() "+topPanel.getHeight());
            return false;
        }
    }

    /*判斷顯示時 滾動的距離 做 Header的變化處理*/
    private void checkVisibleScrollDistance(){
        topPanel.getLocalVisibleRect(localRect);

        if(topPanel.getLocalVisibleRect(localRect)) {
        /*localRect.top / view.getHeight 算隱藏了多少比例 , ＊255 ＝ 透明度 */
            float alpha = ((float)localRect.top / (float)topPanel.getHeight());
            Tool.log(TAG,"checkVisibleScrollDistance alpha"+alpha + " localRect.top  " + localRect.top  + " topPanel.getHeight() " +topPanel.getHeight() );
            Tool.log(TAG,"checkVisibleScrollDistance alpha  "+(localRect.top / topPanel.getHeight()));
            setHeaderCoverAlpha(alpha);
        }else{
            setHeaderCoverAlpha(1.0f);
        }
    }

    private void setHeaderCoverAlpha(float alpha){
        /*int alpha 0 ~ 255*/
        /*float alpha 0.0 ~ 1.0*/
        header_cover.setAlpha(alpha);
    }

    private void scrollPosition(final int y){
        outerScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                outerScrollView.MyscrollBy(y);
            }
        },50);
    }

    private void setListener(MyListenerNestedScrollView outerScrollView){
        outerScrollView.setOnScrollListener(new MyListenerNestedScrollView.OnScrollListener() {
            @Override
            public void onBottomArrived() {
                //滑倒底部了
            }

            @Override
            public void onScrollStateChanged(MyListenerNestedScrollView view, int scrollState) {
                //滑动状态改变
                Tool.log(TAG,"checkHalfVisible scrollState "+scrollState);
                if(scrollState == SCROLL_STATE_IDLE){
                    checkHalfVisible();
                }
            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                //滑动位置改变
                checkVisibleScrollDistance();
            }
        });
    }
}
