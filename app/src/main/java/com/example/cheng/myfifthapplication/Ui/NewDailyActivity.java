package com.example.cheng.myfifthapplication.Ui;

import android.content.ClipboardManager;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.cheng.myfifthapplication.Hleper.DailyDataHelper;
import com.example.cheng.myfifthapplication.Hleper.FileHelper;
import com.example.cheng.myfifthapplication.Hleper.ImageShareHelper;
import com.example.cheng.myfifthapplication.R;
import com.example.cheng.myfifthapplication.util.ColorPanel;
import com.example.cheng.myfifthapplication.util.Constant;

import java.io.File;

/**
 * Created by cheng on 16-3-3.
 * 新建日志
 */
public class NewDailyActivity extends EditActivity {
    private com.rengwuxian.materialedittext.MaterialEditText titleEdit;
    private com.rengwuxian.materialedittext.MaterialEditText contentEdit;
    private Toolbar toolbar;
    private String newColor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initEditText();
        initToolBar();
        initBackground();
    }

    /**
     * 初始化加载日志背景图片
     */
    public void initBackground() {
            loadWallpaper();
    }

    /**
     * 设置为单个日志的背景图
     */
    @Override
    public void setSpecialLayoutBackground() {
        String file_path = Environment.getExternalStorageDirectory() + "/" + FileHelper.FOLDER + "/" + Constant.WALL_PAPER_TEM;
        String wall_paper_file = Environment.getExternalStorageDirectory() + "/" + FileHelper.FOLDER + "/" + "tem_"+Constant.WALL_PAPER_FILE_NAME;
        FileHelper.CopyFile(new File(file_path), new File(wall_paper_file));
        LoadBitmap(wall_paper_file);
    }

    /**
     * 术石化Toolbar
     */
    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.add_toolbar);
        TypedArray array = getTheme().obtainStyledAttributes(new int[]{android.R.attr.colorPrimary});
        toolbar.setBackgroundColor(array.getColor(0, 0xFF00FF));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(saveDaily()) {
                    onBackPressed();
                }
                else{
                    Toast.makeText(NewDailyActivity.this,R.string.warn_title_is_null,Toast.LENGTH_SHORT).show();
                }
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.add_finish:
                        saveDaily();
                        onBackPressed();
                        break;
                    case R.id.add_delete:
                        deleteDaily();
                        deleteOldPic();
                        onBackPressed();
                        break;
                    case R.id.add_share:
                        copyToBoard();
                        break;
                    case R.id.change_background:
                        pick_photo();
                        break;
                    case R.id.add_change_to_pic:
                        ImageShareHelper imgeHelper = new ImageShareHelper();
                        imgeHelper.savePic(imgeHelper.createPic(getLayout()),NewDailyActivity.this);
                        Toast.makeText(NewDailyActivity.this,"已生成图片存至相册",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.add_change_word_color:
                        showColorDailog();
                        break;
                    case R.id.delete_background:
                        deletePic();
                        recreate();
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 保存日志
     * @return
     */
    public boolean saveDaily() {
        String title = titleEdit.getText().toString();
        String content = contentEdit.getText().toString();
        if(title.equals(""))
        {
            return false;
        }
        else{
            DailyDataHelper dailyDataHelper = new DailyDataHelper(NewDailyActivity.this);
            if(dailyDataHelper.newDaily(title,content,newColor)) {
                String wall_paper_file = Environment.getExternalStorageDirectory() + "/" + FileHelper.FOLDER + "/" + "tem_"+Constant.WALL_PAPER_FILE_NAME;
                File wall_file = new File(wall_paper_file);
                if(FileHelper.hasFile(wall_file)) {
                    String new_wall_file = Environment.getExternalStorageDirectory() + "/" + FileHelper.FOLDER + "/" + dailyDataHelper.getLastId()+Constant.WALL_PAPER_FILE_NAME;
                    FileHelper.renameFile(wall_file,new File((new_wall_file)));
                }
                return true;
            }
            else return false;
        }
    }

    /**
     * 删除日志
     */
    @Override
    public void deleteDaily() {
        onBackPressed();
    }


    /**
     * 删除已经存在的背景图片（当有新壁纸时使用）
     */
    public void deleteOldPic(){
        FileHelper.deletePic("tem_");
    }


    protected void initEditText(){
        titleEdit = (com.rengwuxian.materialedittext.MaterialEditText)findViewById(R.id.label_edit_text);
        contentEdit = (com.rengwuxian.materialedittext.MaterialEditText)findViewById(R.id.content_edit_text);
        newColor = ColorPanel.getDefultColor();
    }

    public void onBackPressed(){
        super.onBackPressed();
    }

    public void copyToBoard(){
        ClipboardManager cmb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        cmb.setText("title:"+titleEdit.getText().toString()+"\ncontent:"+contentEdit.getText().toString());
        Toast.makeText(this,"已复制到剪切板",Toast.LENGTH_SHORT).show();
    }

    /**
     * 改变字体颜色
     * @param color
     */
    public void changeWordColor(int color){
        contentEdit.setTextColor(color);
        titleEdit.setTextColor(color);
        newColor = ColorPanel.int2stringColor(color);
    }

    /**
     * 删除背景图片
     */
    public void deletePic(){
        String wall_paper_file = Environment.getExternalStorageDirectory() + "/" + FileHelper.FOLDER + "/" + Constant.WALL_PAPER_FILE_NAME;
        File wall_paper = new File(wall_paper_file);
        if(FileHelper.hasFile(wall_paper))
            FileHelper.deletePic("");
    }
}
