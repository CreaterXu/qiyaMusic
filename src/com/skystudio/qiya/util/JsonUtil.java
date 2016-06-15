package com.skystudio.qiya.util;

import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skystudio.qiya.pojo.Share;
import com.skystudio.qiya.pojo.User;

/**
 *
 * 
 * @author creater xv
 * @since sevlet
 * @version 1.1
 * 
 * */

public class JsonUtil {
	private Gson gson = new Gson();

	public String list2Json(ArrayList<?> list) {
		String json = gson.toJson(list);
		return json;
	}

	public ArrayList<User> json2UsersList(String jsonString) {

		ArrayList<User> list = gson.fromJson(jsonString,
				new TypeToken<ArrayList<User>>() {
				}.getType());
		return list;
	}

	public ArrayList<Share> json2SharesList(String jsonString) {

		ArrayList<Share> list = gson.fromJson(jsonString,
				new TypeToken<ArrayList<Share>>() {
				}.getType());
		return list;
	}
	
	public ArrayList<Map<String, Object>> json2Musics(String jsonString) {

		ArrayList<Map<String, Object>> list = gson.fromJson(jsonString,
				new TypeToken<ArrayList<Map<String, Object>>>() {
				}.getType());
		return list;
	}
}
