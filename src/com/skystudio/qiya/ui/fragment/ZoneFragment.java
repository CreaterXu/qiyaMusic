package com.skystudio.qiya.ui.fragment;

import android.annotation.TargetApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.skystudio.qiya.Config.Contans;
import com.skystudio.qiya.R;
import com.skystudio.qiya.adpter.RecycleViewAdpter;
import com.skystudio.qiya.pojo.Share;
import com.skystudio.qiya.ui.animation.FloatActionButtonAni;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/30.
 * 动态Fragment
 */
public class ZoneFragment extends BaseFragment {
    private FloatingActionButton mFloatingActionButton;
    private ArrayList<Share> shares = null;

    @Override
    protected void getData() {
        super.getData();
        shares = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Share share = new Share();
            shares.add(share);
        }
    }

    @Override
    protected void setAdapter() {
        super.setAdapter();
        mRecycleViewAdapter = new RecycleViewAdpter(getActivity(), shares, Contans.ZONE_FRAGMENT);
        mRecycleView.setAdapter(mRecycleViewAdapter);
    }

    @Override
    protected void setFloatActionButton() {
        super.setFloatActionButton();
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.action_float_button);
        mFloatingActionButton.setImageResource(R.drawable.ic_create_white_24dp);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启编辑动态Activity
                Snackbar.make(rootView, "xxxx", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void setScrollListen() {
        super.setScrollListen();
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                boolean isSignificantDelta = Math.abs(dy) > 3;
                if (isSignificantDelta) {
                    if (dy > 0) {//上滑
                        Log.e("xv","in shanghua");
                        FloatActionButtonAni.hideView(mFloatingActionButton, mFloatingActionButton.getHeight());

                    } else {//下拉
                        Log.e("xv","in xiala");
                        //FloatActionButtonAni.showView(mFloatingActionButton, mFloatingActionButton.getHeight());
                    }
                }
            }
        });

    }


}
