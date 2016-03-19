package com.example.cheng.myfifthapplication.Hleper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

/**
 * Created by cheng on 16-3-17.
 * 图片选择以及剪切
 */
public class ImageSelectHelper  {

    public static Intent pick_photo(Context context){
        Intent pick_intent = new Intent(Intent.ACTION_PICK, null);
        if (!FileHelper.isSdMounted() || !FileHelper.createFolder()) {
            Toast.makeText(context, "读取错误", Toast.LENGTH_SHORT).show();
            return null;
        }
        pick_intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        return  pick_intent;
    }

    public static Intent PhotoZoom(Uri uri, int width, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", width);
        intent.putExtra("aspectY", height);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("return-data", false);

        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);

        Uri picture_uri = FileHelper.getTempUri();
        if (picture_uri == null)
            return null;

        intent.putExtra(MediaStore.EXTRA_OUTPUT, picture_uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        return intent;
    }
}
