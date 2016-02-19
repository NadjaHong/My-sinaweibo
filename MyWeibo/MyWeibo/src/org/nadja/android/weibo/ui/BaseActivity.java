package org.nadja.android.weibo.ui;

import java.util.Timer;
import java.util.TimerTask;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.nadja.android.weibo.R;
import org.nadja.android.weibo.WeiboApplication;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * 首页Activity
 */
public abstract class BaseActivity extends SherlockFragmentActivity {

	private static final int TOAST_DURATION = Toast.LENGTH_SHORT;

	protected WeiboApplication mApplication;

	protected Oauth2AccessToken mAccessToken;

	private Boolean isExit = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mApplication = (WeiboApplication) getApplication();
		mAccessToken = mApplication.getOauth2AccessToken();

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.base, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (this instanceof TimelineActivity) {
				return false;
			}

			NavUtils.navigateUpFromSameTask(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	//2秒退出程序
	public void onKeyDownExit() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast mTstShow = Toast.makeText(this, "再按一次退出程序",Toast.LENGTH_SHORT);
			mTstShow.setGravity(Gravity.BOTTOM, 0, 20);
			mTstShow.show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
		} else {
			this.finish();
		}
	}

	/**
	 * @param resId
	 *            resource id
	 */
	public void displayToast(int resId) {
		Toast.makeText(this, resId, TOAST_DURATION).show();
	}

	/**
	 * @param text
	 *            desplay text
	 */
	public void displayToast(CharSequence text) {
		Toast.makeText(this, text, TOAST_DURATION).show();
	}

	public WeiboApplication getWeiboApplication() {
		return mApplication;
	}
}
