package com.skystudio.qiya.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.skystudio.qiya.R;
import com.skystudio.qiya.musicplayer.FloatService;
import com.skystudio.qiya.musicplayer.MusicPlayService;
import com.skystudio.qiya.util.CurrentUser;

public class MeFragment extends Fragment {
	private Button logoutButton;
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_me, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		logoutButton = (Button) view.findViewById(R.id.logoutButton);


		CurrentUser cu = CurrentUser.getInstance();
		if (cu.getName() == null) {
			System.out.println("have no instance");
		} else {
			System.out.println(cu.getName());
		}
		logoutButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SharedPreferences sp = getActivity().getSharedPreferences(
						"user", getActivity().MODE_PRIVATE);
				Editor editor = sp.edit();
				editor.clear();
				editor.commit();
				Intent in = new Intent(getActivity(), MusicPlayService.class);
				Intent in2 = new Intent(getActivity(), FloatService.class);
				getActivity().getApplicationContext().stopService(in);
				getActivity().getApplicationContext().stopService(in2);
				getActivity().finish();
			}
		});
	}
}
