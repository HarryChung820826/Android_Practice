package com.example.harry.appmsg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyListView extends AppCompatActivity {

    LinearLayout bottom_msg;
    LinearLayout bottom2;
    ListView listview;
    EditText edt_input;
    ArrayList<String> list_data = new ArrayList<String>();
    ArrayList<String> list_data_filter = new ArrayList<String>();
    MyAdapter myAdapter;

    ArrayList<AsyncTaskTool> asyncTaskToolManager = new ArrayList<AsyncTaskTool>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list_view);
//        for(int i = 0 ; i < 10022 ; i ++) {
//            AsyncTaskTool asyncTaskTool = new AsyncTaskTool();
//            asyncTaskTool.execute();
//            asyncTaskToolManager.add(asyncTaskTool);
//        }
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        for(AsyncTaskTool asyncTaskTool :asyncTaskToolManager) {
            asyncTaskTool.cancel(true);
        }
    }

    private void init(){
        for(int i = 0 ; i < 50 ; i++){
            if(i%2 == 0) {
                list_data.add("Item_Two" + i);
            }else if(i%5 == 0) {
                list_data.add("Item_Five" + i);
            }else{
                list_data.add("Item_" + i);
            }
        }
        bottom2  = (LinearLayout)findViewById(R.id.bottom2);
        bottom_msg = (LinearLayout)findViewById(R.id.bottom_msg);
        listview = (ListView)findViewById(R.id.listview);
        myAdapter = new MyAdapter(list_data);
        listview.setAdapter(myAdapter);
//        listview.setAdapter(new ArrayAdapter<String>(MyListView.this,android.R.layout.simple_list_item_1,list_data));

        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
//                checkLastItemIsVisible(absListView);
            }
        });

        init_Edit();
    }

    class MyAdapter extends BaseAdapter{

        ArrayList<String> data = new ArrayList<String>();

        public MyAdapter(ArrayList<String> data) {
            this.data.addAll(data);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            if(data.size()>0){
                return data.get(i);
            }else{return null;}
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View myView = LayoutInflater.from(MyListView.this).inflate(android.R.layout.simple_list_item_1,null,false);
            ((TextView)myView.findViewById(android.R.id.text1)).setText(data.get(i));
            return myView ;
        }

        public void notifyMyData(ArrayList<String> notifydata ){
            data.clear();
            data.addAll(notifydata);
            notifyDataSetChanged();
        }
    }


    /*EditText 篩選*/
    private void init_Edit(){
        edt_input = (EditText)findViewById(R.id.edt_input);
        edt_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterData(edt_input.getText().toString());
            }
        });
    }


    private void filterData(String key){
        if(!(key.length()>0)){
            myAdapter.notifyMyData(list_data);
            setIsEmptyResult(false);
        }else{
            list_data_filter.clear();
             for(String content :list_data){
                if(content.contains(key)){
                    list_data_filter.add(content);
                }
             }
            setIsEmptyResult((list_data_filter.size()<=0));
            if(list_data_filter.size()>0) {
                myAdapter.notifyMyData(list_data_filter);
            }
        }

    }

    private void setIsEmptyResult(boolean isEmpty){
         if(isEmpty){
             bottom2.setVisibility(View.VISIBLE);
             listview.setVisibility(View.GONE);
         }else{
             bottom2.setVisibility(View.GONE);
             listview.setVisibility(View.VISIBLE);
         }
    }

    /*顯示底部透明訊息*/
    private void setBottomMsg(boolean isVisible){
        if(isVisible) {
            bottom_msg.setVisibility(View.VISIBLE);
        }else{
            bottom_msg.setVisibility(View.GONE);
        }
    }
    /*依據判斷最後一筆是否顯示 判斷是否顯示底部透明訊息*/
    private void checkLastItemIsVisible(AbsListView absListView){
        if(absListView.getLastVisiblePosition() != list_data.size()-1){
            setBottomMsg(true);
            Tool.log("MyListView","Already to bottom");
        }else{
            setBottomMsg(false);
        }
        Tool.log("MyListView",
                "LastVisiblePosition : "+absListView.getLastVisiblePosition() + " ; "
        +" dataSize : "+ list_data.size());
    }

}
