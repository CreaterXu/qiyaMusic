package com.skystudio.qiya.adpter;

import java.util.ArrayList;
import java.util.zip.Inflater;

import com.skystudio.qiya.R;
import com.skystudio.qiya.pojo.User;
import com.skystudio.qiya.util.ImageLoadUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactListViewAdpter extends BaseAdapter {
	private ArrayList<User> ulist;
	private LayoutInflater inflater;

	public ContactListViewAdpter(Context context, ArrayList<User> uList) {
		this.ulist = uList;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ulist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return ulist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View contview, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		//if (contview == null) {
			viewHolder=new ViewHolder();
			contview = inflater.inflate(R.layout.contacts_lineview, null);
			viewHolder.headImageView = (ImageView) contview
					.findViewById(R.id.headImageView);
			viewHolder.nameTextView = (TextView) contview
					.findViewById(R.id.nameTextView);
			contview.setTag(viewHolder);
		//} else {
			//viewHolder = (ViewHolder) contview.getTag();
		//}
		viewHolder.headImageView.setImageResource(R.drawable.ic_launcher);
		String url=ulist.get(position).getHeaduri();
		viewHolder.headImageView.setTag(url);
		new ImageLoadUtil(viewHolder.headImageView).execute(url);
		viewHolder.nameTextView.setText(ulist.get(position).getName());
		return contview;
	}

	class ViewHolder {
		public ImageView headImageView;
		public TextView nameTextView;
	}

}
