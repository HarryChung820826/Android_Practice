package com.example.harry.appmsg.RecyclerViewApplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.harry.appmsg.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Harry on 2017/10/21.
 */
public class RecyclerViewAppAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<String> allData = new ArrayList<String>();
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
        this.allData.addAll(allData);
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEWTYPE_ONE) {
            return new MyViewHolder(inflater.inflate(R.layout.recyclerview_app_item_one, parent, false));
        }else if(viewType == VIEWTYPE_TWO){
            return new MyViewHolder(inflater.inflate(R.layout.recyclerview_app_item_two, parent, false));
        }else{
//            VIEWTYPE_SAMPLELAYOUT
            return new MySampleLayoutViewHolder(inflater.inflate(R.layout.layout_sample, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        viewHolderHashMap.put(position,holder);
        if(holder instanceof MyViewHolder) {
            ((MyViewHolder)holder).tx_item.setText(allData.get(position));
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
        LinearLayout sample;
        public MySampleLayoutViewHolder(View itemView) {
            super(itemView);
            sample = (LinearLayout)itemView.findViewById(R.id.sample_layout);
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



}
