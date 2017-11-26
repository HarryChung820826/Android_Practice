package com.example.harry.appmsg;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MyGrideView extends AppCompatActivity {

    GridView gridview_one , gridview_two;
    LinearLayout linear_one ;

    String[] data_img = new String[]{
         "https://www.google.com.tw/imgres?imgurl=http%3A%2F%2Ffarm4.staticflickr.com%2F3795%2F9269794168_3ac58fc15c_b.jpg&imgrefurl=http%3A%2F%2Fladaga.pixnet.net%2F&docid=zHtFX2-3BpXhBM&tbnid=4ASn5nrmwq3-qM%3A&vet=10ahUKEwjMk9_FiZrWAhXMi5QKHfHuDQ8QMwh3KAEwAQ..i&w=1024&h=680&client=safari&bih=736&biw=1334&q=風景&ved=0ahUKEwjMk9_FiZrWAhXMi5QKHfHuDQ8QMwh3KAEwAQ&iact=mrc&uact=8"
         ,"https://www.google.com.tw/imgres?imgurl=http%3A%2F%2Ffarm4.staticflickr.com%2F3795%2F9269794168_3ac58fc15c_b.jpg&imgrefurl=http%3A%2F%2Fladaga.pixnet.net%2F&docid=zHtFX2-3BpXhBM&tbnid=4ASn5nrmwq3-qM%3A&vet=10ahUKEwjMk9_FiZrWAhXMi5QKHfHuDQ8QMwh3KAEwAQ..i&w=1024&h=680&client=safari&bih=736&biw=1334&q=風景&ved=0ahUKEwjMk9_FiZrWAhXMi5QKHfHuDQ8QMwh3KAEwAQ&iact=mrc&uact=8#h=680&imgdii=sDi-LD_SYW9RqM:&vet=10ahUKEwjMk9_FiZrWAhXMi5QKHfHuDQ8QMwh3KAEwAQ..i&w=1024"
         ,"https://www.google.com.tw/imgres?imgurl=http%3A%2F%2Ffarm4.staticflickr.com%2F3795%2F9269794168_3ac58fc15c_b.jpg&imgrefurl=http%3A%2F%2Fladaga.pixnet.net%2F&docid=zHtFX2-3BpXhBM&tbnid=4ASn5nrmwq3-qM%3A&vet=10ahUKEwjMk9_FiZrWAhXMi5QKHfHuDQ8QMwh3KAEwAQ..i&w=1024&h=680&client=safari&bih=736&biw=1334&q=風景&ved=0ahUKEwjMk9_FiZrWAhXMi5QKHfHuDQ8QMwh3KAEwAQ&iact=mrc&uact=8#h=680&imgdii=usNW-r9ZmvnImM:&vet=10ahUKEwjMk9_FiZrWAhXMi5QKHfHuDQ8QMwh3KAEwAQ..i&w=1024"
         ,"http://www.taiwan.net.tw/att/1/big_scenic_spots/pic_412_8.jpg"
         ,"http://kstf.tw.tranews.com/Show/images/Column/12607_2.jpg"
         ,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSwOrcwORPIImrdvdgDh0RnQJPlrB5N-95k2_qkLPAOHFFBL3La"
         ,"http://i.epochtimes.com/assets/uploads/2016/09/scene-e1474272128359.jpg"
         ,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTFlvB_hKtPh1sssikMJkYEKMHsjZk7l-4A69dVhl8S5ZW33cMQ"
         ,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTg-qvtl9UIkPw5UIyfJ4XO7hHdELWBS822q5VJ92Zx8c_a4Qui5g"
         ,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRSD2AYnDkA0rwkTOm-VRfGUiPROC0G-XY_Q7Fxp82YRmwE8caA"
         ,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSMgf3MN4lMbbicGx2kyTIe7RAR9YpL52uuq6bPQzcpsxtwPitTzg"
         ,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSM1l3g_ypmDrXN2RO71wvtH55z9IL3zIYGTHXb3ZtFEFt-Jb3B"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gride_view);
        init();
    }


    private void init(){
        gridview_one = (GridView)findViewById(R.id.gridview_one);
        gridview_two = (GridView)findViewById(R.id.gridview_two);
        linear_one = (LinearLayout) findViewById(R.id.linear_one);

        gridview_one.setAdapter(new MyGridViewAdapter(this,getRandomData(6),"gridview_one"));
        gridview_two.setAdapter(new MyGridViewAdapter(this,getRandomData(9),"gridview_two"));
    }

    private ArrayList<String> getRandomData(int size){
        Random random = new Random();
        int random_index= 0 ;
        ArrayList<String> one_data = new ArrayList<String>();

        for(int i = 0 ; i < size ;i ++) {
            random_index = random.nextInt(data_img.length);
            one_data.add(data_img[random_index]);
        }
        return one_data;
    }

    class MyGridViewAdapter extends BaseAdapter{
        private LayoutInflater mInflater = null;
        Context context = null;
        ArrayList<String> data = new ArrayList<String>();
        HashMap<String,View> myViewHolderHS = new HashMap<String,View>();
        private String TAG = "Sample";
        MyGridViewAdapter(Context context , ArrayList<String> data,String tag){
            this.data = data;
            this.context =context;
            this.mInflater = LayoutInflater.from(this.context);
            this.TAG = tag;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            if(data.get(i) != null){
                return data.get(i);
            }else{
                return null;
            }

        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            return makeView(i,convertView,viewGroup);
        }
        public boolean check_first = false;
        private View makeView(int i, View convertView, ViewGroup viewGroup){
//            Tool.log("MyGridViewAdapter ",TAG + " index =  "+ i +"  "+ data.get(i));
            if(check_first && i == 0){
                Tool.log("MyGridViewAdapter ",TAG + " index =  zero already set . " );
                return (View)myViewHolderHS.get(Integer.toString(i));
            }else{
                Tool.log("MyGridViewAdapter ",TAG + " index =  "+ i );
            }
            ViewHolder holder = null;
            if(convertView == null)
            {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.mygridview_item, null);
                holder.gridView_image = (ImageView)convertView.findViewById(R.id.gridView_image);
                convertView.setTag(holder);
                myViewHolderHS.put(Integer.toString(i),convertView);
                if(i == 0 && myViewHolderHS.get(Integer.toString(i)) != null){
                    check_first = true;
                }
            }else
            {
//                holder = (ViewHolder)convertView.getTag();
//                holder = (ViewHolder)myViewHolderHS.get(Integer.toString(i));
                return (View)myViewHolderHS.get(Integer.toString(i));
            }
            Glide.with(context).load(data.get(i)).placeholder(R.drawable.dog).error(R.drawable.ic_copyright_black_dp)
                    .fitCenter().crossFade().into(holder.gridView_image);

            return convertView;
        }

        class ViewHolder{
            ImageView gridView_image;
        }
    }
}
