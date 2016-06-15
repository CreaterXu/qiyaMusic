package com.skystudio.qiya.musicplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.skystudio.qiya.R;
import com.skystudio.qiya.R.layout;
import com.skystudio.qiya.util.HttpUtil;
import com.skystudio.qiya.util.JsonUtil;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.StaticLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MusicSeerchActivity extends Activity {
	static String condition;
	public String condition2;
	private EditText searchEditText;
	private Button searchButton;
	private TextView textView;
	String name;
	String url;

	public static void lanch(String conditionString, Context context) {
		condition = conditionString;
		Intent in = new Intent(context, MusicSeerchActivity.class);
		context.startActivity(in);

	}

	Handler hand = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				name = msg.getData().getString("name");
				url = msg.getData().getString("url");
				textView.setText(msg.getData().getString("name"));
				textView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						textView.setBackgroundColor(Color.rgb(204, 204, 204));
						Toast.makeText(getApplicationContext(), "onclick",
								Toast.LENGTH_SHORT).show();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("title", name);
						map.put("url", url);
						PlayerState ps = PlayerState.getInstance();
						ps.setState(5);
						ps.setMusic(map);
					}
				});
				textView.setBackgroundColor(Color.WHITE);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_music_seerch);
		searchButton = (Button) findViewById(R.id.searchButton);
		searchEditText = (EditText) findViewById(R.id.searchEditText);
		textView = (TextView) findViewById(R.id.textView1);

		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String condition = "";
				condition = searchEditText.getText().toString();
				MusicSeerchActivity.lanch(condition, MusicSeerchActivity.this);
				MusicSeerchActivity.this.finish();
			}
		});

		condition2 = condition;
		System.out.println(condition2);

		search(condition2);
	}

	Music search(String conditon) {
		Map<String, String> conMap = new HashMap<String, String>();
		conMap.put("Name", conditon);
		HttpUtil hu = new HttpUtil();
		try {
			String jsonString = "";
			jsonString = hu.postRequest("musicManagerServlet?action=search",
					conMap);
			if (jsonString.equals("false")) {
				Toast.makeText(getApplicationContext(), "加载错误",
						Toast.LENGTH_SHORT).show();
			}else {
				JsonUtil ju = new JsonUtil();
				ArrayList<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
				mList = ju.json2Musics(jsonString);
				Bundle b = new Bundle();
				b.putString("name", (String) mList.get(0).get("title"));
				b.putString("url", (String) mList.get(0).get("url"));
				Message msg = new Message();
				msg.setData(b);
				msg.what = 0;
				hand.sendMessage(msg);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
