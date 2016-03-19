package com.example.cheng.myfifthapplication.Ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.cheng.myfifthapplication.Hleper.FileHelper;
import com.example.cheng.myfifthapplication.Hleper.ImageSelectHelper;
import com.example.cheng.myfifthapplication.R;
import com.example.cheng.myfifthapplication.util.Constant;
import com.example.cheng.myfifthapplication.util.ThemeUtils;
import com.turkialkhateeb.materialcolorpicker.ColorChooserDialog;
import com.turkialkhateeb.materialcolorpicker.ColorListener;

import java.io.File;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by cheng on 16-2-15.
 */
public abstract class EditActivity extends BaseActivity {
	private Toolbar toolbar;
	private static final int PICK_PHOTO = 1;
	private static final int CROP_PHOTO = 2;
	private FrameLayout add_layout;
	private Bitmap wall_paper;
	private MaterialDialog mMaterialDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		initToolbar();
		init();
	}

	/**
	 * 获取Layout
	 * @return
     */
	protected FrameLayout getLayout(){
		return add_layout;
	}

	private void initToolbar() {
		toolbar = (Toolbar)findViewById(R.id.add_toolbar);
		setSupportActionBar(toolbar);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Toast.makeText(EditActivity.this,"finish",Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void init(){
		add_layout = (FrameLayout)findViewById(R.id.activity_add_layout);

	}
	@Override
	public  void onBackPressed(){
		if(wall_paper != null)
			wall_paper.recycle();
		System.gc();
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_add, menu);
		return true;
	}

	public abstract boolean saveDaily();

	public abstract void deleteDaily();

	public abstract void initBackground();

	public void pick_photo() {
		Intent pick_intent = ImageSelectHelper.pick_photo(EditActivity.this);
		if(pick_intent != null)
			startActivityForResult(pick_intent, PICK_PHOTO);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode){
			case PICK_PHOTO:
				if (resultCode == RESULT_OK) {
					int width = add_layout.getWidth();
					int height = add_layout.getHeight();
					PhotoZoom(data.getData(), width, height);
					Log.d("tag", "onActivityResult:"+width+"he:"+height);
				}
				break;
			case CROP_PHOTO:
				if (resultCode == RESULT_OK) {
//					showNoticeDialog();
					showDailog();
				}
				break;
			default:
				break;
		}
	}



	private void PhotoZoom(Uri uri, int width, int height) {
		Intent intent = ImageSelectHelper.PhotoZoom(uri,width,height);
		startActivityForResult(intent, CROP_PHOTO);
	}

	public void setAllLayoutBackground() {
		String file_path = Environment.getExternalStorageDirectory() + "/" + FileHelper.FOLDER + "/" + Constant.WALL_PAPER_TEM;
		String wall_paper_file = Environment.getExternalStorageDirectory() + "/" + FileHelper.FOLDER + "/" + Constant.WALL_PAPER_FILE_NAME;
		FileHelper.CopyFile(new File(file_path), new File(wall_paper_file));
		LoadBitmap(wall_paper_file);
		deleteOldPic();
	}

	/**
	 * 删除已经存在的背景图片（当有新壁纸时使用）
	 */
	public abstract void deleteOldPic();
	/**
	 * 设置为单个日志的背景图
	 */
	public abstract void setSpecialLayoutBackground();

	/**
	 * 加载通用背景图片
	 * @return
     */
	public boolean loadWallpaper() {
		String file_path = FileHelper.getFolder(true) + Constant.WALL_PAPER_FILE_NAME;
		File wall_paper_file = new File(file_path);
		if (wall_paper_file.exists()) {
			LoadBitmap(file_path);
			return true;
		}
		return false;
	}

	/**
	 * 加载特定图片背景
	 * @param id
	 * @return
     */
	public boolean loadWallpaper(int id) {
		String file_path = FileHelper.getFolder(true) + id+Constant.WALL_PAPER_FILE_NAME;
		File wall_paper_file = new File(file_path);
		if (wall_paper_file.exists()) {
			LoadBitmap(file_path);
			return true;
		}
		return false;
	}

	/**
	 * 加载图片
	 * @param file_path
     */
	public void LoadBitmap(String file_path) {
		wall_paper = BitmapFactory.decodeFile(file_path);
		Drawable drawable = new BitmapDrawable(getResources(), wall_paper);
		add_layout.setBackground(drawable);
		View shandow = (View)findViewById(R.id.toorbar_shadow);
		shandow.setVisibility(View.GONE);
	}

	private void showDailog() {
		mMaterialDialog = new MaterialDialog(this)
				.setTitle(getResources().getString(R.string.apply_as_all_background))
				.setMessage(getResources().getString(R.string.can_apply_as_all_background))
				.setPositiveButton(getResources().getString(R.string.use_as_all_backgourd), new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mMaterialDialog.dismiss();
						setAllLayoutBackground();
					}
				})
				.setNegativeButton(getResources().getString(R.string.use_as_special_backgourd), new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mMaterialDialog.dismiss();
						setSpecialLayoutBackground();
					}
				});

		mMaterialDialog.show();
	}

	/**
	 * 显示颜色选择板
	 */
	public void showColorDailog(){
		ColorChooserDialog dialog = new ColorChooserDialog(EditActivity.this);
		dialog.setColorListener(new ColorListener() {
			@Override
			public void OnColorClick(View v, int color) {
				changeWordColor(color);
			}
		});
		dialog.show();
	}

	public abstract void changeWordColor(int color);

	public abstract void deletePic();
}
