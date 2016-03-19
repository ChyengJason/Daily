package com.example.cheng.myfifthapplication.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import com.example.cheng.myfifthapplication.R;
import com.turkialkhateeb.materialcolorpicker.ColorChooserDialog;
import com.turkialkhateeb.materialcolorpicker.ColorListener;

/**
 * Created by cheng on 16-3-11.
 * 主题管理
 */
public class ThemeUtils {
    private static int sTheme;

    public final static int THEME_DEFAULT = 0;
    public final static int THEME_WHITE = 1;
    public final static int THEME_BLUE = 2;

    /**
     * 显示颜色面板
     * @param activity
     */
    public static void showColorDailog(final Activity activity){
        ColorChooserDialog dialog = new ColorChooserDialog(activity);
        dialog.setColorListener(new ColorListener() {
            @Override
            public void OnColorClick(View v, int color) {
                changeTheme(activity,color);
            }
        });
        dialog.show();
    }


    private static void changeTheme(Activity activity, int color) {
        Intent intent = new Intent();
        activity.setResult(activity.RESULT_OK, intent);
        ThemeUtils.changeToTheme(activity,color);
    }

    public static void changeToTheme(Activity activity, int color)
    {
        sTheme = ColorToTheme(color);
        SharedPreferences pref = activity.getSharedPreferences("Daily",0);
        SharedPreferences.Editor editor = pref.edit();
//        editor.clear();
        editor.putInt("Theme",sTheme);
        editor.commit();
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    private static int ColorToTheme(int color) {
        int theme;
        switch (color){
            case ColorPanel.red:
                theme = 1;
                break;
            case ColorPanel.pink:
                theme = 2;
                break;
            case ColorPanel.Purple:
                theme = 3;
                break;
            case ColorPanel.DeepPurple:
                theme = 4;
                break;
            case ColorPanel.Indigo:
                theme = 5;
                break;
            case ColorPanel.Blue:
                theme = 6;
                break;
            case ColorPanel.LightBlue:
                theme = 7;
                break;
            case ColorPanel.Cyan:
                theme = 8;
                break;
            case ColorPanel.Teal:
                theme = 9;
                break;
            case ColorPanel.Green:
                theme = 10;
                break;
            case ColorPanel.LightGreen:
                theme = 11;
                break;
            case ColorPanel.Lime:
                theme = 12;
                break;
            case ColorPanel.Yellow:
                theme = 13;
                break;
            case ColorPanel.Amber:
                theme = 14;
                break;
            case ColorPanel.Orange:
                theme = 15;
                break;
            case ColorPanel.DeepOrange:
                theme = 16;
                break;
            case ColorPanel.Brown:
                theme = 17;
                break;
            case ColorPanel.Grey:
                theme = 18;
                break;
            case ColorPanel.BlueGray:
                theme = 19;
                break;
            case ColorPanel.Black:
                theme = 20;
                break;
            default:
                theme = 1;
                break;
        }
        return theme;
    }

    public static void onActivityCreateSetTheme(Activity activity)
    {

        SharedPreferences pref = activity.getSharedPreferences("Daily",0);
        sTheme = pref.getInt("Theme",0);
        switch (sTheme)
        {
            case 1:
                activity.setTheme(R.style.RedTheme);
                break;
            case 2:
                activity.setTheme(R.style.PinkTheme);
                break;
            case 3:
                activity.setTheme(R.style.PurpleTheme);
                break;
            case 4:
                activity.setTheme(R.style.DeepPurpleTheme);
                break;
            case 5:
                activity.setTheme(R.style.IndigoTheme);
                break;
            case 6:
                activity.setTheme(R.style.BlueTheme);
                break;
            case 7:
                activity.setTheme(R.style.LightBlueTheme);
                break;
            case 8:
                activity.setTheme(R.style.CyanTheme);
                break;
            case 9:
                activity.setTheme(R.style.TealTheme);
                break;
            case 10:
                activity.setTheme(R.style.GreenTheme);
                break;
            case 11:
                activity.setTheme(R.style.LightGreenTheme);
                break;
            case 12:
                activity.setTheme(R.style.LimeTheme);
                break;
            case 13:
                activity.setTheme(R.style.YellowTheme);
                break;
            case 14:
                activity.setTheme(R.style.AmberTheme);
                break;
            case 15:
                activity.setTheme(R.style.OrangeTheme);
                break;
            case 16:
                activity.setTheme(R.style.DeepOrangeTheme);
                break;
            case 17:
                activity.setTheme(R.style.BrownTheme);
                break;
            case 18:
                activity.setTheme(R.style.GreyTheme);
                break;
            case 19:
                activity.setTheme(R.style.BlueGrayTheme);
                break;
            case 20:
                activity.setTheme(R.style.BlackTheme);
                break;
            default:
                activity.setTheme(R.style.BlueTheme);
                break;
        }
    }

    public static int getsTheme() {
        return sTheme;
    }
}
