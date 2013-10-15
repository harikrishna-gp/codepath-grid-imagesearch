package com.codepath.gridimagesearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
import android.widget.AbsListView;
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

	HashMap<String, String> queryParameters = new HashMap<String, String>();

	ImageResultArrayAdapter imageAdapter;
	String query;
	
	int REQUEST_CODE = 123;
	int NUM_PER_PAGE = 8;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setUpViews();
		setDefaultParams();

		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		
		clearResults();

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
		
		setUpScrolling();
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
	
	public void setUpScrolling() {
		gvResults.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void loadMore(int page, int totalItemsCount) {
				Log.d("DEBUG", "Scroll loading page " + page);
				queryParameters.put("start", String.valueOf((page) * NUM_PER_PAGE));
				SearchActivity.this.loadSearch();
			}
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			    
			}
		});
	}

	public void clearResults() {
		imageAdapter.clear();
		queryParameters.put("start", "0");
	}
	
	public void setDefaultParams() {
		queryParameters.clear();
		queryParameters.put("rsz", String.valueOf(NUM_PER_PAGE));
		queryParameters.put("v", "1.0");
	}

	public String getQueryParams() {
		ArrayList<QueryParameter> namedValuePairs = new ArrayList<QueryParameter>();

		Iterator<String> iter = queryParameters.keySet().iterator();

		while (iter.hasNext()) {
			String key = iter.next();
			namedValuePairs.add(new QueryParameter(key, queryParameters
					.get(key)));
		}

		String params = URLEncodedUtils.format(namedValuePairs, "UTF-8");
		return params;
	}

	public void loadSearch() {
		AsyncHttpClient client = new AsyncHttpClient();

		// ajax.googleapis.com/ajax/services/search/images?q=Android&v=1.0
		String baseUrl = "https://ajax.googleapis.com/ajax/services/search/images";

		queryParameters.put("q", Uri.encode(query));
		String params = this.getQueryParams();
		String url = baseUrl + "?" + params;

		Log.d("DEBUG", "Searching for " + url);

		client.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults = null;
				try {				
					if (response.get("responseData") != JSONObject.NULL) {
					  JSONObject responseData = response.getJSONObject("responseData");
  					  imageJsonResults = responseData.getJSONArray("results");
					  imageAdapter.addAll(ImageResult
							  .fromJSONArray(imageJsonResults));
					}
					else {
						Log.d("debug", "No response made from API call -- possible max reached");
					}
					//Log.d("DEBUG", imageResults.toString());
				} catch (JSONException e) {
					String stackTrace = Log.getStackTraceString(e);
					Log.d("debug", stackTrace);
				}
			}
			
		});
	}

	public void onImageSearch(View v) {
		clearResults();
		
		query = etQuery.getText().toString();
		Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT)
				.show();

		this.loadSearch();
	}

	@Override
	@SuppressWarnings("unchecked")
	// better way?
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			ArrayList<QueryParameter> params = (ArrayList<QueryParameter>) data
					.getExtras().get("params");

			for (QueryParameter param : params) {
				queryParameters.put(param.getName(), param.getValue());
			}

			String x = URLEncodedUtils.format(params, "UTF-8");
			Log.d("debug", "Got here" + x);
		}
	}
}
