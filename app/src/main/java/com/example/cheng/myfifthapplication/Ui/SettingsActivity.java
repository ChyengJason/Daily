package com.example.cheng.myfifthapplication.Ui;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.cheng.myfifthapplication.Widget.SettingsFragment;
import com.example.cheng.myfifthapplication.R;

/**
 * Created by cheng on 16-2-15.
 * 应用设置
 */
public class SettingsActivity extends BaseActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SettingsFragment settingsFragment = new SettingsFragment().newInstance(SettingsActivity.this);
        getFragmentManager().beginTransaction().replace(R.id.fragment_content, settingsFragment).commit();
        initToolbar();
    }

    private void initToolbar() {
        toolbar = (Toolbar)findViewById(R.id.back_toolbar);
        TypedArray array = getTheme().obtainStyledAttributes(new int[]{android.R.attr.colorPrimary});
        toolbar.setBackgroundColor(array.getColor(0, 0xFF00FF));
        toolbar.setTitle(R.string.setting);
        toolbar.collapseActionView();
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

}
