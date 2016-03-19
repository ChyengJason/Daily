package com.example.cheng.myfifthapplication.Ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cheng.myfifthapplication.Adapter.DailyItemAdatper;
import com.example.cheng.myfifthapplication.Bean.DailyInfo;
import com.example.cheng.myfifthapplication.Hleper.ActivityCollector;
import com.example.cheng.myfifthapplication.Hleper.DailyDataHelper;
import com.example.cheng.myfifthapplication.Hleper.FileHelper;
import com.example.cheng.myfifthapplication.Hleper.ImageDownloadHelper;
import com.example.cheng.myfifthapplication.Hleper.ImageConpressHelper;
import com.example.cheng.myfifthapplication.Hleper.ImageSelectHelper;
import com.example.cheng.myfifthapplication.Hleper.UpdateManage;
import com.example.cheng.myfifthapplication.R;
import com.example.cheng.myfifthapplication.util.Constant;
import com.example.cheng.myfifthapplication.util.LocalDisplay;
import com.example.cheng.myfifthapplication.util.ThemeUtils;

import java.io.File;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import me.drakeet.materialdialog.MaterialDialog;

public class MainActivity extends BaseActivity {
	public Toolbar toolbar;
	public FloatingActionButton fab;
	public DrawerLayout drawerLayout;
	//左侧栏
	public ActionBarDrawerToggle drawerToggle;
	private PtrFrameLayout ptrFrameLayout;
	//日志数据
	private DailyDataHelper dailyDataHelper;
	//当前所有的日志数据list
	private List<DailyInfo> itemlist;
	private ListView dailylistview;
	private DailyItemAdatper dailyItemAdatper;
	//首页背景图
	private ImageView wall_pic;
	//关于双击退出
	private MaterialDialog mMaterialDialog;
	private long exitTime = 0;
	private int topView;
	private final static int THEME_CHANGE = 1;
	private final static int LOGIN_STATE  = 2;
	private static final int PICK_PHOTO = 3;
	private static final int CROP_PHOTO = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initIntent();
		initToolbar();
		initPic();
		initDailyView();
		initFab();
		initDrawLayout();
		initTopView();
	}

	/**
	 * 初始化检测intent返回值
	 */
	private void initIntent() {
		Intent intent = getIntent();
		boolean isDownload = intent.getBooleanExtra("download_pic",false);
		intent.putExtra("download_pic",false);
		if(isDownload == true){
			DownloadFirstImageService();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode,resultCode,data);
		switch (requestCode) {
			case THEME_CHANGE://主题改变
				if (resultCode == RESULT_OK) {
					System.gc();
					recreate();
					onResume();
				}
				break;
			case LOGIN_STATE://登陆
				break;
			case PICK_PHOTO://图片选择
				if (resultCode == RESULT_OK) {
					int width = this.getWindowManager().getDefaultDisplay().getWidth()  ;
					int height = this.getWindowManager().getDefaultDisplay().getHeight() ;
					PhotoZoom(data.getData(), width, height/4);
					//剪切图片
				}
				break;
			case CROP_PHOTO://图片剪切
				if (resultCode == RESULT_OK) {
					Toast.makeText(MainActivity.this, "c", Toast.LENGTH_SHORT).show();
					saveBackground();
					System.gc();
					recreate();
					onResume();
				}
			default:
		}
	}

	/**
	 * 异步下载出场动画
	 */
	private void DownloadFirstImageService() {
			FileHelper.createFolder();
			String imgPath = Environment.getExternalStorageDirectory() + "/" + FileHelper.FOLDER + "/" + Constant.START_PIC;
			File imgFile = new File(imgPath);
			new ImageDownloadHelper(MainActivity.this, imgFile).Download();
	}

	@Override
	protected void onResume(){
		itemlist = dailyDataHelper.getDailyViewData();
		dailyItemAdatper.setItemlist(itemlist);
		showTopView();
		dailyItemAdatper.notifyDataSetChanged();
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		if((System.currentTimeMillis()-exitTime) > 2000) {
			Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		}
		else {
			ActivityCollector.finishAll();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		menuSelect(item);
		return true;
	}

	/**
	 * 初始化左侧栏
	 */
	public void initDrawLayout() {
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
		ListView mDrawerList = (ListView) findViewById(R.id.leftdraw_first);
		ListView mDrawerList_sec = (ListView) findViewById(R.id.leftdraw_sencond);
		final String[] mPlanetTitles = getResources().getStringArray(R.array.planets_array_first);
		String[] mPlanetTitles_sec = getResources().getStringArray(R.array.planets_array_second);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.ptr_textview,mPlanetTitles));
		mDrawerList_sec.setAdapter(new ArrayAdapter<String>(this,R.layout.ptr_textview,mPlanetTitles_sec));
		mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				switch (i){
					case 0://首页
						drawerLayout.closeDrawers();
						break;
					case 2://设置
						startSettingActivity();
						break;
					case 3://主题
						ThemeUtils.showColorDailog(MainActivity.this);
						break;
				}
			}
		});
		mDrawerList_sec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				switch (i) {
					case 0://反馈
						break;
					case 1://退出
						ActivityCollector.finishAll();
						break;
				}
			}
		});
	}

	//初始化head首页图片
	public void initPic(){
		String file_path = FileHelper.getFolder(true) +Constant.MAIN_WALL_PAPER_FILE_NAME;
		File wall_paper_file = new File(file_path);
		int width = this.getWindowManager().getDefaultDisplay().getWidth()  ;
		int height = this.getWindowManager().getDefaultDisplay().getHeight() ;

		wall_pic = new ImageView(this);
		wall_pic.setMaxHeight(height /5);
		wall_pic.setScaleType(ImageView.ScaleType.CENTER_CROP );

		if(wall_paper_file.exists()){
		 	Bitmap wall_paper = BitmapFactory.decodeFile(file_path);
			Drawable drawable = new BitmapDrawable(getResources(), wall_paper);
			wall_pic.setImageDrawable(drawable);
		}
		else{
			wall_pic.setImageDrawable(ImageConpressHelper.resizeImage3(getResources(), R.drawable.background_defalut, height / 3,width /3));
		}
		wall_pic.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				showDailog(getResources().getString(R.string.change_first_pic),getResources().getString(R.string.select_pic_from_photo));
				return true;
			}
		});
	}

	/**
	 * 初始化ToolBar和监听
	 */
	public void initToolbar() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		TypedArray array = getTheme().obtainStyledAttributes(new int[]{android.R.attr.colorPrimary});

		toolbar.setBackgroundColor(array.getColor(0, 0xFF00FF));
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				menuSelect(item);
				return true;
			}
		});
		setSupportActionBar(toolbar);
	}

	/**
	 * 初始化当前的日志数据
	 */
	public void initDailyData(){
		dailyDataHelper = new DailyDataHelper(MainActivity.this);
	}

	/**
	 * 初始化dailylistview和数据
	 */
	public void initDailyView(){
		dailylistview = (ListView)findViewById(R.id.daily_listview);
		dailylistview.addHeaderView(wall_pic, null, false);
		initDailyData();
		itemlist = dailyDataHelper.getDailyViewData();
		dailyItemAdatper = new DailyItemAdatper(MainActivity.this,itemlist);
		dailylistview.setAdapter(dailyItemAdatper);

		dailylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

					startChangeDailyActivity(itemlist.get(position - 1).getId());
					parent.getAdapter().getItem(position - 1);

			}
		});
		dailylistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
				final boolean top;
				if((dailyItemAdatper.getItem(i-1)).getId() == topView)
					top = true;
				else
				    top = false;

				dailylistview.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener(){
					public void onCreateContextMenu(ContextMenu menu, View v,
													ContextMenu.ContextMenuInfo menuInfo) {
						getMenuInflater().inflate(R.menu.menu_daily, menu);
						if(top == true) {
							menu.findItem(R.id.action_canel_set_to_top).setVisible(true);
							menu.findItem(R.id.action_set_to_top).setVisible(false);
						}
						else
						{
							menu.findItem(R.id.action_canel_set_to_top).setVisible(false);
							menu.findItem(R.id.action_set_to_top).setVisible(true);
						}
					}
				});
				return false;
			}
		});

		final MaterialHeader header = new MaterialHeader(this);
		header.setPadding(0,  LocalDisplay.dp2px(15), 0, 0);
		ptrFrameLayout = (PtrFrameLayout) findViewById(R.id.ptrFrameLayout);
		ptrFrameLayout.setHeaderView(header);
		ptrFrameLayout.addPtrUIHandler(header);
		ptrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				boolean refresh = isListViewReachTopEdge((ListView)content);
				return refresh;
			}
			@Override
			public void onRefreshBegin(final PtrFrameLayout frame) {
				frame.postDelayed(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(MainActivity.this,"刷新",Toast.LENGTH_SHORT).show();
						dailylistview.setAdapter(dailyItemAdatper);
						dailyItemAdatper.notifyDataSetChanged();
						frame.refreshComplete();
					}
				}, 2000);
			}
		});
	}

	/**
	 * 上下文item监听
	 * @param item
	 * @return
     */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
						.getMenuInfo();
		DailyInfo daily;
		if(info.position > 0) {
			daily = (DailyInfo) dailyItemAdatper.getItem(info.position - 1);
			switch (item.getItemId()) {
				case R.id.action_set_to_top:
					setTopView(daily);
					break;
				case R.id.action_canel_set_to_top:
					cancelTopView();
					break;
				case R.id.action_edit:
					startChangeDailyActivity(daily.getId());
					break;
				case R.id.action_remove:
					deleteDaily(daily.getId());
					break;
				default:
					break;
			}
		}
		return super.onContextItemSelected(item);
	}

	/**
	 * 初始化Fab
	 */
	public void initFab() {
		fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startNewDailyActivity();
			}
		});
	}

	public void menuSelect(MenuItem item)
	{
		int id = item.getItemId();
		switch (id)
		{
			case R.id.action_login:
				startLoginActivity();
				break;
			case R.id.action_new:
				Toast.makeText(MainActivity.this,"刷新",Toast.LENGTH_SHORT).show();
				ptrFrameLayout.autoRefresh();
				break;
			case R.id.action_add:
				startNewDailyActivity();
				break;
			case R.id.action_about:
				startAboutActivity();
				break;
			case R.id.action_update:
				new UpdateManage(MainActivity.this);
				break;
			case R.id.action_settings:
				startSettingActivity();
				break;
			default:
				break;
		}
	}

	/**
	 * 删除日志（上下文）
	 * @param id
     */
	private void deleteDaily(int id){
		dailyDataHelper.deleteDaily(id);
		FileHelper.deletePic(id);
		if(id == topView)
			cancelTopView();
		else
			onResume();
	}

	private void startLoginActivity() {
		Intent intent_login = new Intent(MainActivity.this, LoginActivity.class);
		MainActivity.this.startActivity(intent_login);
	}

	private void startNewDailyActivity() {
		Intent intent_add = new Intent(MainActivity.this, NewDailyActivity.class);
		MainActivity.this.startActivity(intent_add);
	}

	private void startChangeDailyActivity(int id){
		Intent intent_change = new Intent(MainActivity.this, ChangeDailyActivity.class);
		intent_change.putExtra("id",id);
		MainActivity.this.startActivity(intent_change);
	}

	private void startAboutActivity(){
		Intent intent_about = new Intent(MainActivity.this, AboutActivity.class);
		startActivity(intent_about);
	}

	private void startSettingActivity(){
		Intent intent_set = new Intent(MainActivity.this, SettingsActivity.class);
		startActivityForResult(intent_set, THEME_CHANGE);
	}

	/**
	 * 判断listview 是否到达顶部
	 * @param listView
	 * @return
     */
	public boolean isListViewReachTopEdge(final ListView listView){
		boolean result = false;
		if(dailyItemAdatper.isEmpty())
		{
			result = true;
		}
		else if(listView.getFirstVisiblePosition() == 0){
			final View topChildView = listView.getChildAt(0);
			result = topChildView.getTop() == 0;
		}

		return result;
	}


	private void showDailog(String title, String msg) {
		mMaterialDialog = new MaterialDialog(this)
				.setTitle(title)
				.setMessage(msg)
				.setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mMaterialDialog.dismiss();
						pick_photo();
					}
				})
				.setNegativeButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mMaterialDialog.dismiss();
					}
				});

		mMaterialDialog.show();
	}

	/**
	 * 图片选择
	 */
	private void pick_photo() {
		Intent pick_intent = ImageSelectHelper.pick_photo(MainActivity.this);
		if(pick_intent != null)
			startActivityForResult(pick_intent, PICK_PHOTO);
	}

	/**
	 * 图片剪切
	 * @param uri
	 * @param width
	 * @param height
     */
	private void PhotoZoom(Uri uri, int width, int height) {
		Intent intent = ImageSelectHelper.PhotoZoom(uri,width,height);
		startActivityForResult(intent, CROP_PHOTO);
	}

	/**
	 * 保存为首页head背景
	 */
	public void saveBackground() {
		String file_path = Environment.getExternalStorageDirectory() + "/" + FileHelper.FOLDER + "/" + Constant.WALL_PAPER_TEM;
		String wall_paper_file = Environment.getExternalStorageDirectory() + "/" + FileHelper.FOLDER + "/" + Constant.MAIN_WALL_PAPER_FILE_NAME;
		FileHelper.CopyFile(new File(file_path), new File(wall_paper_file));
	}

	/**
	 * 初始化获取日志置顶的id保存在变量topView中
	 */
	private void initTopView() {
		SharedPreferences pref =getSharedPreferences("Daily",0);
		topView = pref.getInt("topView",-1);
	}

	/**
	 * 显示置顶的日志
	 */
	private void showTopView(){
		if(topView!=-1){
			dailyItemAdatper.setTotop(dailyDataHelper.getDailyInfoById(topView));
		}
		Log.d("tag", "showTopView: "+topView);
	}

	/**
	 * 置顶日志
	 * @param daily
     */
	private void setTopView(DailyInfo daily){
		dailyItemAdatper.setTotop(daily);
		dailyItemAdatper.notifyDataSetChanged();
		topView = daily.getId();
		SharedPreferences pref =getSharedPreferences("Daily",0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt("topView",topView);
		editor.commit();
	}

	/**
	 * 取消置顶
	 */
	private void cancelTopView(){
		topView = -1;
		SharedPreferences pref =getSharedPreferences("Daily",0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putInt("topView",topView);
		editor.commit();
		onResume();
	}
}