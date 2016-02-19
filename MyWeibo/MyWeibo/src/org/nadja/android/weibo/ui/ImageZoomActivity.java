package org.nadja.android.weibo.ui;

import android.app.Activity;
import android.os.Bundle;

import com.androidquery.AQuery;

import org.nadja.android.weibo.R;

public class ImageZoomActivity extends Activity {

    private AQuery aq;

    private String mOriginalImageUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.image_zoom_activity);

        mOriginalImageUrl = getIntent().getStringExtra("original_pic_url");

        aq = new AQuery(this);

        image_zoom();
    }

    private void image_zoom() {
        aq.id(R.id.text).text("Try pinch zoom with finger.");
        if (mOriginalImageUrl != null) {
            aq.id(R.id.web).progress(R.id.progress).webImage(mOriginalImageUrl);
            // aq.id(R.id.web).progress(R.id.progress).webImage(url, true, true,
            // 0xFF000000);
        }
    }
}
