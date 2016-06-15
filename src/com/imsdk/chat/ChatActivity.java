package com.imsdk.chat;

import imsdk.data.IMMyself;
import imsdk.data.IMSDK;
import imsdk.data.IMMyself.OnActionListener;
import imsdk.views.IMChatView;

import com.skystudio.qiya.R;
import com.skystudio.qiya.R.id;
import com.skystudio.qiya.R.layout;
import com.skystudio.qiya.R.menu;
import com.skystudio.qiya.fragments.QiyaActivity;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class ChatActivity extends ActionBarActivity {
	private IMChatView chatView;
	private static String targetUserId;
	public static void lanch(Context c,String targetUserId) {
		ChatActivity.targetUserId=targetUserId;
		Intent intent = new Intent(c, ChatActivity.class);
		c.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		System.out.println(targetUserId);
		chatView = new IMChatView(this, targetUserId);
		setContentView(chatView);
		chatView.setUserMainPhotoVisible(true);
		chatView.setUserNameVisible(false);
		chatView.setMaxGifCountInMessage(10);
		chatView.setTitleBarVisible(true);
	}

}
