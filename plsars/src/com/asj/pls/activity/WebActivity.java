package com.asj.pls.activity;

import com.asj.pls.R;
import com.asj.pls.util.DialogUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class WebActivity extends Activity implements OnClickListener{

	/** 
     * 返回
     */
    private ImageView backWebview;
    
    /**
	 * 删除按钮的引用
	 */
    private WebView myWebView;
    
    /** 
     * 加载控件
     */
    private Dialog dialog;
    
    @SuppressLint({"JavascriptInterface","SetJavaScriptEnabled"})
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_web);
		
		Intent intent = getIntent();
		String url = intent.getStringExtra("url");
		
		backWebview = (ImageView) findViewById(R.id.back_webview);
		myWebView = (WebView)findViewById(R.id.webview_common);
		WebSettings settings = myWebView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDefaultTextEncodingName("utf-8"); 
		myWebView.addJavascriptInterface(new JsInteration(), "control");
		myWebView.setWebViewClient(new WebViewClient() {
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				dialog = DialogUtils.createLoadingDialog(view.getContext(), "页面加载中...");
                dialog.show();
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				dialog.dismiss();
			}
			
		});
		myWebView.loadUrl(url);
		backWebview.setOnClickListener(this);
	}
	
	public class JsInteration {
		@JavascriptInterface
		public void addCart(long id) {
	    	  
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.back_webview:
			this.finish();
			break;
		}
	}
}
