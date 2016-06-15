package com.skystudio.qiya.musicplayer;

import java.util.ArrayList;
import java.util.Map;
/**
 *
 * 
 * @author Creater xv
 * 
 * */
public class PlayerState {
	private static PlayerState playerState;
	private int state=0;
	private int currentId=-1;
	private ArrayList<Map<String, Object>> musics;
	private Map<String, Object> music;
	
	private PlayerState() {

	}
	public static PlayerState getInstance() {
		if (playerState == null) {
			playerState = new PlayerState();
		}
		return playerState;
	}
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getCurrentId() {
		return currentId;
	}
	public void setCurrentId(int currentId) {
		this.currentId = currentId;
	}
	public ArrayList<Map<String, Object>> getMusics() {
		return musics;
	}
	public void setMusics(ArrayList<Map<String, Object>> musics) {
		this.musics = musics;
	}
	public Map<String, Object> getMusic() {
		return music;
	}
	public void setMusic(Map<String, Object> music) {
		this.music = music;
	}
}
