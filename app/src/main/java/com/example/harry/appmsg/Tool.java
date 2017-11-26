package com.example.harry.appmsg;

import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Harry on 2017/8/27.
 */
public class Tool {
    public static void log(String key,String content){
        Log.d(key,content);
    }

    public static void showEventLog(String TAG,String content, MotionEvent event){
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                log("showEventLog_"+TAG,content + "_ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                log("showEventLog_"+TAG,content + "_ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                log("showEventLog_"+TAG,content + "_ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                log("showEventLog_"+TAG,content + "_ACTION_CANCEL");
                break;
            default:
                break;
        }
    }
}
