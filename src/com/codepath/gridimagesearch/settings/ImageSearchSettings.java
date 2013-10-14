package com.codepath.gridimagesearch.settings;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.settings.queries.BaseImageSearchQuery;
import com.codepath.gridimagesearch.settings.queries.ImageColorFilterQuery;
import com.codepath.gridimagesearch.settings.queries.ImageSizeSearchQuery;

public class ImageSearchSettings extends Activity {

	ArrayList<BaseImageSearchQuery> options;
	ImageSettingArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_search_settings);

		setUpViews();
	}

	public void setUpViews() {
		ListView lv = (ListView) findViewById(R.id.settingView);

		this.options = new ArrayList<BaseImageSearchQuery>();

		this.options.add(new ImageSizeSearchQuery(this));
		this.options.add(new ImageColorFilterQuery(this));

		adapter = new ImageSettingArrayAdapter(this, options);
		lv.setAdapter(adapter);
	}

	public void updateSettings(View v) {
		ArrayList<String> params = new ArrayList<String>();
		
		for (int i = 0; i < this.options.size(); i++) {
			String param = this.options.get(i).getQueryParam();
			if (param != "") {
				params.add(param);
			}
		}
		
		Toast.makeText(getBaseContext(), params.toString(), Toast.LENGTH_SHORT).show();
		Intent data = new Intent();
		data.putExtra("params", params.toString());
		setResult(RESULT_OK, data);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_search_settings, menu);
		return true;
	}

}
