package com.example.harry.appmsg.RecyclerViewApplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.harry.appmsg.R;
import com.example.harry.appmsg.Tool;
import com.example.harry.appmsg.ViewTool.MyViewTool;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Harry on 2017/10/21.
 */
public class RecyclerViewAppAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static ArrayList<String> allData = new ArrayList<String>();
    Context mContext;
    LayoutInflater inflater;

    public static final int VIEWTYPE_ONE = 1;
    public static final int VIEWTYPE_TWO = 2;
    public static final int VIEWTYPE_SAMPLELAYOUT = 3;
    public static final String SAMPLELAYOUT = "sample_layout";

    private HashMap<Integer,RecyclerView.ViewHolder> viewHolderHashMap = new HashMap<Integer,RecyclerView.ViewHolder>();
    public RecyclerViewAppAdapter(Context mContext,ArrayList<String> allData) {
        super();
        this.mContext = mContext;
        this.allData.clear();
        this.allData.addAll(allData);
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEWTYPE_ONE) {
            return new MyViewHolder(inflater.inflate(R.layout.recyclerview_app_item_one, parent, false));
        }else if(viewType == VIEWTYPE_TWO){
            return new MyViewHolder(inflater.inflate(R.layout.recyclerview_app_item_two, parent, false));
        }else /*if(viewType == VIEWTYPE_SAMPLELAYOUT)*/{
            return new MySampleLayoutViewHolder(mContext,inflater.inflate(R.layout.layout_sample, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        viewHolderHashMap.put(position,holder);
        if(holder instanceof MyViewHolder) {
            ((MyViewHolder)holder).tx_item.setText(allData.get(position));
        }

        if(holder instanceof MySampleLayoutViewHolder){
            ((MySampleLayoutViewHolder)holder).onBindViewHolder(position);
        }
    }

    @Override
    public int getItemCount() {
        return allData.size();
    }

    public RecyclerView.ViewHolder getViewHolder(int Pos){
        if(viewHolderHashMap!=null){
            return viewHolderHashMap.get(Pos);
        }
        return null;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tx_item;
        View line;
        public MyViewHolder(View itemView) {
            super(itemView);
            tx_item = (TextView)itemView.findViewById(R.id.tx_item);
            line = (View)itemView.findViewById(R.id.line);
            line.setVisibility(View.GONE);
        }
    }

    public static class MySampleLayoutViewHolder extends RecyclerView.ViewHolder{
        Context context;
        LinearLayout sample;
        public MySampleLayoutViewHolder(Context context ,View itemView) {
            super(itemView);
            this.context = context;
            sample = (LinearLayout)itemView.findViewById(R.id.sample_layout);
        }

        public void onBindViewHolder(int position){
            if(position > 0  && position < allData.size() -1 ) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) sample.getLayoutParams();
                params.height = (int)MyViewTool.convertDpToPixel(this.context,100);
                if((position%2 == 0)) {
                    sample.setBackgroundColor(this.context.getResources().getColor(R.color.blue88));
                }else{
                    sample.setBackgroundColor(this.context.getResources().getColor(R.color.green2299));
                }
                sample.setLayoutParams(params);
            }
            if(position == allData.size() -1){
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) sample.getLayoutParams();
                params.height = (int)MyViewTool.convertDpToPixel(this.context,1);
                sample.setLayoutParams(params);

            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(allData.get(position).equals(SAMPLELAYOUT)){
            return VIEWTYPE_SAMPLELAYOUT;
        }else {
            if (Integer.parseInt(allData.get(position)) % 2 == 0) {
                return VIEWTYPE_TWO;
            } else {
                return VIEWTYPE_ONE;
            }
        }
    }

    public void addItem(ArrayList<String> addData){
        allData.addAll(1,addData);
        notifyItemRangeInserted(1,addData.size());
    }

    public void removeItem(int remove_size){
        for(int i = 0 ; i < remove_size ; i ++){
            allData.remove(1);
        }
        notifyItemRangeRemoved(1,remove_size);
    }

    public void changeBottomLinearLayoutHeight(int height){
        RecyclerView.ViewHolder holder = viewHolderHashMap.get(allData.size()-1);
        holder.itemView.getMeasuredHeight();
        if(holder != null){
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) ((MySampleLayoutViewHolder)holder).sample.getLayoutParams();
//            params.height = (int)MyViewTool.convertDpToPixel(mContext,height);
            params.height = height;
            ((MySampleLayoutViewHolder)holder).sample.setLayoutParams(params);
        }else{
            Tool.log("onGlobalLayout","changeBottomLinearLayoutHeight holder is null");
        }

//        allData.add(allData.size() -1 , RecyclerViewAppAdapter.SAMPLELAYOUT);
//        notifyDataSetChanged();
    }
    int height_item_one = 0 ;
    int height_item_two = 0 ;
    int height_item_layout = 0 ;
    public int getRecyclerViewContentHeight(){
        int allHeight = 0 ;
        for(int i = 0 ; i < allData.size() ; i ++){
            RecyclerView.ViewHolder holder = viewHolderHashMap.get(allData.size()-1);
            if(holder!=null) {
                if (getItemViewType(i) == VIEWTYPE_ONE) {
                    height_item_one = holder.itemView.getMeasuredHeight();
                    allHeight += height_item_one;
                }
                if (getItemViewType(i) == VIEWTYPE_TWO) {
                    height_item_two = holder.itemView.getMeasuredHeight();
                    allHeight += height_item_two;
                }
                if (holder instanceof MySampleLayoutViewHolder) {
                    height_item_layout = holder.itemView.getMeasuredHeight();
                    allHeight += height_item_layout;
                }
            }
        }
        Tool.log("onGlobalLayout","onGlobalLayout height_item_layout " + height_item_layout + " allHeight "+allHeight);
        return  allHeight;
    }

}
