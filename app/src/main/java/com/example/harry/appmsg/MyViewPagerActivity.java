package com.example.harry.appmsg;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MyViewPagerActivity extends AppCompatActivity {

    private static String TAG = "MyViewPagerActivity";
    ViewPager viewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view_pager);
        init();
    }

    private void init(){
        viewpager = (ViewPager)findViewById(R.id.viewpager);
        viewpager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int positionCurrent;
            boolean dontLoadList;
            /**
             * onPageScrolled  position 当前界面左面view的索引
             * positionOffset是左面view偏移的百分比
             * positionOffsetPixels左边页面偏移的像素
             * */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                positionCurrent = position;
                if( positionOffset == 0 && positionOffsetPixels == 0 ){ // the offset is zero when the swiping ends{
                    dontLoadList = false;
                } else{
                    dontLoadList = true; // To avoid loading content for list after swiping the pager.
                }

                Tool.log(TAG,"onPageScrolled position : "+position
                        +" positionOffset : "+ positionOffset
                        +" positionOffsetPixels : "+ positionOffsetPixels
                        +" positionCurrent : "+ positionCurrent
                        +" dontLoadList : "+ dontLoadList);
            }

            @Override
            public void onPageSelected(int position) {
//                Tool.log(TAG,"onPageSelected position : "+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == 0){ // the viewpager is idle as swipping ended
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            if(!dontLoadList){
                                //async thread code to execute loading the list...
                                Tool.log(TAG,"async thread code to execute loading the list...");
                            }
                        }
                    },200);
                }
            }
        });
    }


    class MyFragmentAdapter extends FragmentPagerAdapter{

        ArrayList<MyFragment> fragmentArrayList = new ArrayList<MyFragment>();

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
            fragmentArrayList.add(MyFragment.newInstance("Page1"));

            fragmentArrayList.add(MyFragment.newInstance("Page2"));

            fragmentArrayList.add(MyFragment.newInstance("Page3"));
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }
    }
}
