package com.example.harry.appmsg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Harry on 2017/8/27.
 */
public class MyFragment extends Fragment {

    TextView tx_content;
    String tag;

    public MyFragment() {}

    public static MyFragment newInstance(String tag){
        MyFragment myFragment = new MyFragment();
        Bundle bd = new Bundle();
        bd.putString("tag",tag);
        myFragment.setArguments(bd);
        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getArguments().getString("tag");
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_layout,null);
        tx_content = (TextView)v.findViewById(R.id.tx_content);
        tx_content.setText(tag);
        return v;
    }
}
