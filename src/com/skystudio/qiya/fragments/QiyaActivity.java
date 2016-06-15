package com.skystudio.qiya.fragments;

import com.imsdk.chat.LoginOrLoginOutImAccount;
import com.skystudio.qiya.R;
import com.skystudio.qiya.musicplayer.MusicPlayService;
import com.skystudio.qiya.shares.ToShareActivity;
import com.skystudio.qiya.util.CurrentUser;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

/**
 *
 * 
 * @author Creater XV
 * @version 1.0
 * @since com.skystudio.qiya.welcome.LoadActivity
 * */
public class QiyaActivity extends FragmentActivity implements OnClickListener {
	private ImageView messageImageView;
	private ImageView meImageView;
	private ImageView zoneImageView;
	private ImageView contactImageView;
	private ImageView shareImageView;

	private ZoneFragment zoneF;
	private MeFragment meF;
	private MessageFragment messageF;
	private ContactFragment contactF;
	boolean hasService = false;

	public static void lanch(Context c) {
		Intent intent = new Intent(c, QiyaActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		c.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_qiya);
		CurrentUser cu = CurrentUser.getInstance();
		String name = cu.getName();
		LoginOrLoginOutImAccount.login(getApplicationContext(), name);
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for (RunningServiceInfo services : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (services.service.getClassName().equals(
					"com.skystudio.qiya.musicplayer.FloatService")) {
				hasService = true;
				System.out.println("have service");
			}
		}
		if (!hasService) {
			Intent intent = new Intent(this,
					com.skystudio.qiya.musicplayer.FloatService.class);
			startService(intent);
		}

		messageImageView = (ImageView) findViewById(R.id.imageViewMesssage);
		meImageView = (ImageView) findViewById(R.id.imageViewMe);
		zoneImageView = (ImageView) findViewById(R.id.imageViewZone);
		contactImageView = (ImageView) findViewById(R.id.imageViewContacters);
		shareImageView = (ImageView) findViewById(R.id.imageViewShare);

		shareImageView.setOnClickListener(this);
		messageImageView.setOnClickListener(this);
		meImageView.setOnClickListener(this);
		zoneImageView.setOnClickListener(this);
		contactImageView.setOnClickListener(this);

		setDefaultFragment();

	}

	private void setDefaultFragment() {
		// TODO Auto-generated method stub
		FragmentManager fm = this.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		zoneF = new ZoneFragment();
		ft.replace(R.id.content, zoneF);
		zoneImageView.setBackgroundColor(Color.YELLOW);
		ft.commit();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		FragmentManager fm = this.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();

		if (v.getId() == R.id.imageViewMesssage) {
			if (messageF == null) {
				messageF = new MessageFragment();
			}
			ft.replace(R.id.content, messageF);
			messageImageView.setBackgroundColor(Color.YELLOW);
			zoneImageView.setBackgroundColor(Color.WHITE);
			contactImageView.setBackgroundColor(Color.WHITE);
			meImageView.setBackgroundColor(Color.WHITE);

		} else if (v.getId() == R.id.imageViewMe) {
			if (meF == null) {
				meF = new MeFragment();
			}
			ft.replace(R.id.content, meF);
			meImageView.setBackgroundColor(Color.YELLOW);
			zoneImageView.setBackgroundColor(Color.WHITE);
			contactImageView.setBackgroundColor(Color.WHITE);
			messageImageView.setBackgroundColor(Color.WHITE);
		} else if (v.getId() == R.id.imageViewContacters) {
			if (contactF == null) {
				contactF = new ContactFragment();
			}
			ft.replace(R.id.content, contactF);
			contactImageView.setBackgroundColor(Color.YELLOW);
			zoneImageView.setBackgroundColor(Color.WHITE);
			messageImageView.setBackgroundColor(Color.WHITE);
			meImageView.setBackgroundColor(Color.WHITE);
		} else if (v.getId() == R.id.imageViewZone) {
			if (zoneF == null) {
				zoneF = new ZoneFragment();
			}
			ft.replace(R.id.content, zoneF);
			zoneImageView.setBackgroundColor(Color.YELLOW);
			contactImageView.setBackgroundColor(Color.WHITE);
			messageImageView.setBackgroundColor(Color.WHITE);
			meImageView.setBackgroundColor(Color.WHITE);
		} else if (v.getId() == R.id.imageViewShare) {
			// �����·���ʱ���߼� ����һ���µ�activity��activity��themeΪ͸��
			shareImageView.setBackgroundColor(Color.rgb(256, 204, 153));
			Intent in = new Intent(QiyaActivity.this, ToShareActivity.class);
			in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(in);
		}
		ft.commit();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// ���߳̽�����������¼
		// LoginOrLoginOutImAccount.loginOut();
	}

}
