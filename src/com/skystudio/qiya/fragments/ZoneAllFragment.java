package com.skystudio.qiya.fragments;

import java.util.ArrayList;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.imsdk.chat.ChatActivity;
import com.skystudio.qiya.R;
import com.skystudio.qiya.adpter.ContactListViewAdpter;
import com.skystudio.qiya.adpter.ZoneListViewAdpter;
import com.skystudio.qiya.pojo.Share;
import com.skystudio.qiya.pojo.User;
import com.skystudio.qiya.util.HttpUtil;
import com.skystudio.qiya.util.JsonUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ZoneAllFragment extends Fragment {
	private View view;
	private ListView sListView;
	private ZoneListViewAdpter adpter;
	private ArrayList<Share> sList = new ArrayList<Share>();
	private PullToRefreshListView pullToRefreshListView;

	Handler handl = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				adpter = new ZoneListViewAdpter(getActivity(), sList);
				pullToRefreshListView.onRefreshComplete();
				sListView.setAdapter(adpter);

			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_zone_all, container, false);
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
							String slistJson = "";
							slistJson = hu
									.getRequest("shareManagerServlet?action=lookShares");
							JsonUtil ju = new JsonUtil();
							sList = ju.json2SharesList(slistJson);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							Toast.makeText(getActivity(), "错误",
									Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
						handl.sendEmptyMessage(0);
					}
				});
		sListView = pullToRefreshListView.getRefreshableView();
		HttpUtil hu = new HttpUtil();
		try {
			String slistJson = "";
			slistJson = hu.getRequest("shareManagerServlet?action=lookShares");
			JsonUtil ju = new JsonUtil();
			sList = ju.json2SharesList(slistJson);
			handl.sendEmptyMessage(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Toast.makeText(getActivity(), "错误",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

	}

}
