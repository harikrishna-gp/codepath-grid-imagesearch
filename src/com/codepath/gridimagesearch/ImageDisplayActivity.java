package com.codepath.gridimagesearch;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.image.SmartImageView;

public class ImageDisplayActivity extends Activity {

	String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");
		SmartImageView ivImage = (SmartImageView) findViewById(R.id.ivResult);
		url = result.getFullUrl();
		ivImage.setImageUrl(url);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
		return true;
	}
	
	public void shareItem(MenuItem m) {
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		SmartImageView ivImage = (SmartImageView) findViewById(R.id.ivResult);
		Bitmap bitmap = ((BitmapDrawable) ivImage.getDrawable()).getBitmap();
//		String pathOfBmp = Images.Media.insertImage(getContentResolver(), bitmap, url, url);

		String path = Environment.getExternalStorageDirectory().toString();
		File file = new File(path, "bla.png");
		if (file.exists())
			file.delete();
		
		String pathOfBmp = "";
		try {
		       FileOutputStream out = new FileOutputStream(file);
		       bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
		       out.close();
		       
		} catch (Exception e) {
		       e.printStackTrace();
		}
		
		Uri bmpUri = Uri.parse("file://" + file.getAbsolutePath());

		shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
		shareIntent.setType("image/*");
		startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
		
	}

}
