package org.nadja.android.weibo.ui;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import org.nadja.android.weibo.core.Configuration;
import org.nadja.android.weibo.core.Session;
import org.nadja.android.weibo.util.Preferences;

import android.content.Intent;
import android.os.Bundle;

/**
 * 授权认证
 */
public class AuthenticatedActivity extends BaseActivity {
    private SsoHandler mSsoHandler;

    private Oauth2AccessToken mAccessToken;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthInfo weiboAuth = new AuthInfo(this, Configuration.CONSUMER_KEY,
                Configuration.REDIRECT_CALLBACK_URL, Configuration.SCOPE);

        mSsoHandler = new SsoHandler(this, weiboAuth);
        mSsoHandler.authorize(new AuthDialogListener());
    }

    private void enterTimeline() {
        Intent intent = new Intent(AuthenticatedActivity.this, TimelineActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    class AuthDialogListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            String token = values.getString(Preferences.ACCESS_TOKEN);
            String expires_in = values.getString(Preferences.EXPIRES_IN);

            mAccessToken = new Oauth2AccessToken(token, expires_in);
            if (mAccessToken.isSessionValid()) {

                Session.save(AuthenticatedActivity.this,
                        mAccessToken);
                getWeiboApplication().setOauth2AccessToken(mAccessToken);
                enterTimeline();
            }
        }

        @Override
        public void onCancel() {
            displayToast("Auth cancel");
        }

        @Override
        public void onWeiboException(WeiboException e) {
            displayToast("Auth exception : " + e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}

