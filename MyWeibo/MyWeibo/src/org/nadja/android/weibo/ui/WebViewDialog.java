package org.nadja.android.weibo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebView;

import org.nadja.android.weibo.R;

public class WebViewDialog extends FragmentActivity {

    public static Intent getIntent(Context context, int title, String url) {
        return new Intent(context, WebViewDialog.class)
                .putExtra(Intent.EXTRA_TITLE, title)
                .putExtra(Intent.EXTRA_STREAM, url);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getIntent().getIntExtra(Intent.EXTRA_TITLE, R.string.app_name));

        setContentView(R.layout.activity_licenses);

        WebView webView = (WebView) findViewById(R.id.content);
        webView.loadUrl(getIntent().getStringExtra(Intent.EXTRA_STREAM));
    }
}

