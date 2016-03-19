package com.example.cheng.myfifthapplication.Hleper;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 16-2-2.
 * 所有活动的收集器，方便删除添加activity，和结束应用
 */
public class ActivityCollector {
	public static List<Activity> activities = new ArrayList<Activity>();

	public static void addActivity(Activity activity) {
		activities.add(activity);
	}

	public static void removeActivity(Activity activity) {
		activities.remove(activity);
	}

	/**
	 * 结束所有Activity
	 */
	public static void finishAll() {
		for (Activity activity : activities) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
	}
}
