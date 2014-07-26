package com.luong.dantri;

import java.util.ArrayList;
import java.util.List;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.luong.dantri.R;
import com.luong.adapter.MenuAdapter;
import com.luong.model.Item;

public abstract class BaseListSample extends FragmentActivity implements
		MenuAdapter.MenuListener {

	private static final String STATE_ACTIVE_POSITION = "LeftDrawerSample.activePosition";

	protected MenuDrawer mMenuDrawer;

	protected MenuAdapter mAdapter;
	protected ListView mList;

	private int mActivePosition = 0;

	@Override
	protected void onCreate(Bundle inState) {
		super.onCreate(inState);

		if (inState != null) {
			mActivePosition = inState.getInt(STATE_ACTIVE_POSITION);
		}

		mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.Type.BEHIND,
				getDrawerPosition(), getDragMode());
		
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(new Item("Trang chủ", R.drawable.trang_chu));
		items.add(new Item("Xã hội", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Thế giới", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Thể thao", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Giáo dục", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Nhân ái", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Kinh doanh", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Văn hóa", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Giải trí", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Pháp luật", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Nhịp sống trẻ", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Tình yêu", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Sức khỏe", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Sức mạnh số", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Ô tô -xe máy", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Bạn đọc", R.drawable.ic_action_select_all_dark));
		items.add(new Item("Việc làm", R.drawable.ic_action_select_all_dark));
	
		mList = new ListView(this);

        mAdapter = new MenuAdapter(this, items);
        mAdapter.setListener(this);
        mAdapter.setActivePosition(mActivePosition);

        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(mItemClickListener);

        mMenuDrawer.setMenuView(mList);

	}

	protected abstract void onMenuItemClicked(int position, Item item);

	protected abstract int getDragMode();

	protected abstract Position getDrawerPosition();

	private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			mActivePosition = position;
			mMenuDrawer.setActiveView(view, position);
			mAdapter.setActivePosition(position);
			onMenuItemClicked(position, (Item) mAdapter.getItem(position));
		}
	};

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_ACTIVE_POSITION, mActivePosition);
	}

	@Override
	public void onActiveViewChanged(View v) {
		mMenuDrawer.setActiveView(v, mActivePosition);
	}
}