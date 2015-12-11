package com.phonemate.activity.webview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.phonemate.R;
import com.phonemate.base.BaseActivity;
import com.phonemate.utils.MessageUtils;
import com.rxx.fast.view.ViewInject;

/**
 * 
 * 加载在线网页activity 需要传入loadUrl
 * @author Ran
 *
 */
public class WebViewOnlineActivity extends BaseActivity implements OnClickListener,DownloadListener{

	@ViewInject(id = R.id.mTvLeft, click = true)
	private TextView mTvLeft;
	@ViewInject(id = R.id.mTvRight, click = true)
	private TextView mTvRight;
	@ViewInject(id = R.id.mTvCentre, click = true)
	private TextView mTvCentre;

	private WebView mWebView;
	private WebSettings mWebSettings;
	private ProgressBar mProgressBar;
	
	/**
	 *1、LoadUrl            直接加载网页、图片并显示.（本地或是网络上的网页、图片、gif）  
     *2、LoadData           显示文字与图片内容 （模拟器1.5、1.6）  
     *3、LoadDataWithBase   显示文字与图片内容（支持多个模拟器版本） 
	 */
	private int loadType;
	private String loadUrl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview_online);
	}

	@Override
	public void bingViewFinish() {
		mTvLeft.setText("返回");
		mTvRight.setVisibility(View.INVISIBLE);

		mTvRight.setOnClickListener(this);
		mTvLeft.setOnClickListener(this);

		mTvLeft.setText("返回");
		mTvCentre.setText("");
		mTvRight.setText("复制链接");
		mTvRight.setVisibility(View.INVISIBLE);
		mTvLeft.setOnClickListener(this);

		mWebView=(WebView)findViewById(R.id.webview);
		mProgressBar=(ProgressBar)findViewById(R.id.progressbar);

		mWebSettings=mWebView.getSettings();
		//允许javaScript
		mWebSettings.setJavaScriptEnabled(true);
		mWebSettings.setBuiltInZoomControls(true);
		mWebSettings.setSupportZoom(true);
		mWebSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
		//如果webView中需要用户手动输入用户名、密码或其他，则webview必须设置支持获取手势焦点。
		mWebView.requestFocusFromTouch();

		mWebView.setWebChromeClient(new MyWebChromeClient());

		mWebView.setWebViewClient(new MyWebViewClient());
		mWebView.setDownloadListener(this);


		loadUrl=getIntent().getStringExtra("loadUrl");
		if(TextUtils.isEmpty(loadUrl)){
			MessageUtils.alertMessageCENTER("加载网页地址不能为空.");
			mActivity.finish();
		}
		mWebView.loadUrl(loadUrl);
	}

	class MyWebViewClient extends WebViewClient{
		  @Override
	      public boolean shouldOverrideUrlLoading(WebView view, String url) {
	          view.loadUrl(url);
	          return true;
	      }
		  
		  @Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			mProgressBar.setVisibility(View.VISIBLE);
		}
	}
	
	class MyWebChromeClient extends WebChromeClient{
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
			if(newProgress==100){
				mProgressBar.setProgress(newProgress);
				mProgressBar.setVisibility(View.GONE);
			}else{
				mProgressBar.setProgress(newProgress);
			}
		}
		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			mTvCentre.setText(title);
		}

	}
	
	//修改返回键关闭本界面，而是回退上一个网页
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) &&   mWebView .canGoBack()) {       
            mWebView.goBack();       
            return true;       
        }       
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		if(v==mTvLeft){
			this.finish();
		}
	}


	@Override
	public void onDownloadStart(String url, String userAgent,
			String contentDisposition, String mimetype, long contentLength) {
		 Uri uri = Uri.parse(url);  
         Intent intent = new Intent(Intent.ACTION_VIEW, uri);  
         startActivity(intent); 
	}
}
