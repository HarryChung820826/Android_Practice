package com.example.harry.appmsg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

public class TwoPageActivity extends AppCompatActivity {
    EditText edt1,edt2,edt3;
    LinearLayout bottom1,bottom2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_page);
        init();
    }

    private void init(){
        bottom1 = (LinearLayout)findViewById(R.id.bottom1);
        bottom2 = (LinearLayout)findViewById(R.id.bottom2);
        edt1 = (EditText)findViewById(R.id.edt1);
        edt1.setOnFocusChangeListener(getfocus);
        edt2 = (EditText)findViewById(R.id.edt2);
        edt2.setOnFocusChangeListener(getfocus);
        edt3 = (EditText)findViewById(R.id.edt3);
        edt3.setOnFocusChangeListener(getfocus);
    }

    private void setPan(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }
    private void setAdjust(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    View.OnFocusChangeListener getfocus = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if(view.getId() == edt1.getId()){
                if(bottom1.getVisibility() == View.VISIBLE)
                    setPan();
            }

            if(view.getId() == edt2.getId()){
                if(bottom1.getVisibility() == View.VISIBLE)
                    setAdjust();
            }

            if(view.getId() == edt3.getId() && b){
                if(bottom1.getVisibility() == View.VISIBLE) {
                    bottom2.setVisibility(View.VISIBLE);
                    bottom1.setVisibility(View.GONE);
                }else{
                    bottom2.setVisibility(View.GONE);
                    bottom1.setVisibility(View.VISIBLE);
                }
            }
        }
    };
}
