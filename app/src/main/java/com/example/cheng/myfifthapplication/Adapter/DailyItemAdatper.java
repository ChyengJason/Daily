package com.example.cheng.myfifthapplication.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheng.myfifthapplication.Bean.DailyInfo;
import com.example.cheng.myfifthapplication.R;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by cheng on 16-2-13.
 * 日志信息的适配器
 */
public class DailyItemAdatper extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater = null;
	private List<DailyInfo> itemlist;

	public DailyItemAdatper(Context context,List<DailyInfo> itemlist){
		this.context = context;
		//根据context上下文加载布局
		this.inflater = LayoutInflater.from(context);
		this.itemlist = itemlist;
	}

	public  void setItemlist(List<DailyInfo> itemlist){
		this.itemlist = itemlist;
	}

	@Override
	public int getCount() {
		//在此适配器中所代表的数据集中的条目数
		return itemlist.size();
	}

	@Override
	public DailyInfo getItem(int position) {
		//获取数据集中与指定索引对应的数据项
		return itemlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		//获取在列表中与指定索引对应的行id
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		//如果缓存convertView为空，则需要创建View
//		Log.d("tag", "getView: "+position+(String)itemlist.get(position).getTitle());
		if(convertView ==null) {
			holder = new ViewHolder();
			//根据自定义的Item布局加载布局
			convertView = inflater.inflate(R.layout.daily_content_listview, null);
			holder.daily_date  = (TextView)convertView.findViewById(R.id.daily_date);
			holder.daily_time  = (TextView)convertView.findViewById(R.id.daily_time);
			holder.daily_title = (TextView)convertView.findViewById(R.id.daily_title);
			holder.daily_content = (TextView)convertView.findViewById(R.id.daily_content);
//			convertView.setOnClickListener(new Onclick());
			//将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder)convertView.getTag();
		}

		holder.daily_date.setText((String)itemlist.get(position).getDate());
		holder.daily_time.setText((String)itemlist.get(position).getTime());
		holder.daily_title.setText((String)itemlist.get(position).getTitle());
		holder.daily_content.setText((String)itemlist.get(position).getContent());

		return convertView;
	}


	public void setTotop(DailyInfo daily) {
		itemlist.remove(daily);
		itemlist.add(0,daily);
	}

	//ViewHolder静态类
	static class ViewHolder
	{
		public TextView daily_date;
		public TextView daily_time;
		public TextView daily_title;
		public TextView daily_content;
	}

}