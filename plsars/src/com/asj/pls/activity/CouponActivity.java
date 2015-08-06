package com.asj.pls.activity;

import com.asj.pls.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class CouponActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_coupon);
		initView();
	}
	
	private void initView(){
		
	}
}
