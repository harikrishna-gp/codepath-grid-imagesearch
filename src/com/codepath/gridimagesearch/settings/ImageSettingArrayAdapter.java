package com.codepath.gridimagesearch.settings;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.settings.queries.BaseImageSearchQuery;

public class ImageSettingArrayAdapter extends
		ArrayAdapter<BaseImageSearchQuery> {

	ImageSettingArrayAdapter(Context context,
			List<BaseImageSearchQuery> imageSettingOptions) {
		super(context, R.layout.item_image_search_setting, imageSettingOptions);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		BaseImageSearchQuery queryOption = getItem(position);
		LinearLayout layoutRow;

		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			layoutRow = (LinearLayout) inflater.inflate(
					R.layout.item_image_search_setting, parent, false);
		} else {
			layoutRow = (LinearLayout) convertView;
		}

		TextView title = (TextView) layoutRow.findViewById(R.id.itemQuery);
		title.setText(queryOption.title);

		Spinner imageFilter = (Spinner) layoutRow
				.findViewById(R.id.itemSpinner);

		imageFilter.setOnItemSelectedListener(queryOption);
		
		ArrayAdapter<ImageSettingOption> imageFilterAdapter = new ArrayAdapter<ImageSettingOption>(
				layoutRow.getContext(), android.R.layout.simple_spinner_item,
				queryOption.getOptions());

		imageFilterAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		imageFilter.setAdapter(imageFilterAdapter);

		return layoutRow;
	}
}
