package com.example.harry.appmsg.MyView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.harry.appmsg.Tool;

/**
 * Created by Harry on 2017/10/10.
 */

/**
 * 事件傳遞 步驟
 * 分發(dispatch) ；攔截(Intercept) ； 消費(Consume)
 * Activity => dispatchTouchEvent , onTouchEvent
 * ViewGroup => dispatchTouchEvent , onInterceptTouchEvent , onTouchEvent
 * View => dispatchTouchEvent , onTouchEvent
 * */
public class EventTextView extends TextView{
    private final String TAG = "EventTextView";
    // 屬於 View => dispatchTouchEvent , onTouchEvent

    public EventTextView(Context context) {
        super(context);
    }

    public EventTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Tool.showEventLog(TAG,"dispatchTouchEvent",event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Tool.showEventLog(TAG,"onTouchEvent",event);
        return super.onTouchEvent(event);
    }
}
