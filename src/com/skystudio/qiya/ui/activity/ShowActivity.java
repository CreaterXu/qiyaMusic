package com.skystudio.qiya.ui.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.skystudio.qiya.R;


/**
 * Created by Administrator on 2016/4/26.
 */
public class ShowActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_show);
        init();
        super.onCreate(savedInstanceState);
    }

    public void init() {
        mToolbar=(Toolbar)this.findViewById(R.id.id_toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
    }
}
