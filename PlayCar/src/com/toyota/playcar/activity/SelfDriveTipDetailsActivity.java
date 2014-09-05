package com.toyota.playcar.activity;

import com.toyota.playcar.view.TitleBar;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

/**
 * 自驾游小常识详情界面
 * 
 * @author ganyu
 * @created 2014-8-25
 * 
 */
public class SelfDriveTipDetailsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void createView() {
		setContentView(R.layout.activity_selfdrive_tip_details);
		TitleBar title_bar = (TitleBar) findViewById(R.id.title_bar);
		WebView webview_tip = (WebView) findViewById(R.id.webview_tip);
		
		title_bar.setTitle(R.string.selfdrive_tip, true, this);
		String content = "<html>自驾游小常识自驾游小常识自驾游小常识。自驾游小常识自驾游小常识自驾游小常识。自驾游小常识自驾游小常识自驾游小常识。自驾游小常识自驾游小常识自驾游小常识。</html>";
		webview_tip.loadDataWithBaseURL(null, content, "text/html", ENCODE_UTF_8, null);
	}

	@Override
	public void onWidgetClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_bar_left: {
			finish();
		}
			break;
		default:
			break;
		}
	}

	@Override
	public void onResponse(int msgType, String response) {
		
	}

}
