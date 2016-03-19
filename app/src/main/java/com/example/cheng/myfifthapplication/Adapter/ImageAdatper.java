package com.example.cheng.myfifthapplication.Adapter;

import android.widget.ImageView;

/**
 * 出场动画适配器
 * Created by cheng on 16-2-2.
 */
public class ImageAdatper {
	private int imageView;
	private String text;

	public ImageAdatper(int imageView, String text){
		super();
		this.imageView = imageView;
		this.text = text;
	}

	public int getImageView() {
		return imageView;
	}

	public void setImageView(int imageView) {
		this.imageView = imageView;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
