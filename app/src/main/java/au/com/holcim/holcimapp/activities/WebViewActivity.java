package au.com.holcim.holcimapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.gson.JsonObject;
import com.wang.avi.AVLoadingIndicatorView;

import au.com.holcim.holcimapp.Constants;
import au.com.holcim.holcimapp.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jovan on 12/4/17.
 */

public class WebViewActivity extends AppCompatActivity {

    @Bind(R.id.webview) WebView mWebview;
    @Bind(R.id.av_indicator) AVLoadingIndicatorView mAvIndicator;
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        if(getIntent() !=null && getIntent().getStringExtra(Constants.Extras.WEBVIEW_URL) != null) {
            url = getIntent().getStringExtra(Constants.Extras.WEBVIEW_URL);
        }
        setupWebView(url);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupWebView(String url) {
        mAvIndicator.show();
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mAvIndicator.hide();
            }
        });
        mWebview.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
