package com.majesty.sudokutrainer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class AboutActivity extends Activity {

	WebView about_wv=null;
	Button about_btn_close=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);


		about_wv=(WebView)findViewById(R.id.about_wv);
		about_wv.loadUrl("file:///android_asset/about.html");
		
		
		about_btn_close=(Button)findViewById(R.id.about_btn_close);
		about_btn_close.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AboutActivity.this.finish();
			}
		});
	}
}
