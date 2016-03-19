package com.example.cheng.myfifthapplication.Ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.cheng.myfifthapplication.Hleper.ActivityCollector;
import com.example.cheng.myfifthapplication.util.ThemeUtils;

/**
 * Created by cheng on 16-2-2.
 */
public class BaseActivity extends AppCompatActivity {

	protected void onCreate(Bundle savedInstanceState) {
		ThemeUtils.onActivityCreateSetTheme(this);
		super.onCreate(savedInstanceState);
		Log.d("BaseActivity", getClass().getSimpleName());
		ActivityCollector.addActivity(this);
	}
	@Override
	public void onBackPressed(){
//		onDestroy();
		finish();
		System.gc();//资源回收
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
}

