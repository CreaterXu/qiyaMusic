package com.skystudio.qiya.musicplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.Toast;

import com.skystudio.qiya.R;
import com.skystudio.qiya.util.HttpUtil;
import com.skystudio.qiya.util.JsonUtil;

public class MusicPlayService extends Service {
	private MediaPlayer media;
	private Music m;
	private int posi;

	private final int PLAY = 1;
	private final int PAUSE = 2;
	private final int CHANGE = 3;
	private final int POSITION = 4;
	private final int THEONE = 5;

	private int currentId = 0;
	private ArrayList<Map<String, Object>> musics;

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				try {
					// �½�һ��mediaplay
					if (media == null) {
						media = new MediaPlayer();
						media.setLooping(true);// ѭ��
					} else {
						media.release();
						media = new MediaPlayer();
						media.setLooping(true);// ѭ��
					}
					media.setDataSource((String) musics.get(currentId).get(
							"url"));
					media.prepare();
					m = Music.getInstance();
					m.setPosition(media.getCurrentPosition());
					m.setLength(media.getDuration());
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			} else if (msg.what == PLAY) {
				media.start();
				m.setPosition(media.getCurrentPosition());
			} else if (msg.what == PAUSE) {
				media.pause();
			} else if (msg.what == CHANGE) {
				media.start();
				m.setPosition(media.getCurrentPosition());
			} else if (msg.what == POSITION) {
				Music m = Music.getInstance();
				media.seekTo(m.getPosition());
				media.start();
				PlayerState ps = PlayerState.getInstance();
				ps.setState(PLAY);
			}
		};
	};
	Thread checkChange = new Thread() {
		public void run() {
			while (true) {
				m = Music.getInstance();
				posi = m.getPosition();
				PlayerState ps = PlayerState.getInstance();
				if (currentId == -1) {
					break;
				}
				musics = ps.getMusics();
				Message msg = new Message();
				if (ps.getState() == 0) {
					currentId = ps.getCurrentId();
					msg.what = 0;
					handler.sendMessage(msg);
				} else if (ps.getState() == PLAY) {
					currentId = ps.getCurrentId();
					msg.what = 1;
					handler.sendMessage(msg);
				} else if (ps.getState() == PAUSE) {
					currentId = ps.getCurrentId();
					msg.what = 2;
					handler.sendMessage(msg);
				} else if (ps.getState() == CHANGE) {
					if (currentId != ps.getCurrentId()) {
						currentId = ps.getCurrentId();
						if (media == null) {
							media = new MediaPlayer();
							media.setLooping(true);
						} else {
							media.stop();
							media = new MediaPlayer();
							media.setLooping(true);
						}
						try {
							media.setDataSource((String) musics.get(currentId)
									.get("url"));
							media.prepare();
							m = Music.getInstance();
							m.setPosition(media.getCurrentPosition());
							m.setLength(media.getDuration());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					msg.what = 3;
					handler.sendMessage(msg);
				} else if (ps.getState() == POSITION) {
					m = Music.getInstance();
					if (posi == m.getPosition()) {
						msg.what = 4;
						handler.sendMessage(msg);
					}

				} else if (ps.getState() == THEONE) {
					Log.e("xv", ps.getMusic().toString());
					Map<String, Object> map = ps.getMusic();
					if (media == null) {
						media = new MediaPlayer();
						media.setLooping(true);
					} else {
						media.stop();
						media = new MediaPlayer();
						media.setLooping(true);
					}
					try {
						media.setDataSource((String) map.get("url"));
						media.prepare();
//						m = Music.getInstance();
//						m.setPosition(media.getCurrentPosition());
//						m.setLength(media.getDuration());
					} catch (Exception e) {
						e.printStackTrace();
					}
					msg.what = 3;
					handler.sendMessage(msg);
				}

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Cursor cursor = this.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
				MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		musics = new ArrayList<Map<String, Object>>();
		if (cursor != null) {
			while (cursor.moveToNext()) {
				Map<String, Object> map = new HashMap<String, Object>();

				int id = cursor.getInt(cursor
						.getColumnIndex(MediaStore.Audio.Media._ID));

				String title = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));

				String url = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
				map.put("id", id);
				map.put("title", title);
				map.put("url", url);
				musics.add(map);
			}
		}
		cursor.close();
		HttpUtil hu = new HttpUtil();
		String jsonString = "";
		try {
			jsonString = hu
					.getRequest("musicManagerServlet?action=lookMyMusics");
			JsonUtil ju = new JsonUtil();
			musics.addAll(ju.json2Musics(jsonString));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PlayerState ps = PlayerState.getInstance();
		ps.setMusics(musics);
		int i = musics.size();
		int r = (int) (Math.random() * i);
		currentId = r;
		ps.setCurrentId(currentId);
		media = new MediaPlayer();
		media.setLooping(true);// ѭ��
		RemoteViews rv = new RemoteViews(getApplicationContext()
				.getPackageName(), R.layout.activity_musicplayer);
		rv.setProgressBar(R.id.seekBar1, media.getDuration(),
				media.getCurrentPosition(), true);
		checkChange.start();
		return super.onStartCommand(intent, flags, startId);
	}

}
