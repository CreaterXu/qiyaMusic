package com.skystudio.qiya.musicplayer;
/**
 *
 * 
 * @author Creater xv
 * 
 * */
public class Music {
	private int id;
	private String title;
	private String url;
	
	private int currentId=0;
	private int position=0;
	private int length=0;
	
	private static Music music;
	private Music(){}
	public static Music getInstance(){
		if (music==null) {
			music=new Music();
		}
		return music;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public int getCurrentId() {
		return currentId;
	}
	public void setCurrentId(int currentId) {
		this.currentId = currentId;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
}
