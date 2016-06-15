package com.skystudio.qiya.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
/**
 *
 * @author Creater xv
 * @version 1.0
 * 
 * */
public class ImageLoadUtil extends AsyncTask<String, Void, Bitmap>{
	private ImageView headImageView;
	
	public ImageLoadUtil(ImageView imageView){
		this.headImageView=imageView;
	}
	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub
		return getBitmapFromUrl(params[0]);
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		headImageView.setImageBitmap(result);
	}
	private Bitmap getBitmapFromUrl(String url) {
		// TODO Auto-generated method stub
		Bitmap b=null;
		try {
			URL u=new URL(url);
			HttpURLConnection conn=(HttpURLConnection) u.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream in=conn.getInputStream();
			b=BitmapFactory.decodeStream(in);
			in.close();
			return b;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
