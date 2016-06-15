package com.skystudio.qiya.ui.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.skystudio.qiya.Config.Contans;
import com.skystudio.qiya.R;
import com.skystudio.qiya.adpter.RecycleViewAdpter;
import com.skystudio.qiya.pojo.Friend;
import com.skystudio.qiya.pojo.Message;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/30.
 * 通讯录Fragment
 */
public class FriendsFragment extends BaseFragment {
    private ArrayList<Friend> friends=null;
    private FloatingActionButton mFloatingActionButton;
    @Override
    protected void getData() {
        super.getData();
        friends=new ArrayList<>();
        for (int i=0;i<10;i++){
            Friend friend=new Friend();
            friends.add(friend);
        }
    }

    @Override
    protected void setAdapter() {
        super.setAdapter();
        mRecycleViewAdapter=new RecycleViewAdpter(getActivity(),friends, Contans.CONTACTERS_FRAGMENT);
        mRecycleView.setAdapter(mRecycleViewAdapter);
    }

    @Override
    protected void setFloatActionButton() {
        super.setFloatActionButton();
        mFloatingActionButton=(FloatingActionButton) rootView.findViewById(R.id.action_float_button);
        mFloatingActionButton.setImageResource(R.drawable.ic_person_add_white_24dp);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启编辑动态Activity
                Snackbar.make(rootView,"xxxx",Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
