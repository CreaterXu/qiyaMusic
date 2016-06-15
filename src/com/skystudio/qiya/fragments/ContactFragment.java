package com.skystudio.qiya.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.imsdk.chat.ChatActivity;
import com.skystudio.qiya.R;
import com.skystudio.qiya.adpter.ContactListViewAdpter;
import com.skystudio.qiya.pojo.User;
import com.skystudio.qiya.util.HttpUtil;
import com.skystudio.qiya.util.JsonUtil;

public class ContactFragment extends Fragment {
	private View view;
	private ListView cListView;
	private ContactListViewAdpter adpter;
	private ArrayList<User> uList = new ArrayList<User>();
	private PullToRefreshListView pullToRefreshListView;

	Handler handl = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				System.out.println(uList);
				adpter = new ContactListViewAdpter(getActivity(), uList);
				pullToRefreshListView.onRefreshComplete();
				cListView.setAdapter(adpter);
				cListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long lp) {
						// TODO Auto-generated method stub
						TextView t = (TextView) adpter.getView(position, view,
								parent).findViewById(R.id.nameTextView);
						ChatActivity.lanch(getActivity(), t.getText() + "");
					}
				});
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_contact, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		pullToRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.pulltorefreshListView);
		pullToRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						HttpUtil hu = new HttpUtil();
						try {
							String ulistJson = "";
							ulistJson = hu
									.getRequest("userManagerServlet?action=lookUser");
							JsonUtil ju = new JsonUtil();
							uList = ju.json2UsersList(ulistJson);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							Toast.makeText(getActivity(), "错误",
									Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
						handl.sendEmptyMessage(0);
					}
				});
		cListView = pullToRefreshListView.getRefreshableView();
		HttpUtil hu = new HttpUtil();
		try {
			String ulistJson = "";
			ulistJson = hu.getRequest("userManagerServlet?action=lookUser");
			JsonUtil ju = new JsonUtil();
			uList = ju.json2UsersList(ulistJson);
			handl.sendEmptyMessage(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Toast.makeText(getActivity(), "错误",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

	}
}
