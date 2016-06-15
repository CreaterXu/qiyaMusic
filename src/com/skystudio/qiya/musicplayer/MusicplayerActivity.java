package com.skystudio.qiya.musicplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.skystudio.qiya.R;
import com.skystudio.qiya.R.string;
import com.skystudio.qiya.adpter.MusicAdapter;
import com.skystudio.qiya.util.HttpUtil;
import com.skystudio.qiya.util.JsonUtil;

@SuppressLint("NewApi")
public class MusicplayerActivity extends Activity {
	private Button play;
	private SeekBar progressBar;
	private Button next;
	private Button searchButton;
	private EditText searchEditText;

	private ArrayList<Map<String, Object>> musics;
	private ArrayList<Map<String, Object>> nativeMusics;
	private ArrayList<Map<String, Object>> myNetMusics;
	private ExpandableListView showListview;
	private MusicAdapter musicAdapter;
	private int currentId;
	private SensorManager msm;
	private Sensor mSensor;
	private AudioManager am;
	private int position;
	private int playState = 0;
	PlayerState state;
	private boolean hasService = false;

	private final int ORDER_MODE = 1;
	private final int RANDOM_MODE = 2;
	private final int LOOP_MODE = 3;
	private int mode = 2;
	Handler hand = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				musicAdapter = new MusicAdapter(nativeMusics, myNetMusics,
						MusicplayerActivity.this);
				showListview.setAdapter(musicAdapter);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_musicplayer);

		msm = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		mSensor = msm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		msm.registerListener(new mySensorListener(this), mSensor, SensorManager.SENSOR_DELAY_NORMAL);
		play = (Button) findViewById(R.id.OKButton);
		next = (Button) findViewById(R.id.button3);
		progressBar = (SeekBar) findViewById(R.id.seekBar1);
		searchButton = (Button) findViewById(R.id.searchButton);
		searchEditText = (EditText) findViewById(R.id.searchEditText);
		show();
		seekBarChange.start();
		progressBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar arg0, int position,
					boolean fromUser) {
				// TODO Auto-generated method stub
				if (fromUser) {
					Music m = Music.getInstance();
					m.setPosition(position);
					playState = 4;
					state.setState(playState);
				}
			}
		});
		am = (AudioManager) this.getSystemService(AUDIO_SERVICE);
		if (am.isMusicActive()) {
			System.out.println("have music on");
			state = PlayerState.getInstance();
			play.setText("��ͣ");
		} else {
			int i = musics.size();
			int r = (int) (Math.random() * i);
			currentId = r;
			play.setText("播放");
			state = PlayerState.getInstance();
			playState = 0;
			state.setState(playState);
			state.setCurrentId(currentId);
			state.setMusics(musics);
		}

		// *********��������*********//

		// ��һ�ײ���
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mode == ORDER_MODE) {
					if (currentId == musics.size() - 1) {// ���������һ�׸�
						currentId = -1;// ��ʾ���ٲ���
					} else {
						currentId++;
					}
				} else if (mode == RANDOM_MODE) {
					int i = musics.size();// ��ȡ����������
					int r = (int) (Math.random() * i);// �տ�ʼ�������
					currentId = r;
				} else if (mode == LOOP_MODE) {
					if (currentId == musics.size() - 1) {// ���������һ�׸�
						currentId = 0;// ��ʾ��ͷ��ʼ
					} else {
						currentId++;
					}
				}
				playState = 3;// ��һ�ײ���״̬
				state.setState(playState);
				state.setCurrentId(currentId);
				play.setText("��ͣ");
			}
		});
		// �տ�ʼ��������¼�
		play.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (play.getText().equals("��ͣ")) {
					// media.pause();
					// ��ͣ����
					playState = 2;
					state.setState(playState);
					play.setText("����");
				} else {
					// media.start();
					playState = 1;
					state.setState(playState);
					play.setText("��ͣ");
				}
			}
		});
		// ѡ��ָ�����ֲ���
		showListview.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1,
					int grupposition, int childposition, long arg4) {
				// TODO Auto-generated method stub
				if (grupposition == 0) {
					currentId = childposition;
				} else if (grupposition == 1) {
					currentId = nativeMusics.size() + childposition;
				}
				playState = 3;
				state.setState(playState);
				state.setCurrentId(currentId);
				play.setText("��ͣ");
				return true;
			}
		});

		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String condition = "";
				condition = searchEditText.getText().toString();
				MusicSeerchActivity.lanch(condition,
						MusicplayerActivity.this);
			}
		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
//		
		return super.onOptionsItemSelected(item);
	}

	public void show() {
		// ********��ʾ����*********//
		showListview = (ExpandableListView) findViewById(R.id.listview);
		Cursor cursor = this.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
				MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		musics = new ArrayList<Map<String, Object>>();
		nativeMusics = new ArrayList<Map<String, Object>>();
		if (cursor != null) {
			while (cursor.moveToNext()) {
				Map<String, Object> map = new HashMap<String, Object>();
				// ��ȡ����id
				int id = cursor.getInt(cursor
						.getColumnIndex(MediaStore.Audio.Media._ID));
				// ��ȡ��������
				String title = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
				// ��ȡ����·��
				String url = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
				map.put("id", id);
				map.put("title", title);
				map.put("url", url);
				musics.add(map);
				nativeMusics.add(map);// ���������б�
			}
		}
		cursor.close();
		// ���������������ҵ����ֵ��߳�
		MinNetThread.start();

	}

	// ******���ٶȴ������ڲ���*******//
	public class mySensorListener implements SensorEventListener {
		private Activity context;

		public mySensorListener(Activity context) {
			// TODO Auto-generated constructor stub
			super();
			this.context = context;
		}

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub
			// �����������ȸı�ʱ�ص��÷�����Do nothing.
		}

		@Override
		public void onSensorChanged(SensorEvent sensorEvent) {
			// TODO Auto-generated method stub
			// ��ȡ����������
			int type = sensorEvent.sensor.getType();
			// ��ȡ�������仯ֵ
			float[] values = sensorEvent.values;
			// ������ڼ��ٶȴ�����
			if (type == Sensor.TYPE_ACCELEROMETER) {
				/*
				 * ��������£���������ֵ������9.8~10֮�䣬ֻ����ͻȻҡ���ֻ� ��ʱ��˲ʱ���ٶȲŻ�ͻȻ�������١�
				 * ������һ��ļ��ٶȴ���17����
				 */
				if ((Math.abs(values[0]) > 17 || Math.abs(values[1]) > 17 || Math
						.abs(values[2]) > 17)) {
					// ��ȡ��ҡ��
					System.out.println("��ҡ��");
					if (mode == ORDER_MODE) {
						if (currentId == musics.size() - 1) {// ���������һ�׸�
							currentId = -1;// ��ʾ���ٲ���
						} else {
							currentId++;
						}
					} else if (mode == RANDOM_MODE) {
						int i = musics.size();// ��ȡ����������
						int r = (int) (Math.random() * i);// �տ�ʼ�������
						currentId = r;
					} else if (mode == LOOP_MODE) {
						if (currentId == musics.size() - 1) {// ���������һ�׸�
							currentId = 0;// ��ʾ��ͷ��ʼ
						} else {
							currentId++;
						}
					}
					playState = 3;// ��һ�ײ���״̬
					state.setState(playState);
					state.setCurrentId(currentId);
					play.setText("��ͣ");
				}
			}

		}

	}

	// ******ʵ�ֽ��������µ��ڲ���*****//
	Thread seekBarChange = new Thread() {
		public void run() {
			while (true) {
				Music m = Music.getInstance();
				position = m.getPosition();
				progressBar.setMax(m.getLength());
				progressBar.setProgress(position);
				try {
					sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
	};

	// ********������������߳���********//
	Thread MinNetThread = new Thread() {
		public void run() {
			myNetMusics = new ArrayList<Map<String, Object>>();
			HttpUtil hu = new HttpUtil();
			String jsonString = "";
			try {
				jsonString = hu
						.getRequest("musicManagerServlet?action=lookMyMusics");
				JsonUtil ju = new JsonUtil();
				myNetMusics = ju.json2Musics(jsonString);
				musics.addAll(myNetMusics);
				Message msg = new Message();
				msg.what = 0;
				hand.sendMessage(msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

}
