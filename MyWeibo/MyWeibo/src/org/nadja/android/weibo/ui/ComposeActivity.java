package org.nadja.android.weibo.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.LocationAjaxCallback;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;

import org.nadja.android.weibo.R;
import org.nadja.android.weibo.adapters.GridViewFaceAdapter;
import org.nadja.android.weibo.util.AlertUtil;
import org.nadja.android.weibo.util.Log;
import org.nadja.android.weibo.util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
/*
 * 发表微博界面
 */
public class ComposeActivity extends BaseActivity implements OnClickListener,
        RequestListener {

    private EditText mEdit;

    private GridView mGridView;

    private GridViewFaceAdapter mGVFaceAdapter;

    private TextView mTextNum;

    private String mPicPath = "";
    private Uri mImageUri = null;
    private String mContent = "";
    private String mLatitude = "";
    private String mLongitude = "";

    private AQuery aq;

    public static final int WEIBO_MAX_LENGTH = 140;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        initView();

        initGridView();

    }

    public void initView() {
        this.setContentView(R.layout.newblog2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        aq = new AQuery(this);

        aq.id(R.id.ll_text_limit_unit).clicked(this);

        mTextNum = (TextView) findViewById(R.id.tv_text_limit);

        mEdit = (EditText) this.findViewById(R.id.et_mblog);
        mEdit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mText = mEdit.getText().toString();
                int len = mText.length();
                if (len <= WEIBO_MAX_LENGTH) {
                    len = WEIBO_MAX_LENGTH - len;
                    mTextNum.setTextColor(getResources().getColor(R.color.text_num_gray));

                } else {
                    len = len - WEIBO_MAX_LENGTH;
                    mTextNum.setTextColor(Color.RED);
                }

                mTextNum.setText(String.valueOf(len));
            }
        });

    }

    // 鍒濆鍖栬〃鎯呮帶浠�
    private void initGridView() {
        mGVFaceAdapter = new GridViewFaceAdapter(this);
        mGridView = (GridView)findViewById(R.id.tweet_pub_faces);
        mGridView.setAdapter(mGVFaceAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 鎻掑叆鐨勮〃鎯�
                SpannableString ss = new SpannableString(view.getTag().toString());
                Drawable d = getResources().getDrawable((int)mGVFaceAdapter.getItemId(position));
                d.setBounds(0, 0, 35, 35);//璁剧疆琛ㄦ儏鍥剧墖鐨勬樉绀哄ぇ灏�
                ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
                ss.setSpan(span, 0, view.getTag().toString().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                // 鍦ㄥ厜鏍囨墍鍦ㄥ鎻掑叆琛ㄦ儏
                mEdit.getText().insert(mEdit.getSelectionStart(), ss);
            }
        });
    }

    @Override
    public void onComplete(String response) {
        Util.showToast(this, R.string.send_success);
        this.finish();
    }

    @Override
    public void onWeiboException(final WeiboException e) {
        String content = String.format(
                ComposeActivity.this.getString(R.string.send_failed) + ":%s", e.getMessage());
        Util.showToast(this, content);
    }

    private void composeNewPost() {
        StatusesAPI api = new StatusesAPI(mApplication, mContent, mAccessToken);
        mContent = mEdit.getText().toString();
        if (TextUtils.isEmpty(mContent))
            return;

        if (!TextUtils.isEmpty(mPicPath)) {
            Util.showToast(this, R.string.sending);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            api.upload(this.mContent, bitmap, mLatitude, mLongitude, this);
        } else {
            // Just update a text weibo!
            api.update(mContent, mLatitude, mLongitude, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.send, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send:
                composeNewPost();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return;
        }

        if (viewId == R.id.ll_text_limit_unit) {
            mContent = mEdit.getText().toString();
            if (TextUtils.isEmpty(mContent)) return;

            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    mEdit.setText("");
                }
            };

            AlertUtil.showAlert(this, R.string.attention, R.string.delete_all,
                    getString(R.string.ok), listener,
                    getString(R.string.cancel), null);
        }
    }
}

