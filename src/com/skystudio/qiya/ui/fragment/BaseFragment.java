package com.skystudio.qiya.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skystudio.qiya.Config.Contans;
import com.skystudio.qiya.R;
import com.skystudio.qiya.adpter.RecycleViewAdpter;
import com.skystudio.qiya.pojo.Share;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/21.
 * 通用碎片类
 */
public class BaseFragment extends Fragment {
    private int position;
    private int res;
    protected RecyclerView mRecycleView;
    protected RecycleViewAdpter mRecycleViewAdapter;
    protected View rootView;
    private ArrayList<Share> shares;

    public BaseFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        res = R.layout.fragment_base;
        rootView = inflater.inflate(res, container, false);
        getData();
        setFloatActionButton();
        mRecycleView = (RecyclerView) rootView.findViewById(R.id.list);
        // 设置LinearLayoutManager
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 设置ItemAnimator
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        // 设置固定大小
        mRecycleView.setHasFixedSize(true);
        setScrollListen();
        setAdapter();
        return rootView;
    }

    /**
     * 获取数据来源,子类不同实现
     */
    protected void getData() {

    }

    /**
     * 设置适配器，子类实现
     */
    protected void setAdapter() {

    }

    /**
     * 设置FAB，子类实现
     */
    protected void setFloatActionButton() {

    }

    /**
     * 设置滑动监听事件
     */
    protected void setScrollListen() {
    }
}
