package com.example.harry.appmsg;

import android.os.AsyncTask;

import java.util.Random;

/**
 * Created by Harry on 2017/9/3.
 */
public class AsyncTaskTool extends AsyncTask<Void,Void,Void>{
    long count = 1;
    Random random = new Random(10);
    String result = "";
    @Override
    protected Void doInBackground(Void... voids) {
        for(int i = 0 ; i < 20000 ; i ++){
            count += random.nextInt()+1;
            for(int j = 0 ; j < 50000 ; j ++){
                result+=Long.toString(count);
            }
        }
        return null;
    }
}
