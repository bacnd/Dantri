package com.luong.adapter;



import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.luong.dantri.R;
import com.luong.img.AppController;
import com.luong.rss.RSSItem;

public class MainAdapter extends BaseAdapter {

	Context context;
	List<RSSItem> items;

	public MainAdapter(Context context, List<RSSItem> items) {
		this.context = context;
		this.items = items;
	}

	static class ViewHolder {
		TextView title;
		TextView date;
		ImageView img;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageLoader imageLoader = AppController.getInstance().getImageLoader();
		ViewHolder viewHolder;
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_row, null);
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			viewHolder.date = (TextView) convertView.findViewById(R.id.time);
			viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.title.setText(items.get(position).get_title());
		viewHolder.date.setText(items.get(position).get_pubdate());
//		viewHolder.img.setImageResource()
		imageLoader.get(items.get(position).get_img(), ImageLoader.getImageListener(
				viewHolder.img, R.drawable.ic_launcher, R.drawable.ic_launcher));
		

		return convertView;
	}

}
