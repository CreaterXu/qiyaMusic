package com.skystudio.qiya.fragments;

import java.util.ArrayList;

import com.skystudio.qiya.R;
import com.viewpagerindicator.IconPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentAdpter extends FragmentPagerAdapter {
	private ArrayList<Fragment> list;
	private static final String[] CONTENT = new String[] { "好友", "相关" };
	protected static final int[] ICONS = new int[] { R.drawable.change,
			R.drawable.change2 };

	public MyFragmentAdpter(FragmentManager fm, ArrayList<Fragment> list) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.list = list;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return CONTENT[position % CONTENT.length].toUpperCase();
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ICONS.length;
	}

	public int getIconResId(int arg0) {
		// TODO Auto-generated method stub
		return ICONS[arg0 % ICONS.length];
	}

}
