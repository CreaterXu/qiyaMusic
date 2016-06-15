package com.skystudio.qiya.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;

import com.skystudio.qiya.pojo.User;

public class HttpUtil extends Thread {
	private String url = "http://192.168.43.216:8080/cardGame/";
	public String postRequest(String urlson, final Map<String, String> rawparams) throws Exception {
		final String urlall=url+urlson;
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						// TODO Auto-generated method stub
						HttpPost hp=new HttpPost(urlall);
						List<NameValuePair> params=new ArrayList<NameValuePair>();
						for (String key : rawparams.keySet()) {
							params.add(new BasicNameValuePair(key, rawparams.get(key)));
						}
						hp.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
						HttpClient client=new DefaultHttpClient();
						HttpResponse hr=client.execute(hp);
						System.out.println("connecting");
						String temp;
						StringBuffer sb = new StringBuffer();
						BufferedReader in = new BufferedReader(new InputStreamReader(hr
								.getEntity().getContent()));
						while ((temp = in.readLine()) != null) {
							sb.append(temp);
						}
						in.close();
						return sb.toString();
					}
				});
		new Thread(task).start();
		return task.get();
	}

	public String getRequest( String urlson) throws Exception {
		final String urlAll=url+urlson;
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						// TODO Auto-generated method stub
						HttpGet hg=new HttpGet(urlAll);
						HttpClient client=new DefaultHttpClient();
						HttpResponse hr=client.execute(hg);
						System.out.println("connecting");
						String temp;
						StringBuffer sb = new StringBuffer();
						BufferedReader in = new BufferedReader(new InputStreamReader(hr
								.getEntity().getContent()));
						while ((temp = in.readLine()) != null) {
							sb.append(temp);
						}
						in.close();
						return sb.toString();
					}
				});
		new Thread(task).start();
		return task.get();
	}
	

	

}
