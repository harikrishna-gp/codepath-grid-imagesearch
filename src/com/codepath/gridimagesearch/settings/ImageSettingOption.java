package com.codepath.gridimagesearch.settings;

import java.util.ArrayList;
import java.util.Arrays;

public class ImageSettingOption {
	
	public String id;
	public String displayName;
	
	public ImageSettingOption(String id, String displayName) {
		this.id = id;
		this.displayName = displayName;
	}
	
	public String toString() {
		return this.displayName;
	}
};
