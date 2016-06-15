package com.skystudio.qiya.shares;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.skystudio.qiya.R;


public class ToShareActivity extends Activity {
	private Button shareButton;
	private Button singForHimButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_share);
		shareButton=(Button)findViewById(R.id.shareButton);
		singForHimButton=(Button)findViewById(R.id.singForHimButton);
	
		shareButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in=new Intent(ToShareActivity.this, ShareActivity.class);
				startActivity(in);
				ToShareActivity.this.finish();
				
			}
		});
		
		singForHimButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	
}
