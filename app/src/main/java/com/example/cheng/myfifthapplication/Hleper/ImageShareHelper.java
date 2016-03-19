package com.example.cheng.myfifthapplication.Hleper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.provider.MediaStore;
import android.widget.FrameLayout;
/**
 * Created by cheng on 16-3-13.
 * 图片生成
 */
public class ImageShareHelper extends ShapeDrawable {

    public Bitmap createPic(FrameLayout contentview){
        // 创建对应大小的bitmap
        Bitmap bitmap = Bitmap.createBitmap(contentview.getWidth(),contentview.getHeight() ,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        contentview.draw(canvas);

        Bitmap newbmp = Bitmap.createBitmap(contentview.getWidth(),contentview.getHeight() ,Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newbmp);
        cv.drawColor(Color.WHITE);
        cv.drawBitmap(bitmap, 0, 0, null);

        canvas.save(Canvas.ALL_SAVE_FLAG);// 保存
        canvas.restore();
        bitmap.recycle();
        return newbmp;
    }

    public void savePic(Bitmap bitmap,Context context){
        MediaStore.Images.Media.insertImage(context.getContentResolver(),bitmap, "daily", "description");
    }
}