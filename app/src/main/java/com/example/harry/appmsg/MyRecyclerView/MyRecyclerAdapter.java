package com.example.harry.appmsg.MyRecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.harry.appmsg.R;
import com.example.harry.appmsg.Tool;

import java.util.ArrayList;

/**
 * Created by Harry on 2017/9/16.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Object> all_data;
    private Context context;
    private LayoutInflater layoutInflater;
    private int SECTION = 0;
    private int ITEM = 1;

    public MyRecyclerAdapter(Context context,ArrayList<Object> all_data) {
        this.all_data = all_data;
        this.context =context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM){
            return new ItemViewHolder(layoutInflater.inflate(R.layout.my_simple_item,parent,false));
        }
        if(viewType == SECTION){
            return new SectionViewHolder(layoutInflater.inflate(R.layout.my_section_simpel_layout,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        String content = "";
        if(holder instanceof ItemViewHolder){
            if(all_data.get(position) instanceof MainObject) {
                content = ((MainObject) all_data.get(position)).content;
            }
            if(all_data.get(position) instanceof AttachObject) {
                content = ((AttachObject) all_data.get(position)).content;
            }
            if(all_data.get(position) instanceof MyRecyclerItemObject) {
                content = ((MyRecyclerItemObject) all_data.get(position)).content;
            }
            ((ItemViewHolder)holder).txv_content.setText(content);
        }
        if(holder instanceof SectionViewHolder){
            content = ((SectionObject)all_data.get(position)).content;
            ((SectionViewHolder)holder).txv_section_content.setText(content);
            ((SectionViewHolder)holder).section_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    if(position+1 < all_data.size()) {
                        Tool.log("section_layout_click ", all_data.size() + " pos " + position + " all_data(pos) " + all_data.get(position).getClass().getName());
                        if (all_data.get(position + 1) instanceof SectionObject) {
                            //下一個是sectionObject , 表 目前position 是 縮小狀態
                            addItemList(position + 1, ((SectionObject) all_data.get(position)).section_list);
                        } else {
                            removeItemList(position + 1, ((SectionObject) all_data.get(position)).section_list.size());
                        }
//                        notifyDataSetChanged();
                    }else {
                        if (position + 1 == all_data.size()) {
                        /*最後一個Section 是 縮小狀態*/
                            addItemList(position+1, ((SectionObject) all_data.get(position)).section_list);
                        }
                    }
                    Tool.log("section_layout_click ", all_data.size() + " pos " + position + " all_data(pos) " + all_data.get(position).getClass().getName());
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(all_data!=null) {
            if (all_data.get(position) instanceof MyRecyclerItemObject) {
                if(((MyRecyclerItemObject) all_data.get(position)).layout == R.layout.my_section_simpel_layout){
                    return SECTION;
                }
                if(((MyRecyclerItemObject) all_data.get(position)).layout == R.layout.my_simple_item){
                    return ITEM;
                }
                return 0;
            } else {
                return 0;
            }
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        if(all_data!=null) {
            return all_data.size();
        }else{
            return 0;
        }
    }

    public void RefreshAdapter(ArrayList<Object> all_data){
        this.all_data = all_data;
        notifyDataSetChanged();
    }

    /**
     * 縮小Section (刪除item_list)
     * */
    public void removeItemList(int position, int remove_size){
        for(int i = 0 ; i < remove_size ; i ++){
            all_data.remove(position);
        }
        notifyItemRangeRemoved(position,remove_size);
    }

    /**
     * 展開Section (加入item_list)
     * */
    public void addItemList(int position, ArrayList<MyRecyclerItemObject> add_section_item){
        all_data.addAll(position,add_section_item);
        notifyItemRangeInserted(position,add_section_item.size());
    }


    class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView txv_content;
        public ItemViewHolder(View view) {
            super(view);
            txv_content = (TextView)view.findViewById(R.id.txv_content);
        }
    }

    class SectionViewHolder extends RecyclerView.ViewHolder{
        LinearLayout section_layout;
        TextView txv_section_content;
        public SectionViewHolder(View view) {
            super(view);
            section_layout = (LinearLayout) view.findViewById(R.id.section_layout);
            txv_section_content = (TextView)view.findViewById(R.id.txv_section_content);
        }
    }


}
