package com.skystudio.qiya.fragments;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.skystudio.qiya.R;
import com.viewpagerindicator.TabPageIndicator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ZoneFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Context context = new ContextThemeWrapper(getActivity(),
				R.style.AppBaseTheme);
		LayoutInflater li=inflater.cloneInContext(context);
		LinearLayout l = (LinearLayout) li.inflate(
				R.layout.fragment_zone, container, false);
		ArrayList<Fragment> list = new ArrayList<Fragment>();
		Fragment zoneAll = new ZoneAllFragment();
		Fragment zoneMe = new ZoneMeFragment();
		list.add(zoneMe);
		list.add(zoneAll);
		FragmentPagerAdapter mfa = new MyFragmentAdpter(
				getChildFragmentManager(), list);
		ViewPager pager = (ViewPager) l.findViewById(R.id.pager);
		pager.setAdapter(mfa);
		TabPageIndicator indicator = (TabPageIndicator) l
				.findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		return l;
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class
					.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
}
