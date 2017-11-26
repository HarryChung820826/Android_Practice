package com.example.harry.appmsg;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

public class BaseActivity extends Activity {

    View view;
    LinearLayout.LayoutParams params;
    WindowManager wManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        allView = getWindow().getDecorView().findViewById(android.R.id.content);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT);
//        params.topMargin = getStatusBarHeight();

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


    }

    @Override
    protected void onStart() {
        super.onStart();
        view = LayoutInflater.from(getApplication()).inflate(R.layout.my_app_msg, null);
//        view = getLayoutInflater().inflate(R.layout.my_app_msg,null);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.LEFT | Gravity.TOP;

    }

    protected void showAppMsg(){

        if(view.getWindowVisibility() != View.VISIBLE){
            getWindow().addContentView(view,params);
        }

        TranslateAnimation translateAnimation1 = new TranslateAnimation(0.0f,0.0f,-view.getHeight(),0.0f);
        translateAnimation1.setDuration(300);

        view.startAnimation(translateAnimation1);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TranslateAnimation translateAnimation1 = new TranslateAnimation(0.0f,0.0f,0.0f,-view.getHeight());
                translateAnimation1.setDuration(300);

                view.startAnimation(translateAnimation1);
                ((ViewGroup)view.getParent()).removeView(view);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        wManager.removeView(view);
    }

    protected void showWindow(){
        wManager = (WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
//        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.x = 0;
        wmParams.y = 0;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;



//        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
//        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;


//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                TranslateAnimation translateAnimation1 = new TranslateAnimation(0.0f,0.0f,0.0f,-view.getHeight());
//                translateAnimation1.setDuration(300);
//
//                view.startAnimation(translateAnimation1);
//                wManager.removeView(view);
//                Log.d("dddd","view click");
//                return false;
//            }
//        });
        view.setFocusable(true);
        view.findViewById(R.id.outer).setFocusable(true);
        view.findViewById(R.id.outer).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                TranslateAnimation translateAnimation1 = new TranslateAnimation(0.0f,0.0f,0.0f,-view.getHeight());
                translateAnimation1.setDuration(300);

                view.startAnimation(translateAnimation1);
                wManager.removeView(view);
                Log.d("dddd","view click");
                return false;
            }
        });

        wManager.addView(view,wmParams);
    }

    private int getStatusBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Status height:" + height);
        return height;
    }


    public int index = 0 ;

    protected void showNotification_Compat(){
        final int ITINDEX = index++;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        builder.setContent(remoteView);
        builder.setSmallIcon(R.drawable.ic_stat_name);
        builder.setContentTitle("MyTitle");
        builder.setContentText("MyMessage");
        builder.setAutoCancel(true);
        builder.setCategory(Notification.CATEGORY_ALARM);
        builder.setDefaults(Notification.DEFAULT_ALL);
//        builder.setCategory(Notification.CATEGORY_MESSAGE);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setFullScreenIntent(getMyPendingIntent(ITINDEX),false);
//        builder.setVisibility(NotificationCompat.VISIBILITY_PRIVATE);
        builder.setContentIntent(getMyPendingIntent(ITINDEX));
//        builder.addAction(R.id.tx2,"myNotificationwww",getMyPendingIntent(ITINDEX));
        Notification nott = builder.build();
        nott.contentView = getMyRemoteView();
//        nott.contentIntent = getMyPendingIntent();
        final NotificationManager manager = (NotificationManager)getApplication().getSystemService(NOTIFICATION_SERVICE);

        manager.notify(ITINDEX,nott);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    manager.cancel(ITINDEX);
                }catch(Exception e){}
            }
        }).start();



    }

     protected void showNotification(){
         int ITINDEX = index++;

         Notification.Builder builder = new Notification.Builder(this);
         builder.setSmallIcon(R.drawable.ic_stat_name);
         builder.setContentTitle("MyTitle");
         builder.setContentText("MyMessage");
         builder.setAutoCancel(true);
         builder.setTicker("myTickerMessage");
//         builder.setDefaults(Notification.DEFAULT_ALL);
//         builder.setContent(getMyRemoteView());
//         builder.setContentIntent(getMyPendingIntent(ITINDEX));
//         builder.setPriority(Notification.PRIORITY_MAX);
         builder.setFullScreenIntent(getMyPendingIntent(ITINDEX),true);
         NotificationManager manager = (NotificationManager)getApplication().getSystemService(NOTIFICATION_SERVICE);

         Notification nottt = builder.build();
//         nottt.contentView = getMyRemoteView();
         nottt.contentIntent = getMyPendingIntent(ITINDEX);
         nottt.priority = Notification.PRIORITY_MAX;
//         manager.notify(ITINDEX,builder.build());
         manager.notify(ITINDEX,nottt);
     }

    private PendingIntent getMyPendingIntent(int requestID){
        Intent intent = new Intent(this,TwoPageActivity.class);
        PendingIntent pd = PendingIntent.getActivity(this,requestID,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        return pd;
    }

    private RemoteViews getMyRemoteView(){
        RemoteViews remoteView = new RemoteViews(this.getPackageName(),R.layout.my_notification_view);

        remoteView.setTextViewText(R.id.tx1,"show");
        remoteView.setTextViewText(R.id.tx2,"myNotification");

//        remoteView.setOnClickPendingIntent(R.id.tx2,getMyPendingIntent());
//        remoteView.setOnClickFillInIntent(R.id.tx2,new Intent(this,TwoPageActivity.class));
        return remoteView;
    }


}

