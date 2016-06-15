package com.skystudio.qiya.adpter;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MusicAdapter extends BaseExpandableListAdapter {
	private ArrayList<Map<String, Object>> nativeList = new ArrayList<Map<String, Object>>();
	private ArrayList<Map<String, Object>> myNetList = new ArrayList<Map<String, Object>>();
	private String[] group = { "��������", "�ҵ�����" };
	private Context context;

	public MusicAdapter(ArrayList<Map<String, Object>> nativeList,
			ArrayList<Map<String, Object>> myNetList, Context context) {
		// TODO Auto-generated constructor stub
		this.myNetList = myNetList;
		this.nativeList = nativeList;
		this.context = context;
	}

	@Override
	public Object getChild(int grupPosition, int childPosition) {
		// TODO Auto-generated method stub		
		if (grupPosition == 0) {
			return nativeList.get(childPosition).get("title");// ������������ȡ������
		}
		return myNetList.get(childPosition).get("title");// ����title����ȡ������
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return arg1;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean islastchild, View arg3, ViewGroup arg4) {
		// TODO Auto-generated method stub
		LinearLayout ll = new LinearLayout(context);
		ll.setOrientation(0);
		TextView textView = getTextView2();
		textView.setText(getChild(groupPosition, childPosition).toString());
		ll.addView(textView);
		return ll;

	}


	public TextView getTextView2() {
		// TODO Auto-generated method stub
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, 100);
		TextView textView = new TextView(context);
		textView.setLayoutParams(lp);
		textView.setGravity(Gravity.CENTER_VERTICAL);
		textView.setPadding(36, 0, 0, 0);
		textView.setTextColor(Color.BLACK);
		return textView;
	}

	@Override
	public int getChildrenCount(int grupposition) {
		// TODO Auto-generated method stub
		if (grupposition==0) {
			return nativeList.size();
		}
		return myNetList.size();
	}

	@Override
	public Object getGroup(int arg0) {
		// TODO Auto-generated method stub
		return group[arg0];
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public long getGroupId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean arg1, View arg2,
			ViewGroup arg3) {
		// TODO Auto-generated method stub
		LinearLayout ll = new LinearLayout(context);
		ll.setOrientation(0);
		TextView textView = getTextView();
		textView.setTextColor(Color.BLACK);
		textView.setBackgroundColor(Color.rgb(102, 255, 255));
		textView.setText(getGroup(groupPosition).toString());
		ll.addView(textView);
		ll.setPadding(100, 10, 10, 10);
		return ll;
	}


	public TextView getTextView() {
		// TODO Auto-generated method stub
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, 100);
		TextView textView = new TextView(context);
		textView.setLayoutParams(lp);
		textView.setGravity(Gravity.CENTER_VERTICAL);
		textView.setPadding(36, 0, 0, 0);
		textView.setTextColor(Color.BLACK);
		return textView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}

}
