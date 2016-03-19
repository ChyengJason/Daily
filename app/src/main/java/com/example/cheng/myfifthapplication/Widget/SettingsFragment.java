package com.example.cheng.myfifthapplication.Widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.cheng.myfifthapplication.Hleper.FileHelper;
import com.example.cheng.myfifthapplication.R;
import com.example.cheng.myfifthapplication.Ui.SettingsActivity;
import com.example.cheng.myfifthapplication.util.ColorPanel;
import com.example.cheng.myfifthapplication.util.Constant;
import com.example.cheng.myfifthapplication.util.ThemeUtils;
import com.jenzz.materialpreference.CheckBoxPreference;
import com.jenzz.materialpreference.Preference;
import com.turkialkhateeb.materialcolorpicker.ColorChooserDialog;
import com.turkialkhateeb.materialcolorpicker.ColorListener;

import java.io.File;

/**
 * Created by cheng on 16-3-9.
 */
public class SettingsFragment extends PreferenceFragment {
    private CheckBoxPreference firstPicPreference;
    private Preference clearPicPreference;
    private boolean isDownloadPic;
    private Context context;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SettingsFragment newInstance(Context context){
        SettingsFragment fragment = new SettingsFragment();
        this.context = context;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        initPerference();
        initDownloadPic();
    }

    private void initDownloadPic() {
        pref = getActivity().getSharedPreferences("Daily",0);
        isDownloadPic = pref.getBoolean(Constant.START_PIC, true);
        if(isDownloadPic == true){
            firstPicPreference.setChecked(true);
        }
        else{
            firstPicPreference.setChecked(false);
        }
    }

    private void initPerference() {
        firstPicPreference = (CheckBoxPreference)findPreference(getString(R.string.download_pic));
        clearPicPreference = (Preference)findPreference((getString(R.string.clear_all_bg)));
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, android.preference.Preference preference) {
        onPreferenceTreeClick((Preference) preference);
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    private boolean onPreferenceTreeClick(Preference preference) {
        if (preference == null){
            return false;
        }
        String key = preference.getKey();
        if(TextUtils.equals(key, getString(R.string.download_pic))){
            changeDownloadPic();
        }
        if(TextUtils.equals(key, getString(R.string.clear_all_bg))){
            deletePicCatagory();
        }
        if(TextUtils.equals(key, getString(R.string.change_theme))){
            ThemeUtils.showColorDailog(getActivity());
        }
        return true;
    }

//    private void changeTheme(int color) {
//        Intent intent = new Intent();
//        getActivity().setResult(getActivity().RESULT_OK, intent);
//       ThemeUtils.changeToTheme(getActivity(),color);
//    }

    private void changeDownloadPic() {
        editor = pref.edit();
        if(isDownloadPic == false){
            editor.putBoolean(Constant.START_PIC, true);
        }
        else{
            editor.putBoolean(Constant.START_PIC, false);
        }
        editor.commit();
    }

    private void deletePicCatagory() {
        String file_path = Environment.getExternalStorageDirectory() + "/" + FileHelper.FOLDER + "/";
        File file = new File(file_path);
        FileHelper.deleteCatagory(file);
        Toast.makeText(getActivity(),"已清除所有背景",Toast.LENGTH_SHORT).show();
    }

//    private void showColorDailog(){
//        ColorChooserDialog dialog = new ColorChooserDialog(getActivity());
//        dialog.setColorListener(new ColorListener() {
//            @Override
//            public void OnColorClick(View v, int color) {
//               changeTheme(color);
//            }
//        });
//        dialog.show();
//    }
}
