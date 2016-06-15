package com.skystudio.qiya.musicplayer;

import com.skystudio.qiya.R;

import android.app.ActivityManager;
import android.app.Service;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 *
 * 
 * @author Creater XV
 * @version 1.0
 * @since com.skystudio.qiya.fragments.QiyaActivity
 * */
public class FloatService extends Service {
	WindowManager wm;
	WindowManager.LayoutParams wlp;
	LinearLayout floatll;
	Button floatib;
	long first = 0;
	int count = 0;
	boolean waitDouble = true;

	private PlayerState state;
	private int playState;
	private boolean hasService = false;
	private AudioManager am;
	Handler handl = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				state = PlayerState.getInstance();
				System.out.println("single click");
				if (floatib.getText().equals("播放")) {
					// media.pause();
					playState = 2;
					state.setState(playState);
					floatib.setText("暂停");
				} else {
					// media.start();
					playState = 1;
					state.setState(playState);
					floatib.setText("播放");
				}
			} else if (msg.what == 1) {
				System.out.println("double click");
				Intent in = new Intent(getApplicationContext(),
						MusicplayerActivity.class);
				in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getApplication().startActivity(in);
			} else if (msg.what == 2) {
				floatib.setText("播放");
			} else if (msg.what == 3) {
				floatib.setText("播放");
			}
		};
	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		creatview();

		cheThread.start();
		openMusicService();
		return super.onStartCommand(intent, flags, startId);
	}

	private void openMusicService() {
		// TODO Auto-generated method stub
		// *************************//
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for (RunningServiceInfo services : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (services.service.getClassName().equals(
					"com.skystudio.qiya.musicplayer.MusicPlayService")) {
				hasService = true;
				System.out.println("have service");
			}
		}
		if (!hasService) {
			Intent intent = new Intent(this, MusicPlayService.class);
			startService(intent);
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if (wm != null) {
			wm.removeView(floatll);
		}
		super.onDestroy();
	}

	@SuppressWarnings("static-access")
	private void creatview() {
		// TODO Auto-generated method stub
		wlp = new WindowManager.LayoutParams();
		// wm = this.getWindowManager();
		wm = (WindowManager) getApplication().getSystemService(
				getApplication().WINDOW_SERVICE);
		Log.e("xv", wm.toString());
		// Log.e("xv", getWindow().getWindowManager().toString());
		wlp.type = LayoutParams.TYPE_PHONE;
		wlp.format = PixelFormat.RGBA_8888;
		;
		wlp.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
		wlp.gravity = Gravity.LEFT | Gravity.TOP;
		wlp.x = 0;
		wlp.y = 0;
		wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
		wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;

		LayoutInflater inflater = LayoutInflater.from(getApplication());
		floatll = (LinearLayout) inflater.inflate(R.layout.float_music, null);
		wm.addView(floatll, wlp);
		floatib = (Button) floatll.findViewById(R.id.imageButtonMiniplayer);

		floatib.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				wlp.x = (int) event.getRawX() - floatib.getMeasuredWidth() / 2;
				wlp.y = (int) event.getRawY() - floatib.getMeasuredHeight() / 2
						- 25;

				wm.updateViewLayout(floatll, wlp);
				return false;
			}
		});

		floatib.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("on clik ");
				if (waitDouble) {
					waitDouble = false;
					Thread th = new Thread() {
						public void run() {
							try {
								Thread.sleep(800);
								if (!waitDouble) {
									waitDouble = true;
									handl.sendEmptyMessage(0);
								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						};
					};
					th.start();
				} else {
					waitDouble = true;
					// ˫��
					handl.sendEmptyMessage(1);
				}
			}
		});

	}

	// ʵʱ����mini��״̬���߳���
	Thread cheThread = new Thread() {
		public void run() {
			while (true) {
				AudioManager am = (AudioManager) getApplicationContext()
						.getSystemService(AUDIO_SERVICE);
				if (am.isMusicActive()) {
					handl.sendEmptyMessage(2);
				} else {
					handl.sendEmptyMessage(3);
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
	};
}
