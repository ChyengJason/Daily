package com.example.cheng.myfifthapplication.Bean;

/**
 * Created by cheng on 16-2-13.
 * 日志信息的bean
 */
public class DailyInfo {
	private int id;        //id
	private String date;   //日期
	private String time;   //具体时间
	private String title;  //标题
	private String content;//内容
//	private String hash;   //哈希值
	private String color;  //字体颜色

	public DailyInfo(int id,String date,String time,String title,String content, String color){
		this.id = id;
		this.date  = date;
		this.time  = time;
		this.title = title;
		this.content = content;
		this.color = color;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

//	public String getHash() {
//		return hash;
//	}
//
//	public void setHash(String hash) {
//		this.hash = hash;
//	}
}
