package com.skystudio.qiya.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.widget.Toast;

public class CheckConnection extends Thread {
	String responseString = "";
	private String userName;
	private String passWord;

	public CheckConnection(String userName, String passWord) {
		// TODO Auto-generated constructor stub
		this.userName = userName;
		this.passWord = passWord;
	}

	public String checkConnction() throws InterruptedException,
			ExecutionException {
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						// TODO Auto-generated method stub
						String url = "http://192.168.0.103:8080/cardGame/userLoginServlet?Name="
								+ userName + "&Pwd=" + passWord;
						HttpClient client = new DefaultHttpClient();
						HttpGet hg = new HttpGet(url);

						HttpResponse hr = client.execute(hg);
						System.out.println("connecting");
						String temp;
						StringBuffer sb = new StringBuffer();
						BufferedReader in = new BufferedReader(
								new InputStreamReader(hr.getEntity()
										.getContent()));
						while ((temp = in.readLine()) != null) {
							sb.append(temp);
						}
						responseString = sb.toString();
						in.close();
						return responseString;
					}
				});
		new Thread(task).start();
		return task.get();

	}

}
