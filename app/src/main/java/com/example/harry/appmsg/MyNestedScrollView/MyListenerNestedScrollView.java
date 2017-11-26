package com.example.harry.appmsg.MyNestedScrollView;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Scroller;

import com.example.harry.appmsg.Tool;

/**
 * Created by Harry on 2017/9/24.
 */
public class MyListenerNestedScrollView extends NestedScrollView{

    private static String TAG = MyListenerNestedScrollView.class.getSimpleName();
    // 检查ScrollView的最终状态
    private static final int CHECK_STATE = 0;
    // 外部设置的监听方法
    private OnScrollListener onScrollListener;
    // 是否在触摸状态
    private static boolean inTouch = false;
    // 上次滑动的最后位置
    private int lastT = 0;

    private Scroller mScroller;

    public MyListenerNestedScrollView(Context context) {
        super(context);
    }

    public MyListenerNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    public MyListenerNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
    }

    public void setTag(String tag){
        this.TAG += ","+tag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                inTouch = true;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                inTouch = false;

                lastT = getScrollY();
                checkStateHandler.removeMessages(CHECK_STATE);// 确保只在最后一次做这个check
                checkStateHandler.sendEmptyMessageDelayed(CHECK_STATE, 5);// 5毫秒检查一下

                break;
            default:
                break;
        }
        Log.d(TAG, "inTouch = " + inTouch);
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener == null) {
            return;
        }

        Log.d(TAG, "t = " + t + ", oldt = " + oldt +" tinTouch "+inTouch);

        if (inTouch) {
            if (t != oldt) {
                // 有手指触摸，并且位置有滚动
                Log.i(TAG, "SCROLL_STATE_TOUCH_SCROLL");
                onScrollListener.onScrollStateChanged(this,
                        OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
            }
        } else {
            if (t != oldt) {
                // 没有手指触摸，并且位置有滚动，就可以简单的认为是在fling
                Log.w(TAG, "SCROLL_STATE_FLING");
                onScrollListener.onScrollStateChanged(this,
                        OnScrollListener.SCROLL_STATE_FLING);
                // 记住上次滑动的最后位置
                lastT = t;
                checkStateHandler.removeMessages(CHECK_STATE);// 确保只在最后一次做这个check
                checkStateHandler.sendEmptyMessageDelayed(CHECK_STATE, 5);// 5毫秒检查一下
            }
        }
        onScrollListener.onScrollChanged(l, t, oldl, oldt);
    }

    private Handler checkStateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (lastT == getScrollY()) {
                // 如果上次的位置和当前的位置相同，可认为是在空闲状态
                Log.e(TAG, "SCROLL_STATE_IDLE" + inTouch);

                if(onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(MyListenerNestedScrollView.this,
                            OnScrollListener.SCROLL_STATE_IDLE);
                    // from http://blog.chinaunix.net/uid-20782417-id-1645164.html
                    if (getScrollY() + getHeight() >= computeVerticalScrollRange()) {
                        onScrollListener.onBottomArrived();
                    } else {
                        Log.d(TAG, "没有到最下方");
                    }
                }else{
                    Log.d(TAG, "onScrollListener is null");
                }
            }

            Log.d(TAG, "onScrollListener is lastT "+lastT  + "getScrollY() "+ getScrollY());
        }
    };

    /**
     * 设置滚动监听事件
     *
     * @param onScrollListener
     *            {@link OnScrollListener} 滚动监听事件（注意类的不同，虽然名字相同）
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    /**
     * 滚动监听事件
     *
     * @author 拉风的道长
     *
     */
    public interface OnScrollListener {
        /**
         * The view is not scrolling. Note navigating the list using the
         * trackball counts as being in the idle state since these transitions
         * are not animated.
         */
        public static int SCROLL_STATE_IDLE = 0;

        /**
         * The user is scrolling using touch, and their finger is still on the
         * screen
         */
        public static int SCROLL_STATE_TOUCH_SCROLL = 1;

        /**
         * The user had previously been scrolling using touch and had performed
         * a fling. The animation is now coasting to a stop
         */
        public static int SCROLL_STATE_FLING = 2;

        /**
         * 滑动到底部回调
         */
        public void onBottomArrived();

        /**
         * 滑动状态回调
         *
         * @param view
         *            当前的scrollView
         * @param scrollState
         *            当前的状态
         */
        public void onScrollStateChanged(MyListenerNestedScrollView view,
                                         int scrollState);

        /**
         * 滑动位置回调
         *
         * @param l
         * @param t
         * @param oldl
         * @param oldt
         */
        public void onScrollChanged(int l, int t, int oldl, int oldt);
    }
    /*滾動多少Ｙ距離*/
    public void MyscrollBy( int y){
        this.smoothScrollBy(0,y);
        Tool.log(TAG,"checkHalfVisible is visible localRect.top"+y);

//        mScroller.fling(0,getScrollY(),0,50,0,0,0,y);
        //参数解释：1、x轴起始位置；2、y轴起始位置；3、x轴的偏移量；4、y轴的偏移量；5、完成这个滑动需要的时间
//        mScroller.startScroll(0, getScrollY(), 0, y, 50);
//        invalidate();
    }

//    @Override
//    public void computeScroll() {
//        //computeScrollOffset()返回true，代表滑动还未结束；false 则已结束。
//        if (mScroller.computeScrollOffset()) {
//            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//            postInvalidate();
//        }
//    }


    public void doFling(int startX, int startY, int velocityX, int velocityY,
                        int minX, int maxX, int minY, int maxY) {
        mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
        invalidate();
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        // Keep track of velocity using our own Overscoller instance
//        mScroller.fling(getScrollX(), getScrollY(), 0, velocityY, 0, 0, 0, 0);

//        mScroller.fling(getScrollX(), getScrollY(), 0, velocityY, 0, 0, 0,
//                Math.max(0, bottom - height), 0, height/2);
    }
}
