package com.skystudio.qiya.ui.listener;

import android.annotation.TargetApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by Administrator on 2016/5/9.
 */
@TargetApi(23)
public class RecyclerviewScrollListener extends RecyclerView.OnScrollListener {
    private  FloatingActionButton fab;
    private  int fragmentIndex;
    public RecyclerviewScrollListener(FloatingActionButton fab,int fragmentIndex){
        this.fab=fab;
        this.fragmentIndex=fragmentIndex;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }
}
