package com.codepath.gridimagesearch;

import java.util.ArrayList;

import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.codepath.gridimagesearch.settings.ImageSearchSettings;
import com.codepath.gridimagesearch.settings.queries.QueryParameter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {

	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ArrayList<QueryParameter> defaultParameters = new ArrayList<QueryParameter>();
	ArrayList<QueryParameter> queryParameters = new ArrayList<QueryParameter>();

	ImageResultArrayAdapter imageAdapter;
	int REQUEST_CODE = 123;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setUpViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent,
					int position, long rowId) {
				ImageResult imageInfo = (ImageResult) imageResults
						.get(position);
				Intent i = new Intent(getApplicationContext(),
						ImageDisplayActivity.class);
				i.putExtra("result", imageInfo);
				startActivity(i);
			}
		});
		defaultParameters.add(new QueryParameter("rsz", "8"));
		defaultParameters.add(new QueryParameter("start", "0"));
		defaultParameters.add(new QueryParameter("v", "1.0"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	public boolean launchSettings(MenuItem item) {
		Intent i = new Intent(this, ImageSearchSettings.class);
		startActivityForResult(i, REQUEST_CODE);
		return true;
	}

	public void setUpViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch = (Button) findViewById(R.id.btnSearch);
	}

	public void onImageSearch(View v) {
		String query = etQuery.getText().toString();
		Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT)
				.show();
		AsyncHttpClient client = new AsyncHttpClient();

		// ajax.googleapis.com/ajax/services/search/images?q=Android&v=1.0
		String baseUrl = "https://ajax.googleapis.com/ajax/services/search/images?";
		String defaultParams = URLEncodedUtils.format(defaultParameters,
				"UTF-8");
		String queryParams = URLEncodedUtils.format(queryParameters, "UTF-8");

		String url = baseUrl + defaultParams + "&" + queryParams + "&q="
				+ Uri.encode(query);
		
		Log.d("DEBUG", "Searching for " + url);

		client.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults = null;
				try {
					imageJsonResults = response.getJSONObject("responseData")
							.getJSONArray("results");
					imageResults.clear();
					imageAdapter.addAll(ImageResult
							.fromJSONArray(imageJsonResults));
					Log.d("DEBUG", imageResults.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	// / better way?
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			queryParameters = (ArrayList<QueryParameter>) data.getExtras().get(
					"params");
			String x = URLEncodedUtils.format(queryParameters, "UTF-8");
			Toast.makeText(this, "Got here" + x, Toast.LENGTH_SHORT).show();
		}
	}
}
