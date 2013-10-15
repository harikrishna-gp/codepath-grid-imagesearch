package com.codepath.gridimagesearch.settings.queries;

import android.content.Context;

import com.codepath.gridimagesearch.R;

public class ImageTypeSearchQuery extends BaseImageSearchQuery {

	public ImageTypeSearchQuery(Context context) {
		super(context, R.array.image_type_array, R.string.image_type);
		
		this.queryParam = "imgtype";
	}
}
