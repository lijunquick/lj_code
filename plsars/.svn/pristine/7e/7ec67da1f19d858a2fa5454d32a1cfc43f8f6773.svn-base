package com.asj.pls.activity;

import java.math.BigDecimal;

import com.asj.pls.R;
import com.asj.pls.init.Model;
import com.asj.pls.util.NetUtils;
import com.asj.pls.util.SPUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;

public class SplashActivity extends Activity {

	private static final int SUCCESS = 1; // 在线
	 private static final int OFFLINE = 2; // 离线

	 private static final int SHOW_TIME_MIN = 800;//毫秒
	 
	 public static String locationAddr;
	 
	 @Override
	 protected void onCreate(Bundle savedInstanceState) { 
		 super.onCreate(savedInstanceState); 
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setContentView(R.layout.activity_splash);
		 
		 new AsyncTask<Void, Void, Integer>() {
			 
			 @Override
			 protected Integer doInBackground(Void... params) {
				 int result;
				 long startTime = System.currentTimeMillis();
				 result = loadingCache();
				 long loadingTime = System.currentTimeMillis() - startTime;
				 if (loadingTime < SHOW_TIME_MIN) { 
					 try {
						 init();
						 Thread.sleep(SHOW_TIME_MIN - loadingTime); 
					 } catch (InterruptedException e) { 
						 e.printStackTrace(); 
					 } 
				 } 
				 return result;
			 } 
			 
			 @Override
			 protected void onPostExecute(Integer result) {//当后台操作结束时，此方法将会被调用
				 if(isFristRun()){
					 Intent intent = new Intent(SplashActivity.this,GuidanceActivity.class);
					 intent.putExtra("net_state", result);
					 startActivity(intent);
					 SplashActivity.this.finish();
				 }else{
					 Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
					 intent.putExtra("net_state", result);
					 startActivity(intent);
					 SplashActivity.this.finish();
				 }
			 };
			 
		 }.execute(new Void[]{});
	 }
	 
	 /** 
	  * 判断启动app网络状态
	  */
	 private int loadingCache() { 
		 if (NetUtils.isConnected(this)) { 
			 return SUCCESS; 
		 } 
		 return OFFLINE; 
	 }
	 
	 /** 
	  * 判断是否第一次启动app
	  */
	 private boolean isFristRun() {
		 boolean isFirstRun = SPUtils.contains("isFirstRun",this);
		 if (isFirstRun) {//非首次
			 return false;
		 } else {//首次
			 SPUtils.put("isFirstRun", false, this);
			 return true;
		 }
	 }
	 
	 /** 
	  * 启动时候加载的数据
	  */
	 private void init(){
		 Model.cartNum = 10;
		 Model.cartPrice = new BigDecimal("2607.70");
	 }
}
