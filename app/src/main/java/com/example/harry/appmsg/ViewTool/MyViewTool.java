package com.example.harry.appmsg.ViewTool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.harry.appmsg.R;

/**
 * Created by Harry on 2017/11/26.
 * 參考 ：http://m.blog.csdn.net/rhljiayou/article/details/7212620
 */
public class MyViewTool {
    static Context mContext;
    static Paint paint = new Paint();  //創建instance不能寫在 onDraw 中
    public MyViewTool(Context mContext){
        this.mContext = mContext;
    }
    /*
    float centerX => 圓中心 x
    float centerY => 圓中心 y
    float radius => 圓半徑
    */
    public static View createCircleView(float centerX,float centerY,float radius){
//        paint.setColor(Color.RED);// 设置红色
        return new CircleView(mContext,centerX,centerY,radius);
    }


    static class CircleView extends View{
        float centerX , centerY,radius;
        public CircleView(Context context) {
            super(context);
        }

        public CircleView(Context context,float centerX,float centerY,float radius) {
            super(context);
            this.centerX = centerX;
            this.centerY = centerY;
            this.radius = radius;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            paint.setColor(Color.argb(255,255,0,0));
            paint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除
            canvas.drawCircle(centerX,centerY,radius,paint);
        }
    }

    public View getRecyclerView(Context context, ViewGroup root, boolean attachToRoot){
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.viewtool_recycler_view,root,attachToRoot);
    }

    public static float convertDpToPixel(Context context , float dp ){
        return dp * getDensity(context);
    }

    public static float convertPixelToDp(Context context , float px){
        return px / getDensity(context);
    }

    private static float getDensity(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.density;
    }

}
