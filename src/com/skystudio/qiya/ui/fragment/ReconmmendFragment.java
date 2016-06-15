package com.skystudio.qiya.ui.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.skystudio.qiya.Config.Contans;
import com.skystudio.qiya.R;
import com.skystudio.qiya.adpter.RecycleViewAdpter;
import com.skystudio.qiya.pojo.Message;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/30.
 * 推荐Fragment
 */
public class ReconmmendFragment extends BaseFragment {
    private ArrayList<Message> messages=null;
    private FloatingActionButton mFloatingActionButton;
    @Override
    protected void getData() {
        super.getData();
        messages=new ArrayList<>();
        for (int i=0;i<10;i++){
            Message message=new Message();
            messages.add(message);
        }
    }

    @Override
    protected void setAdapter() {
        super.setAdapter();
        mRecycleViewAdapter=new RecycleViewAdpter(getActivity(),messages, Contans.RECONMMEND_FRAGMENT);
        mRecycleView.setAdapter(mRecycleViewAdapter);
    }

    @Override
    protected void setFloatActionButton() {
        super.setFloatActionButton();
        mFloatingActionButton=(FloatingActionButton) rootView.findViewById(R.id.action_float_button);
        mFloatingActionButton.setImageResource(R.drawable.ic_add_white_24dp);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启编辑动态Activity
                Snackbar.make(rootView,"xxxx",Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
