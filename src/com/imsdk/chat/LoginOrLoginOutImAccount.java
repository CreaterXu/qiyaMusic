package com.imsdk.chat;

import imsdk.data.IMMyself;
import imsdk.data.IMSDK;
import imsdk.data.IMMyself.OnActionListener;
import android.content.Context;
import android.util.Log;

/**
 *
 * 
 * @author Creater xv
 * @since com.skystudio.qiya.welcome.LoadActivity
 * 
 * */
public class LoginOrLoginOutImAccount {

	public static void login(Context context, String useId) {
		System.out.println(useId);
		IMSDK.init(context, "209189ae61dab6dd26202b92");
		IMMyself.setCustomUserID(useId);
		IMMyself.setPassword(" ");
		IMMyself.login(true, 10, new OnActionListener() {

			@Override
			public void onFailure(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Log.e("xv", "success"+IMMyself.getCustomUserID());
			}
		});
	}
	//�˳���ǰ�˺�
	public static void loginOut() {
		IMMyself.logout();
	}
}
