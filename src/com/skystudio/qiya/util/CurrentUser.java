package com.skystudio.qiya.util;



public class CurrentUser {
	private static CurrentUser currentUser;

	private CurrentUser() {
	}

	public static CurrentUser getInstance() {
		if (currentUser == null) {
			currentUser = new CurrentUser();
		}
		return currentUser;
	}
	
	private int id;
	private String name;
	private String pwd;
	private String headuri;
	private String registerTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getHeaduri() {
		return headuri;
	}

	public void setHeaduri(String headuri) {
		this.headuri = headuri;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

}
