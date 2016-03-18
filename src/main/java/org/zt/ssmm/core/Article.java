package org.zt.ssmm.core;

import java.util.List;


public class Article {
	private int id;
	private int user_id;
	private String title;
	private String type;
	private String text;
//	private Uploadpic Uploadpic;
	private List<Uploadpic> Uploadpic;

	public List<Uploadpic> getUploadpics() {
		return Uploadpic;
	}
	public void setUploadpics(List<Uploadpic> uploadpics) {
		Uploadpic = uploadpics;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
