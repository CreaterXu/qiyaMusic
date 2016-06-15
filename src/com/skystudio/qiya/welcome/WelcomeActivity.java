package com.skystudio.qiya.welcome;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import com.skystudio.qiya.R;
import com.skystudio.qiya.fragments.QiyaActivity;
import com.skystudio.qiya.util.CheckConnection;
import com.skystudio.qiya.util.CurrentUser;

public class WelcomeActivity extends Activity {
	private Animation firstAnimation;
	private Animation topicaAnimation;
	private Runnable firstAnimationRunnble;
	private Handler firstAnimationHandler;

	Handler than = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				Toast.makeText(WelcomeActivity.this, "成功", Toast.LENGTH_LONG)
						.show();
				SharedPreferences preferences = getSharedPreferences("user",
						MODE_PRIVATE);
				String userName = preferences.getString("userName", "default");
				String passWord = preferences.getString("password", "111");
				CurrentUser currentUser = CurrentUser.getInstance();
				currentUser.setName(userName);
				currentUser.setPwd(passWord);
				// LoginOrLoginOutImAccount.login(getApplicationContext(),
				QiyaActivity.lanch(WelcomeActivity.this);
				WelcomeActivity.this.finish();
			} else {
				Toast.makeText(WelcomeActivity.this, "错误", Toast.LENGTH_LONG)
						.show();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		topicaAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		topicaAnimation.setDuration(2000);
		findViewById(R.id.imageView1).startAnimation(topicaAnimation);
		firstAnimation = AnimationUtils.loadAnimation(this, R.anim.first_out);
		firstAnimationRunnble = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				findViewById(R.id.firstlayout).startAnimation(firstAnimation);

			}
		};
		firstAnimationHandler = new Handler();
		firstAnimationHandler.removeCallbacks(firstAnimationRunnble);
		firstAnimationHandler.postDelayed(firstAnimationRunnble, 3000);
		firstAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				SharedPreferences preferences = getSharedPreferences("user",
						MODE_PRIVATE);
				String userName = preferences.getString("userName", "default");
				String passWord = preferences.getString("password", "111");
				boolean flag = preferences.getBoolean("autoFlag", false);
				if (!userName.equals("default") && flag) {
					Log.e("xv", "have user and flag true");
					CheckConnection cc = new CheckConnection(userName, passWord);
					String response = "";
					try {
						response = cc.checkConnction();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (response.equals("true")) {
						Message msg = new Message();
						msg.what = 0;
						than.sendMessage(msg);
					} else {
						Message msg = new Message();
						msg.what = 1;
						than.sendMessage(msg);
					}
				} else {
					LoadActivity.lanch(WelcomeActivity.this);
					WelcomeActivity.this.finish();
				}

			}
		});

	}
}
