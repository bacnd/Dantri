package com.luong.dantri;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.luong.dantri.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

public class NewsActivity extends Activity {

	WebView webview;
	String link, title, description, date;
	String detail = "";
	ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_news);

		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.actionbar_news);

		back = (ImageView) findViewById(R.id.back);
		webview = (WebView) findViewById(R.id.desc);
		Bundle bundle = getIntent().getExtras();
		link = bundle.getString("link");
		title = bundle.getString("title");
		description = bundle.getString("decription");
		date = bundle.getString("date");
		WebSettings webSettings = webview.getSettings();
		webSettings.setSupportZoom(true);

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		new GetData().execute();

	}

	public class GetData extends AsyncTask<Void, Void, Void> {

		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (pd == null) {
				pd = MainActivity.createProgressDialog(NewsActivity.this);
				pd.show();
			} else {
				pd.show();
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Document doc = Jsoup.connect(link).get();
				detail += "<h2 style = \" color: red \">" + title + "</h2>";
				detail += "<font size=\" 1.2em \" style = \" color: #005500 \"><em>"
						+ date + "</em></font>";
				detail += "<p style = \" color: #999999 \"><b>" + description
						+ "</b></p>";
				doc.select("table").remove();
				Elements e = doc.select("div.ovh.content ");
				Log.d("detail", e.toString());
				detail += e.toString();

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			webview.loadDataWithBaseURL(
					"",
					"<style>img{display: inline;height: auto;max-width: 100%;}"
							+ " p {font-family:\"Tangerine\", \"Sans-serif\",  \"Serif\" font-size: 48px} </style>"
							+ detail, "text/html", "UTF-8", "");
			if (pd.isShowing())
				pd.dismiss();
		}

	}
}
