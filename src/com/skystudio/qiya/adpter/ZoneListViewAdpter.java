package com.skystudio.qiya.adpter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.skystudio.qiya.R;
import com.skystudio.qiya.musicplayer.PlayerState;
import com.skystudio.qiya.pojo.Comment;
import com.skystudio.qiya.pojo.Share;
import com.skystudio.qiya.util.CurrentUser;
import com.skystudio.qiya.util.HttpUtil;
import com.skystudio.qiya.util.ImageLoadUtil;
import com.skystudio.qiya.util.JsonUtil;

public class ZoneListViewAdpter extends BaseAdapter {
	private ArrayList<Share> slist;
	private LayoutInflater inflater;
	ViewHolder viewHolder = null;
	Context context;
	ArrayList<String> commentStrings = new ArrayList<String>();

	public ZoneListViewAdpter(Context context, ArrayList<Share> sList) {
		this.slist = sList;
		inflater = LayoutInflater.from(context);
		this.context = context;
		for (int i = 0; i < sList.size(); i++) {
			commentStrings.add("");
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return slist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return slist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	private Integer index = -1;

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View contview, ViewGroup parent) {
		// TODO Auto-generated method stub

		viewHolder = new ViewHolder();
		contview = inflater.inflate(R.layout.zone_all_lineview, null);
		viewHolder.headImageView = (ImageView) contview
				.findViewById(R.id.headImageView);
		viewHolder.musicTextView = (TextView) contview
				.findViewById(R.id.musicTextView);
		viewHolder.commentButton = (Button) contview
				.findViewById(R.id.commentButton);
		viewHolder.commentEditText = (EditText) contview
				.findViewById(R.id.commentEditText);
		viewHolder.commentEditText.setTag(position);
		viewHolder.commentsListView = (ListView) contview
				.findViewById(R.id.commentsListView);
		viewHolder.contextTextView = (TextView) contview
				.findViewById(R.id.contentTextView);
		viewHolder.timeTextView = (TextView) contview
				.findViewById(R.id.timeTextView);

		contview.setTag(viewHolder);

		viewHolder.headImageView.setImageResource(R.drawable.icon_user);
		String url = slist.get(position).getHeadImageUrl();
		viewHolder.headImageView.setTag(url);
		new ImageLoadUtil(viewHolder.headImageView).execute(url);
		viewHolder.musicTextView.setText(slist.get(position).getAboutMusic());
		viewHolder.musicTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Map<String, String> conMap = new HashMap<String, String>();
				conMap.put("Name", slist.get(position).getAboutMusic());
				HttpUtil hu = new HttpUtil();
				try {
					String jsonString = "";
					jsonString = hu.postRequest(
							"musicManagerServlet?action=search", conMap);
					if (jsonString.equals("false")) {
						Toast.makeText(context, "û���ҵ�����",
								Toast.LENGTH_SHORT).show();
					}else {
						JsonUtil ju = new JsonUtil();
						ArrayList<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
						mList = ju.json2Musics(jsonString);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("title", mList.get(0).get("title"));
						map.put("url", mList.get(0).get("url"));
						PlayerState ps = PlayerState.getInstance();
						ps.setState(5);
						ps.setMusic(map);
					}
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		viewHolder.contextTextView.setText(slist.get(position).getContent());
		viewHolder.timeTextView.setText(slist.get(position).getTime());
		viewHolder.commentEditText
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View view, boolean isFocus) {
						// TODO Auto-generated method stub
						if (isFocus == false) {
							System.out.println("lose focus" + position);
							String s = "";
							s = ((EditText) view).getText().toString();
							commentStrings.set(position, s);
							System.out.println(commentStrings.toString());
						} else {
							System.out.println("have focus " + position);
						}
					}
				});
		viewHolder.commentButton.setOnClickListener(new OnClickListener() {

			@SuppressLint({ "SimpleDateFormat", "ShowToast" })
			@SuppressWarnings("static-access")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String comment = "";
				comment = commentStrings.get(position);
				if (comment.equals("")) {
				} else {
					Log.e("xv", slist.get(position).getComments().toString());
					String time = "";
					Date d = new Date();
					SimpleDateFormat myFmt2 = new SimpleDateFormat("yyyy-MM-dd");
					time = myFmt2.format(d);
					Map<String, String> params = new HashMap<String, String>();
					params.put("comment", comment);
					params.put("time", time);// ʱ��
					params.put("share_id", slist.get(position).getId() + "");
					CurrentUser cu=CurrentUser.getInstance();
					String name=cu.getName();
					params.put("post_name", name);// �û���
					System.out.println(params.toString());
					HttpUtil hu = new HttpUtil();
					try {
						String result = hu
								.postRequest(
										"shareManagerServlet?action=addComment",
										params);
						if (result.equals("true")) {
							Toast.makeText(context, "���۳ɹ�", Toast.LENGTH_SHORT)
									.show();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		});

		sonAdpter sa = new sonAdpter(slist.get(position).getComments(), context);
		viewHolder.commentsListView.setAdapter(sa);
		setListViewHeight(viewHolder.commentsListView);
		return contview;
	}

	public void setListViewHeight(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	class ViewHolder {
		public ImageView headImageView;
		public TextView musicTextView;
		public TextView timeTextView;
		public ListView commentsListView;
		public TextView contextTextView;
		public Button commentButton;
		public EditText commentEditText;
	}

	class sonAdpter extends BaseAdapter {
		private ArrayList<Comment> clist;
		viewhold viewholde = null;
		Context context;
		LayoutInflater inflater;

		public sonAdpter(ArrayList<Comment> cList, Context context) {
			// TODO Auto-generated constructor stub
			this.clist = cList;
			inflater = LayoutInflater.from(context);
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return clist.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return clist.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View contView, ViewGroup parent) {
			// TODO Auto-generated method stub
			viewholde = new viewhold();
			contView = inflater.inflate(R.layout.comments_lineview, null);
			viewholde.nameTextView = (TextView) contView
					.findViewById(R.id.nameTextView);
			viewholde.contentTextView = (TextView) contView
					.findViewById(R.id.contentTextView);
			viewholde.timeTextView = (TextView) contView
					.findViewById(R.id.timeTextView);
			contView.setTag(viewholde);
			viewholde.nameTextView.setText(clist.get(position).getPost_name()
					+ ":");
			viewholde.contentTextView.setText(clist.get(position).getContent());
			viewholde.timeTextView
					.setText("at" + clist.get(position).getTime());
			return contView;
		}

		class viewhold {
			private TextView nameTextView;
			private TextView contentTextView;
			private TextView timeTextView;
		}
	}

}
