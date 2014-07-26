package com.luong.dantri;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import java.util.List;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.BadTokenException;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.luong.dantri.R;
import com.luong.adapter.MainAdapter;
import com.luong.model.Count;
import com.luong.model.Item;
import com.luong.rss.RSSItem;
import com.luong.rss.RssParser;
import com.startapp.android.publish.StartAppAd;

public class MainActivity extends BaseListSample {

	private static final String STATE_CONTENT_TEXT = "LeftDrawerSample.contentText";
	private String mContentText;
	ImageView home, refresh;
	TextView title;
	ListView list;
	List<RSSItem> items;
	RssParser rssParser = new RssParser();
	MainAdapter adapter;
	private int currentPosition = 0;

	@Override
	protected void onCreate(Bundle inState) {
		super.onCreate(inState);

		if (inState != null) {
			mContentText = inState.getString(STATE_CONTENT_TEXT);
		}
		
		StartAppAd.init(this, "107517432", "207327544");

		mMenuDrawer.setContentView(R.layout.activity_main);
		mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
		mMenuDrawer.setSlideDrawable(R.drawable.ic_drawer);
		mMenuDrawer.setDrawerIndicatorEnabled(true);

		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.actionbar_custom);

		items = new ArrayList<RSSItem>();
		list = (ListView) findViewById(R.id.listview1);
		home = (ImageView) findViewById(R.id.home);
		title = (TextView) findViewById(R.id.title);
		refresh = (ImageView) findViewById(R.id.refresh);

		new GetListItem(this).execute();

		refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Animation myRotation = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.rotator);
				refresh.startAnimation(myRotation);
			}
		});

		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mMenuDrawer.toggleMenu();
			}
		});

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MainActivity.this,
						NewsActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("link", items.get(position).get_link());
				bundle.putString("title", items.get(position).get_title());
				bundle.putString("decription", items.get(position).get_description());
				bundle.putString("date", items.get(position).get_pubdate());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		mMenuDrawer
				.setOnInterceptMoveEventListener(new MenuDrawer.OnInterceptMoveEventListener() {
					@Override
					public boolean isViewDraggable(View v, int dx, int x, int y) {
						return v instanceof SeekBar;
					}
				});

	}

	@Override
	protected void onMenuItemClicked(int position, Item item) {
		// mContentTextView.setText(item.mTitle);
		title.setText(item.mTitle);
		if (position != currentPosition) {
			currentPosition = position;
			new GetListItem(this).execute();
		}

		Log.d("position", position + "");
		mMenuDrawer.closeMenu();

	}

	@Override
	protected int getDragMode() {
		return MenuDrawer.MENU_DRAG_CONTENT;
	}

	@Override
	protected Position getDrawerPosition() {
		return Position.START;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(STATE_CONTENT_TEXT, mContentText);
	}

	@Override
	public void onBackPressed() {
		final int drawerState = mMenuDrawer.getDrawerState();
		if (drawerState == MenuDrawer.STATE_OPEN
				|| drawerState == MenuDrawer.STATE_OPENING) {
			mMenuDrawer.closeMenu();
			return;
		}

		super.onBackPressed();
	}

	public static ProgressDialog createProgressDialog(Context mContext) {
		ProgressDialog dialog = new ProgressDialog(mContext);
		try {
			dialog.show();
		} catch (BadTokenException e) {

		}
		dialog.setCancelable(false);
		dialog.setContentView(R.layout.progress_dialog);
		// dialog.setMessage(Message);
		return dialog;
	}

	public class GetListItem extends AsyncTask<Void, Void, Void> {

		Context context;
		ProgressDialog pd;

		GetListItem(Context context) {
			this.context = context;
		}

		protected void onPreExecute() {
			if (pd == null) {
				pd = MainActivity.createProgressDialog(MainActivity.this);
				pd.show();
			} else {
				pd.show();
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			items = rssParser.getRSSFeedItems(Count.RSS[currentPosition]);			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (pd.isShowing())
				pd.dismiss();
			adapter = new MainAdapter(MainActivity.this, items);
			list.setAdapter(adapter);
		}

	}
}
