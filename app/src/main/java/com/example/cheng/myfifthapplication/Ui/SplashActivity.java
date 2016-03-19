package com.example.cheng.myfifthapplication.Ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.cheng.myfifthapplication.Hleper.FileHelper;
import com.example.cheng.myfifthapplication.Hleper.ImageConpressHelper;
import com.example.cheng.myfifthapplication.R;

import java.io.File;

import com.example.cheng.myfifthapplication.util.Constant;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * Created by cheng on 16-2-2.
 * 出场动画
 */

public class SplashActivity extends Activity {
	private ImageView iv_start;
	private boolean isDownload;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);
//		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences pref =getSharedPreferences("Daily",0);
		isDownload = pref.getBoolean(Constant.START_PIC,true);
		if(isDownload == true) {
			initImageLoader(this);
			iv_start = (ImageView) findViewById(R.id.iv_start);
			initImage();
		}
		else{
			startActivity(isDownload);
		}
	}

	private void initImageLoader(Context context) {
		File cacheDir = StorageUtils.getCacheDirectory(context);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
						context).threadPoolSize(3)
						.threadPriority(Thread.NORM_PRIORITY - 2)
						.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
						.denyCacheImageMultipleSizesInMemory()
						.diskCacheFileNameGenerator(new Md5FileNameGenerator())
						.tasksProcessingOrder(QueueProcessingType.LIFO)
						.diskCache(new UnlimitedDiskCache(cacheDir)).writeDebugLogs()
						.build();
		ImageLoader.getInstance().init(config);

	}

	private void initImage() {
//		File dir = getFilesDir();
		String imgPath = Environment.getExternalStorageDirectory() + "/" + FileHelper.FOLDER + "/" + Constant.START_PIC;
//		final File imgFile = new File(dir, Constant.START_PIC);
		final File imgFile = new File(imgPath);
		if (imgFile.exists()) {
			iv_start.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
		} else {
//			iv_start.setImageResource(R.mipmap.splash_default);
			int width = this.getWindowManager().getDefaultDisplay().getWidth() / 3;
			int height = this.getWindowManager().getDefaultDisplay().getHeight() / 3;
			iv_start.setImageDrawable(ImageConpressHelper.resizeImage3(getResources(), R.drawable.splash_default ,width,height));
//			iv_start.setImageDrawable(ImageConpressHelper.resizeImage3(getResources() , R.drawable.splash_default,width,height));
		}

		final ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.0f,
						Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
						0.5f);
		scaleAnim.setFillAfter(true);
		scaleAnim.setDuration(1000);//设置动画持续时间
		scaleAnim.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				Log.d("tag", "start");
			}

			@Override
			public void onAnimationEnd(Animation animation) {

//				Log.d("tag", "onAnimationEnd: ");
				startActivity(isDownload);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		iv_start.startAnimation(scaleAnim);

	}

	private void startActivity(boolean isDownload) {
		Intent intent = new Intent(SplashActivity.this, MainActivity.class);
		intent.putExtra("download_pic",isDownload);
		int version = Integer.valueOf(android.os.Build.VERSION.SDK);
		if(version >= 5) {
			overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
		}
		startActivity(intent);
//		iv_start.setImageBitmap(null);
//		iv_start.setImageResource(0);
		finish();
		System.gc();
	}
}
