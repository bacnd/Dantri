package com.luong.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.luong.dantri.R;
import com.luong.model.Item;

public class MenuAdapter extends BaseAdapter {

	public interface MenuListener {

		void onActiveViewChanged(View v);
	}

	private Context mContext;

	private ArrayList<Item> mItems;

	private MenuListener mListener;

	private int mActivePosition = -1;

	public MenuAdapter(Context context, ArrayList<Item> items) {
		mContext = context;
		mItems = items;
	}

	public void setListener(MenuListener listener) {
		mListener = listener;
	}

	public void setActivePosition(int activePosition) {
		mActivePosition = activePosition;
	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean isEnabled(int position) {
		return getItem(position) instanceof Item;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		Object item = getItem(position);

		if (v == null) {
			v = LayoutInflater.from(mContext).inflate(R.layout.menu_row_item,
					parent, false);
		}
		
		TextView tv = (TextView) v;
		tv.setText((mItems.get(position).mTitle));
		Log.d(position + "", ((Item) item).mTitle.toString() );
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			tv.setCompoundDrawablesRelativeWithIntrinsicBounds(
					((Item) item).mIconRes, 0, 0, 0);
		} else {
			tv.setCompoundDrawablesWithIntrinsicBounds(
					((Item) item).mIconRes, 0, 0, 0);
		}

		v.setTag(R.id.mdActiveViewPosition, position);

		if (position == mActivePosition) {
			mListener.onActiveViewChanged(v);
		}

		return v;
	}
}