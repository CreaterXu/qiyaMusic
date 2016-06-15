package com.skystudio.qiya.shares;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.skystudio.qiya.R;
import com.skystudio.qiya.fragments.QiyaActivity;
import com.skystudio.qiya.musicplayer.PlayerState;
import com.skystudio.qiya.pojo.Share;
import com.skystudio.qiya.util.CurrentUser;
import com.skystudio.qiya.util.HttpUtil;
import com.skystudio.qiya.util.JsonUtil;

public class ShareActivity extends Activity {
	private Button okButton;
	private EditText contextEditText;
	private Spinner musicSpinner;
	private String content = "";

	@SuppressLint("HandlerLeak")
	private String flag;
	Handler hand = new Handler() {
		@SuppressLint("ShowToast")
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				Toast.makeText(getApplicationContext(), "成功",
						Toast.LENGTH_LONG);
				Intent in =new Intent(ShareActivity.this,QiyaActivity.class);
				startActivity(in);
				ShareActivity.this.finish();
			} else {
				Log.e("xv", "ffffff");
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_share);
		okButton = (Button) findViewById(R.id.OKButton);
		musicSpinner=(Spinner)findViewById(R.id.choiceMusicSpinner);
		PlayerState ps=PlayerState.getInstance();
		if (ps.getMusic()==null) {
			System.out.println("null");
		}else {
			System.out.println(ps.getMusic().toString());
		}
		
		contextEditText = (EditText) findViewById(R.id.contentEditText);
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				content = contextEditText.getText().toString();
				String time = "";
				Date d = new Date();
				SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd");
				time = myFmt2.format(d);
				CurrentUser currentUser=CurrentUser.getInstance();
				String post_name=currentUser.getName();
				System.out.println(post_name);
				Map<String, String> params = new HashMap<String, String>();
				params.put("content", content);
				params.put("time",time);
				params.put("about_music", "");
				params.put("post_name", post_name);
				HttpUtil hu = new HttpUtil();
				try {
					String back = hu.postRequest("shareManagerServlet?action=upload", params);
					System.out.println(back);
					if (back.equals("true")) {
						hand.sendEmptyMessage(0);
						
					}else {
						System.out.println("错误");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

}
