package com.example.cheng.myfifthapplication.Ui;

import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheng.myfifthapplication.Hleper.UpdateManage;
import com.example.cheng.myfifthapplication.R;
import com.example.cheng.myfifthapplication.util.AndroidShare;

import java.io.File;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by cheng on 16-2-10.
 */

public class AboutActivity extends BaseActivity {
	private Toolbar toolbar;
	private TextView version;
	private ListView listView;
	private MaterialDialog mMaterialDialog;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_show);
		init();
	}

	/**
	 * 初始化
	 */
	private void init()
	{
		toolbar = (Toolbar) findViewById(R.id.back_toolbar);
		TypedArray array = getTheme().obtainStyledAttributes(new int[]{android.R.attr.colorPrimary});
		toolbar.setBackgroundColor(array.getColor(0, 0xFF00FF));
		setSupportActionBar(toolbar);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		version = (TextView)findViewById(R.id.about_version);
		version.setText("V"+getVersion(AboutActivity.this));

		listView = (ListView)findViewById(R.id.about_listview);
		String[] mMenuTitles = getResources().getStringArray(R.array.about_array);
		listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,mMenuTitles));
		listView.setOnItemClickListener(new AboutListViewClickListener());
	}

	/**
	 * 获取本机的版本号
	 * @param context
	 * @return
     */
	public String getVersion(Context context) {
		String versionName = "";
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionName
			versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}

	private class AboutListViewClickListener implements ListView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		Log.d("tag", "selectItem: p"+position);
		switch (position)
		{
			case 0:
				showDailog("Email","    你好~ \n"+getResources().getString(R.string.Email));
				break;
			case 1:
				showDailog("Github",getResources().getString(R.string.Github));
				break;
			case 2:
				//分享
				AndroidShare as = new AndroidShare(
						this,
						"\"Daily 日志应用下载地址 \" http://www.jscheng.cn:9090/wordpress?download=87",
						"");
				as.show();break;
			case 3:
				//更新
				new UpdateManage(AboutActivity.this);
				break;
		}
	}

	/**
	 * @param title
	 * @param msg
     */
	private void showDailog(String title, final String msg) {
		mMaterialDialog = new MaterialDialog(this)
				.setTitle(title)
				.setMessage(msg)
				.setPositiveButton("复制", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						copy2board(msg);
						mMaterialDialog.dismiss();
					}
				})
				.setNegativeButton("取消", new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mMaterialDialog.dismiss();
					}
				});

		mMaterialDialog.show();
	}

	/**
	 * 将content复制到剪贴板
	 * @param content
     */
	private void copy2board(String content){
		ClipboardManager cmb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		cmb.setText(content);
		Toast.makeText(this,"已复制到剪切板",Toast.LENGTH_SHORT).show();
	}

}
