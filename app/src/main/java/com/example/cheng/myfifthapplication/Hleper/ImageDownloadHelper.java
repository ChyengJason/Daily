package com.example.cheng.myfifthapplication.Hleper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.example.cheng.myfifthapplication.util.Constant;
import com.example.cheng.myfifthapplication.util.HttpUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by cheng on 16-2-5.
 * 图片下载
 */
public class ImageDownloadHelper
{
	  Context context;
	  File imgFile;

		public ImageDownloadHelper(Context context, File imgFile){
			this.context = context;
			this.imgFile = imgFile;
		}

		public boolean Download() {
			if (HttpUtils.isNetworkConnected(context)) {
				Log.d("tag","download");
				HttpUtils.get(Constant.BASEURL, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int i, Header[] headers, byte[] bytes) {
						try {
							Log.d("tag", "dowload2");
							JSONObject jsonObject = new JSONObject(new String(bytes));
							String url = jsonObject.getString("link");
							Log.d("tag",jsonObject.getString("describe"));
							Log.d("tag", url);
							Log.d("tag","onsuccess2");

							HttpUtils.getImage(url, new BinaryHttpResponseHandler() {
								@Override
								public void onSuccess(int i, Header[] headers, byte[] bytes) {
									Log.d("tag", "onSuccess3: ");

									saveImage(imgFile, bytes);

									Log.d("tag", "onSuccess45: ");

								}

								@Override
								public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
									Log.d("tag", "fail11");
								}
							});

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
						Log.d("tag", "fail2");
					}
				});
				return true;
			} else {
				return false;//无网络链接
			}
		}

	public void saveImage(File file, byte[] bytes) {
		try {
			if (file.exists()) {
				file.delete();
			}
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(bytes);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
