package com.codepath.gridimagesearch.settings.queries;

import java.util.ArrayList;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.R.array;
import com.codepath.gridimagesearch.settings.ImageSettingOption;

import android.content.Context;
import android.content.res.Resources;


public class ImageColorSearchQuery extends BaseImageSearchQuery {
		
	public ImageColorSearchQuery(Context context) {
		super(context, R.array.color_filter_array, R.string.color_filter);
		this.queryParam = "imgcolor";
		
	}
}
