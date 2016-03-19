package com.example.cheng.myfifthapplication.Hleper;

import android.content.Context;

import com.example.cheng.myfifthapplication.Bean.DailyInfo;
import com.example.cheng.myfifthapplication.util.DbUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志操作
 * Created by cheng on 16-2-13.
 */
public class DailyDataHelper {
	List<DailyInfo> itemlist; //所有日志的列表
	DbUtil dbUtil;

	public DailyDataHelper(Context context){
		itemlist =new ArrayList<DailyInfo>();
		dbUtil = new DbUtil(context);
		itemlist = dbUtil.getAllData();
	}

	/**
	 * 获取所有日志数据的List
	 * @return
     */
	public List<DailyInfo> getDailyViewData(){
		itemlist = dbUtil.getAllData();
		return itemlist;
	}

	/**
	 * 通过Id获取日志信息
	 * @param id
	 * @return
     */
	public DailyInfo getDailyInfoById(int id){
		DailyInfo daily;
		for(int i=0;i<itemlist.size();i++){
           daily = itemlist.get(i);
			if (daily.getId() == id)
				return daily;
		}
		return null;
	}

	/**
	 * 新建日志
	 * @param title
	 * @param content
	 * @param color
     * @return
     */
	public boolean newDaily(String title,String content,String color){
		Date date = new Date();
		int year = date.getYear() + 1900;
		int month = date.getMonth() +1;
		int day = date.getDate();
		int hour = date.getHours();
		int min = date.getMinutes();
		String dateString = month + "月" + day + "日";
		String timeString = hour + ":" + min;
		DailyInfo daily = new DailyInfo(-1,dateString,timeString,title,content,color);
		dbUtil.insert(daily);
		return true;
	}

	public boolean newDaily(String title,String content){
		Date date = new Date();
		int year = date.getYear() + 1900;
		int month = date.getMonth() +1;
		int day = date.getDate();
		int hour = date.getHours();
		int min = date.getMinutes();
		String dateString = month + "月" + day + "日";
		String timeString = hour + ":" + min;
		DailyInfo daily = new DailyInfo(-1,dateString,timeString,title,content,"Black");
		dbUtil.insert(daily);
		return true;
	}

	/**
	 * 删除日志
	 * @param id
	 * @return
     */
	public boolean deleteDaily(int id){
		String idString = id+"";
		dbUtil.delete(idString);
		return true;
	}

	/**
	 * 更新日志
	 * @param id
	 * @param title
	 * @param content
     * @return
     */
	public boolean updateDaily(int id,String title,String content){
		DailyInfo daily = getDailyInfoById(id);

		Date date = new Date();
		int year = date.getYear() + 1900;
		int month = date.getMonth() +1;
		int day = date.getDate();
		int hour = date.getHours();
		int min = date.getMinutes();
		String dateString = month + "月" + day + "日";
		String timeString = hour + ":" + min;
		String idString = id+"";

		daily.setDate(dateString);
		daily.setTime(timeString);
		daily.setTitle(title);
		daily.setContent(content);

		dbUtil.update(idString,daily);
		return true;
	}

	public boolean updateDaily(int id,String title,String content,String color){
		DailyInfo daily = getDailyInfoById(id);

		Date date = new Date();
		int year = date.getYear() + 1900;
		int month = date.getMonth() +1;
		int day = date.getDate();
		int hour = date.getHours();
		int min = date.getMinutes();
		String dateString = month + "月" + day + "日";
		String timeString = hour + ":" + min;
		String idString = id+"";

		daily.setDate(dateString);
		daily.setTime(timeString);
		daily.setTitle(title);
		daily.setContent(content);
		daily.setColor(color);

		dbUtil.update(idString,daily);
		return true;
	}

	/**
	 * 获取最新的日志的id
	 * @return
     */
	public int getLastId(){
		return dbUtil.lastId();
	}

}
