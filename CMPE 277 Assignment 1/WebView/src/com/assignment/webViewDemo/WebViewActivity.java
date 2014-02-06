package com.assignment.webViewDemo;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.webview);
	WebView webview=(WebView)findViewById(R.id.webview);
	webview.getSettings().setJavaScriptEnabled(true);
	webview.loadUrl("http://www.sjsu.edu");
	}

}
