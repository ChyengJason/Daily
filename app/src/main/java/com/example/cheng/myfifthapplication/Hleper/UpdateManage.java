package com.example.cheng.myfifthapplication.Hleper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cheng.myfifthapplication.Bean.UpdateInfo;
import com.example.cheng.myfifthapplication.R;
import com.example.cheng.myfifthapplication.util.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by cheng on 16-2-9.
 * 更新信息管理
 */
public class UpdateManage {
	/* 上下文 */
	private Context context;
	/* 更新的信息 */
	private UpdateInfo info;
	/* 下载中 */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;

	private static final int NEEDTOUPDATE = 3;

	private static final int DONTUPDATE = 4;

	private ProgressBar mProgress;

	private Dialog mDownloadDialog;

	private String mSavePath;

	/* 记录进度条数量 */
	private int progress;
	/* 是否取消更新 */
	private boolean cancelUpdate = false;

	private MyThreadPool myThreadPool = new MyThreadPool();

	public UpdateManage(Context context){
		this.context = context;
		CheckUpdate();
	}

	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				// 正在下载
				case DOWNLOAD:
					// 设置进度条位置
					mProgress.setProgress(progress);
					break;
				case DOWNLOAD_FINISH:
					// 安装文件
					installApk();
					break;
				case NEEDTOUPDATE:
					showNoticeDialog();
//					showDownloadDialog();
					Log.d("tag", "handleMessage: 1");
					break;
				case DONTUPDATE:
					Log.d("tag", "handleMessage: 2");
					Toast.makeText(context,R.string.soft_update_no,Toast.LENGTH_SHORT).show();
				default:
					break;
			}
		};
	};

	public void CheckUpdate() {
		Runnable task_check = new Runnable() {
			int currentVersion;
			int serverVersion;
			@Override
			public void run() {
				try{
					info = getUpdataInfo();
					currentVersion = getVersionCode(context);
					serverVersion = getServerVersion();
					Log.d("tag", "isUpdate->: " + currentVersion + "server->:" + serverVersion);
					if (currentVersion < serverVersion)
						mHandler.sendEmptyMessage(NEEDTOUPDATE);
					else
						mHandler.sendEmptyMessage(DONTUPDATE);
				}catch (Exception e)
				{
					e.printStackTrace();
					Log.d("tag", "get_run: "+e.getMessage());
				}
			}
		};
		myThreadPool.submit(task_check);
	}

	/**
	 * 获取本机版本号
	 * @param context
	 * @return
     */
	public int getVersionCode(Context context) {
		int versionCode = -1;
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 获取服务器的版本号
	 * @return
     */
	public int getServerVersion() {
		int ServerVersion = info.getIntegerVersion();
		return ServerVersion;
	}

	/**
	 * 获取服务器的wersion.xml
	 * @return
     */
	public UpdateInfo getUpdataInfo() {
//		String path = "http://www.jscheng.cn:9090/version.xml";
		String path = Constant.UPDATE_ADDRESS;
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(2000);
			conn.setRequestMethod("GET");
			InputStream is = conn.getInputStream();
			return UpdateInfoParser.getUpdataInfo(is);
		}catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	private void showNoticeDialog()
	{
		// 构造对话框
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.soft_update_title);
		builder.setMessage(R.string.soft_update_info);
		// 更新
		builder.setPositiveButton(R.string.soft_update_update, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				// 显示下载对话框
				showDownloadDialog();
			}
		});
		// 稍后更新
		builder.setNegativeButton(R.string.soft_update_later, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}

	/**
	 * 显示软件下载对话框
	 */
	private void showDownloadDialog()
	{
		// 构造软件下载对话框
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.soft_updating);
		// 给下载对话框增加进度条
		final LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.softupdate_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		builder.setView(v);
		// 取消更新
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				// 设置取消状态
				cancelUpdate = true;
			}
		});
		mDownloadDialog = builder.create();
		mDownloadDialog.show();
		// 现在文件
		downloadApk();
	}

	/**
	 * 下载apk文件
	 */
	private void downloadApk()
	{
		// 启动新线程下载软件
		Log.d("tag", "downloadApk: ");
		new downloadApkThread().start();
	}

	/**
	 * 下载文件线程
	 *
	 */
	private class downloadApkThread extends Thread
	{
		@Override
		public void run()
		{
			try
			{
				Log.d("tag", "run: download");
				// 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				{
					// 获得存储卡的路径
					String sdpath = Environment.getExternalStorageDirectory() + "/";
					mSavePath = sdpath + "download";
					Log.d("tag", "run: path:"+sdpath);
					URL url = new URL(info.getUrl());
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// 判断文件目录是否存在
					if (!file.exists())
					{
						file.mkdir();
					}
					File apkFile = new File(mSavePath, info.getName());
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do
					{
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0)
						{
							// 下载完成
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载.
					fos.close();
					is.close();
				}
				else{
					Log.d("tag", "run: 无权限");
				}
			} catch (MalformedURLException e)
			{
				e.printStackTrace();
				Log.d("tag", "run: "+e.getMessage());
			} catch (IOException e)
			{
				e.printStackTrace();
				Log.d("tag", "run: "+e.getMessage());
			}
			// 取消下载对话框显示
			mDownloadDialog.dismiss();
		}
	};

	/**
	 * 安装APK文件
	 */
	private void installApk()
	{
		File apkfile = new File(mSavePath, info.getName());
		if (!apkfile.exists())
		{
			return;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		context.startActivity(i);
	}
}
