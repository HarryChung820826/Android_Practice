package com.example.harry.appmsg.MyRecyclerView;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.harry.appmsg.R;

import java.util.ArrayList;
import java.util.Random;

public class MyRecyclerViewActivity extends Activity {

    Button inputMain,inputAttach,clearＭain,clearAttach;
    RecyclerView my_recyclerView;
    ArrayList<Object> all_data = new ArrayList<Object>();/*全部Data <MainObject + AttachObject>*/
    ArrayList<MainObject> Main_objects = new ArrayList<MainObject>();/*MainObject全部Data */
    ArrayList<AttachObject> Attach_objects = new ArrayList<AttachObject>();/*AttachObject全部Data */
    ArrayList<Object> UIobjects = new ArrayList<Object>(); /*UI上顯示的Data*/

    MyRecyclerAdapter myRecyclerAdapter;


    boolean clear_main_switch = false;
    boolean clear_attach_switch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recycler_view);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void init(){
        my_recyclerView = (RecyclerView)findViewById(R.id.my_recyclerView);
        clearＭain = (Button)findViewById(R.id.clearＭain);
        inputMain = (Button)findViewById(R.id.inputMain);
        clearAttach = (Button)findViewById(R.id.clearAttach);
        inputAttach = (Button)findViewById(R.id.inputAttach);

        inputMain.setOnClickListener(input_main_click);
        clearＭain.setOnClickListener(clear_main_click);

        inputAttach.setOnClickListener(input_attach_click);
        clearAttach.setOnClickListener(clear_attach_click);
    }

    /**
     * 背景 處理 資料
     * */
    private void initData(){
        new InitMainData().execute();
        new InitAttachData().execute();
    }

    /**
     * 顯示在ＵＩ上
     * */
    private synchronized void setUIData(ArrayList<Object> all_data){
        if(myRecyclerAdapter == null){
            myRecyclerAdapter = new MyRecyclerAdapter(this,all_data);
            my_recyclerView.setLayoutManager(new LinearLayoutManager(this));
            my_recyclerView.setAdapter(myRecyclerAdapter);
        }else{
            /*notify*/
            myRecyclerAdapter.RefreshAdapter(all_data);
        }
    }

    /**
     * 建立 Main資料物件
     * item layout use  R.layout.my_simple_item
     * */
    class InitMainData extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            synchronized (Main_objects) {
                /*清空MainObject資料*/
                Main_objects.clear();
                if(!clear_main_switch) {
                /*模凝 ＡＰＩ回傳的資料*/
                    for (int i = 0; i < 20; i++) {
                        Random random = new Random();
                        int random_index = random.nextInt(30)+1;
                        MainObject object = new MainObject();
                        object.content = "Index_" + random_index;
                        if (random_index % 3 == 0) {
                            object.existOtherSection = true;
                        }
                        object.layout = R.layout.my_simple_item;
                        Main_objects.add(object);
                    }
                }
                clear_main_switch = false;
                return null;
            }
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(Main_objects.size()>0) {
                new RefreshUIData().execute(Main_objects);
            }else{
                new ClearMainUIData().execute();
            }
        }
    }


    /**
     * 建立 Attach資料物件
     * item layout use  R.layout.my_simple_item
     * */
    class InitAttachData extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            synchronized (Attach_objects) {
                /*清空AttachObject資料*/
                Attach_objects.clear();
                if(!clear_attach_switch) {
                /*模凝 ＡＰＩ回傳的資料*/
                    Random random = new Random();
                    for (int i = 0; i < 20; i++) {
                        AttachObject object = new AttachObject();
                        object.content = "Attach_" + (random.nextInt(20) + i);
                        Attach_objects.add(object);
                    }
                }
                clear_attach_switch = false;
                return null;
            }
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(Attach_objects.size()>0) {
                new RefreshUIData().execute(Attach_objects);
            }else{
                new ClearAttachUIData().execute();
            }
        }
    }


    /*整理 ＵＩ顯示順序 */
    class RefreshUIData extends AsyncTask<ArrayList<? extends Object>,Void,ArrayList<Object>>{

        @Override
        protected ArrayList<Object> doInBackground(ArrayList<? extends Object>... data) {
            if(data.length>0){
                if(data[0].size()>0) {
                    if (data[0].get(0) instanceof MainObject) {
                        ArrangeMainObject((ArrayList<MainObject>) data[0]);
                    }
                    if (data[0].get(0) instanceof AttachObject) {
                        ArrangeAttachObject((ArrayList<AttachObject>) data[0]);
                    }
                }
            }
            return all_data;
        }

        @Override
        protected void onPostExecute(ArrayList<Object> all_data) {
            super.onPostExecute(all_data);
            setUIData(all_data);
        }
    }

    /*清除 MainObject ＵＩ顯示 */
    class ClearMainUIData extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            ArrangeMainObject(null);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setUIData(all_data);
        }
    }

    /*清除 MainObject ＵＩ顯示 */
    class ClearAttachUIData extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            ArrangeAttachObject(null);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setUIData(all_data);
        }
    }

    /*分類 整理 MainObject 加入UI_Data上*/
    private ArrayList<Object> ArrangeMainObject(ArrayList<MainObject> receive_data){
        /*判斷all_data(UIData) 已經有舊資料在UI_Data上*/
        if(all_data.size()>0) {
            /*all_data(UIData) 清空重新排序 加入UI_Data上*/
            ArrayList<Object> cache_AttachData = new ArrayList<Object>();
            if (all_data.get(1) != null && all_data.get(1) instanceof AttachObject) {
                if (all_data.get(0) != null && all_data.get(0) instanceof SectionObject) {
                    /*暫存 AttachSection & AttachItem*/
                    cache_AttachData.add(all_data.get(0));
                    cache_AttachData.add(all_data.get(1));
                }
            }
            all_data.clear();
            if (cache_AttachData.size() > 0)
                all_data.addAll(cache_AttachData);
        }

        if(receive_data !=null && receive_data.size()>0) {
            ArrayList<MyRecyclerItemObject> otherSection = new ArrayList<MyRecyclerItemObject>();
            ArrayList<MyRecyclerItemObject> mainSection = new ArrayList<MyRecyclerItemObject>();
            for (MainObject mainObject : receive_data) {
                if(mainObject.existOtherSection){
                    /*其他Section 也要顯示的MainObject*/
                    otherSection.add(mainObject);
                }
                /*主要Section 顯示的MainObject*/
                mainSection.add(mainObject);
            }
            /*其他Section 項目大於0 加入其他Section 物件 & 加入項目*/
            if(otherSection.size()>0){
                addSectionAndItem("OtherSection",otherSection);
            }
                     addSectionAndItem("MainSection",mainSection);
        }else {

            /*空MainObject 顯示 OtherSection & EmptyOtherItem */
            ArrayList<MyRecyclerItemObject> emptyOtherItem = new ArrayList<MyRecyclerItemObject>();
            MyRecyclerItemObject myRecyclerItemObject = new MyRecyclerItemObject();
            myRecyclerItemObject.content = "No any MainObject";
            myRecyclerItemObject.layout = R.layout.my_simple_item;
            emptyOtherItem.add(myRecyclerItemObject);

            addSectionAndItem("OtherSection", emptyOtherItem);
        }
        return all_data;
    }

    /*分類 整理 AttachObject 加入UI_Data上*/
    private void ArrangeAttachObject(ArrayList<AttachObject> receive_data){
        if(all_data.get(1)!=null && all_data.get(1) instanceof AttachObject){
            all_data.remove(1);

            if(all_data.get(0)!=null && all_data.get(0) instanceof SectionObject){
                all_data.remove(0);
            }
        }
        if(receive_data !=null && receive_data.size()>0) {
            String content ="";
            for(int i = 0 ; i < receive_data.size() ; i++){
                content +=receive_data.get(i).content;
                if(i != receive_data.size()-1){
                    content += "、";
                }
            }
            AttachObject attachObject = new AttachObject();
            attachObject.content = content;
            attachObject.layout =  R.layout.my_simple_item;
            ArrayList<MyRecyclerItemObject> recode = new ArrayList<MyRecyclerItemObject>();
            recode.add(attachObject);
            /*加入項目*/
            all_data.addAll(0,recode);

            SectionObject sectionObject = new SectionObject();
            /*加入Section*/
            sectionObject.content = "AttachSection";
            sectionObject.layout = R.layout.my_section_simpel_layout;
            /*Item 記錄在 Section物件裡 伸縮時用*/
            sectionObject.section_list.addAll(recode);
            all_data.add(0,sectionObject);
        }else{
            /*無Attach資料*/
//            AttachObject attachObject = new AttachObject();
//            attachObject.content = "無資料";
//            attachObject.layout =  R.layout.my_simple_item;
//            /*加入項目*/
//            all_data.add(0,attachObject);
        }
    }

    /*all_data 加入Section & Item 物件*/
    private void addSectionAndItem(String sectionName , ArrayList<MyRecyclerItemObject> item_data){
        /*加入Section*/
        SectionObject sectionObject = new SectionObject();
        sectionObject.content = sectionName;
        sectionObject.layout = R.layout.my_section_simpel_layout;
        /*Item 記錄在 Section物件裡 伸縮時用*/
        sectionObject.section_list.addAll(item_data);
        all_data.add(sectionObject);
        /*加入項目*/
        all_data.addAll(item_data);
    }


    View.OnClickListener input_main_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new InitMainData().execute();
        }
    };

    View.OnClickListener clear_main_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            clear_main_switch = true;
            new InitMainData().execute();
        }
    };

    View.OnClickListener input_attach_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new InitAttachData().execute();
        }
    };
    View.OnClickListener clear_attach_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            clear_attach_switch = true;
            new InitAttachData().execute();
        }
    };


}
