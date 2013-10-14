package com.codepath.gridimagesearch.settings.queries;

import java.util.ArrayList;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.R.array;
import com.codepath.gridimagesearch.settings.ImageSettingOption;

import android.content.Context;
import android.content.res.Resources;

public class ImageSizeSearchQuery extends BaseImageSearchQuery {

	public ImageSizeSearchQuery(Context context) {
		super(context, R.array.image_size_array, R.string.image_size);
		
		this.queryParam = "imgsz";
	}
}
