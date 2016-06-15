package com.skystudio.qiya.pojo;

import java.io.Serializable;
import java.util.ArrayList;

public class Share {
	private int id;
	private String content = "";
	private String postName = "";
	private String time = "";
	private String to_id = "";
	private String aboutMusic = "";
	private String aboutMusiclist = "";
	private String to_peple = "";
	private String headImageUrl = "";
	private ArrayList<Comment> comments = null;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTo_id() {
		return to_id;
	}

	public void setTo_id(String to_id) {
		this.to_id = to_id;
	}

	public String getAboutMusic() {
		return aboutMusic;
	}

	public void setAboutMusic(String aboutMusic) {
		this.aboutMusic = aboutMusic;
	}

	public String getAboutMusiclist() {
		return aboutMusiclist;
	}

	public void setAboutMusiclist(String aboutMusiclist) {
		this.aboutMusiclist = aboutMusiclist;
	}

	public String getTo_peple() {
		return to_peple;
	}

	public void setTo_peple(String to_peple) {
		this.to_peple = to_peple;
	}

	public String getHeadImageUrl() {
		return headImageUrl;
	}

	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id=" + this.id + "....aboutmusic=" + this.aboutMusic
				+ "....aboutMusiclist=" + this.aboutMusiclist + "....content="
				+ this.content + "....headimage=" + this.headImageUrl
				+ "....postname=" + this.postName + "....comments="
				+ this.getComments() + "....time=" + this.time + "....to-id="
				+ this.to_id + "....to-people=" + this.to_peple;
	}
}
