package com.majesty.sudokutrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	
	Button main_btn_level1=null;
	Button main_btn_level2=null;
	Button main_btn_level3=null;
	Button main_btn_quit=null;
	
	Button main_btn_about=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 

		main_btn_level1=(Button)findViewById(R.id.main_btn_level1);
		main_btn_level2=(Button)findViewById(R.id.main_btn_level2);
		main_btn_level3=(Button)findViewById(R.id.main_btn_level3);
		
		main_btn_level1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(MainActivity.this,PlayActivity.class);
				startActivity(intent);
			}
		});
		
		main_btn_level2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(MainActivity.this,NakedActivity.class);
				startActivity(intent);
			}
		});
		
		
		
		
		
		
		
		
		//ÍË³ö³ÌÐò
		main_btn_quit=(Button)findViewById(R.id.main_btn_quit);
		main_btn_quit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				System.exit(0);
			}
		});
		//aboutÒ³Ãæ
		main_btn_about=(Button)findViewById(R.id.main_btn_about);
		main_btn_about.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent_about=new Intent();
				intent_about.setClass(MainActivity.this, AboutActivity.class);
				startActivity(intent_about);
				
			}
		});
	}
}
