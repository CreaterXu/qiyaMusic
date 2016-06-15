package com.skystudio.qiya.adpter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.skystudio.qiya.Config.Contans;
import com.skystudio.qiya.ui.fragment.BaseFragment;
import com.skystudio.qiya.ui.fragment.FriendsFragment;
import com.skystudio.qiya.ui.fragment.ReconmmendFragment;
import com.skystudio.qiya.ui.fragment.ZoneFragment;

/**
 * Created by Administrator on 2016/4/21.
 */
public class ViewPagerAdpter extends FragmentStatePagerAdapter {
    public ViewPagerAdpter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment=null;
        switch (position){
            case Contans.ZONE_FRAGMENT:
                fragment=new ZoneFragment();
                break;
            case Contans.RECONMMEND_FRAGMENT:
                fragment=new ReconmmendFragment();
                break;
            case Contans.CONTACTERS_FRAGMENT:
                fragment=new FriendsFragment();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return Contans.FRAGMENT_NUMBERS;
    }
}
